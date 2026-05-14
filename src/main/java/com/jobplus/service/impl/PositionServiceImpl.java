package com.jobplus.service.impl;

import com.jobplus.common.dto.PositionCreateReq;
import com.jobplus.common.dto.PositionQuery;
import com.jobplus.common.dto.PositionUpdateReq;
import com.jobplus.common.exception.BusinessException;
import com.jobplus.common.vo.PageResult;
import com.jobplus.common.vo.PositionVO;
import com.jobplus.entity.*;
import com.jobplus.mapper.*;
import com.jobplus.service.PositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 职位服务实现
 */
@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private AdminActionLogMapper adminActionLogMapper;

    /**
     * 分页搜索职位
     */
    @Override
    public PageResult<PositionVO> search(PositionQuery query) {
        List<Position> positions = positionMapper.search(query);
        long total = positionMapper.count(query);

        List<PositionVO> voList = positions.stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        int page = query.getPage() != null ? query.getPage() : 1;
        int size = query.getSize() != null ? query.getSize() : 10;

        return new PageResult<>(total, page, size, voList);
    }

    /**
     * 获取职位详情，自动增加浏览计数
     */
    @Override
    public PositionVO getDetail(Integer id, Integer userId) {
        Position position = positionMapper.findById(id);
        if (position == null) {
            throw new BusinessException(404, "职位不存在");
        }

        // 增加浏览计数
        positionMapper.incrementViewCount(id);

        PositionVO vo = toVO(position);

        // 如果用户已登录，检查是否收藏
        if (userId != null) {
            boolean favorited = favoriteMapper.exists(userId, id);
            vo.setIsFavorited(favorited);
        }

        return vo;
    }

    /**
     * 创建职位（企业用户）
     */
    @Override
    public PositionVO create(PositionCreateReq req, User user) {
        // 校验企业身份
        Company company = companyMapper.findByUserId(user.getId());
        if (company == null) {
            throw new BusinessException(400, "请先创建企业信息");
        }
        if (company.getStatus() != 1) {
            throw new BusinessException(400, "企业信息未通过审核，无法发布职位");
        }

        Position position = new Position();
        BeanUtils.copyProperties(req, position);
        position.setCompanyId(company.getId());
        position.setStatus(0); // 待审核
        position.setViewCount(0);
        position.setDeliveryCount(0);
        position.setCreatedAt(new Date());
        position.setUpdatedAt(new Date());

        positionMapper.insert(position);

        return toVO(position);
    }

    /**
     * 更新职位
     */
    @Override
    public void update(PositionUpdateReq req, User user) {
        Position position = positionMapper.findById(req.getId());
        if (position == null) {
            throw new BusinessException(404, "职位不存在");
        }

        // 校验归属权
        Company company = companyMapper.findByUserId(user.getId());
        if (company == null || !company.getId().equals(position.getCompanyId())) {
            throw new BusinessException(403, "无权修改该职位");
        }

        BeanUtils.copyProperties(req, position, "id", "companyId", "status", "viewCount", "deliveryCount");
        position.setStatus(0); // 回退到待审核
        position.setUpdatedAt(new Date());

        positionMapper.update(position);
    }

    /**
     * 更新职位状态
     */
    @Override
    public void updateStatus(Integer id, Integer status) {
        Position position = positionMapper.findById(id);
        if (position == null) {
            throw new BusinessException(404, "职位不存在");
        }
        positionMapper.updateStatus(id, status);
    }

    /**
     * 审核职位
     */
    @Override
    public void audit(Integer id, Integer adminId, boolean approved, String reason) {
        Position position = positionMapper.findById(id);
        if (position == null) {
            throw new BusinessException(404, "职位不存在");
        }
        if (position.getStatus() != 0) {
            throw new BusinessException(400, "该职位不是待审核状态");
        }

        int newStatus = approved ? 1 : 2; // 1=通过, 2=驳回
        positionMapper.updateStatus(id, newStatus);

        // 记录操作日志
        AdminActionLog log = new AdminActionLog();
        log.setAdminId(adminId);
        log.setAction(approved ? "APPROVE_POSITION" : "REJECT_POSITION");
        log.setTargetType("POSITION");
        log.setTargetId(id);
        log.setDetail(reason);
        log.setCreatedAt(new Date());
        adminActionLogMapper.insert(log);

        // 发送通知给企业
        Company company = companyMapper.findById(position.getCompanyId());
        if (company != null) {
            Notification notification = new Notification();
            notification.setUserId(company.getUserId());
            notification.setTitle(approved ? "职位审核通过" : "职位审核驳回");
            notification.setContent(approved
                    ? "您的职位「" + position.getTitle() + "」已通过审核"
                    : "您的职位「" + position.getTitle() + "」未通过审核，原因：" + reason);
            notification.setType("POSITION_AUDIT");
            notification.setReadFlag(0);
            notification.setCreatedAt(new Date());
            notificationMapper.insert(notification);
        }
    }

    /**
     * 获取企业职位列表
     */
    @Override
    public List<PositionVO> getCompanyPositions(Integer companyId) {
        List<Position> positions = positionMapper.findByCompanyId(companyId);
        return positions.stream()
                .map(this::toVO)
                .collect(Collectors.toList());
    }

    /**
     * Position → PositionVO 转换，并填充企业名称
     */
    private PositionVO toVO(Position position) {
        PositionVO vo = new PositionVO();
        BeanUtils.copyProperties(position, vo);

        // 填充企业名称
        Company company = companyMapper.findById(position.getCompanyId());
        if (company != null) {
            vo.setCompanyName(company.getName());
        }

        return vo;
    }
}
