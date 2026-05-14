package com.jobplus.service.impl;

import com.jobplus.common.dto.PositionVO;
import com.jobplus.common.exception.BusinessException;
import com.jobplus.entity.Company;
import com.jobplus.entity.Favorite;
import com.jobplus.entity.Position;
import com.jobplus.mapper.CompanyMapper;
import com.jobplus.mapper.FavoriteMapper;
import com.jobplus.mapper.PositionMapper;
import com.jobplus.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired private FavoriteMapper favoriteMapper;
    @Autowired private PositionMapper positionMapper;
    @Autowired private CompanyMapper companyMapper;

    @Override
    public void addFavorite(Integer userId, Integer positionId) {
        if (favoriteMapper.exists(userId, positionId))
            throw new BusinessException(400, "已收藏该职位");
        Favorite f = new Favorite();
        f.setUserId(userId);
        f.setPositionId(positionId);
        favoriteMapper.insert(f);
    }

    @Override
    public void removeFavorite(Integer userId, Integer positionId) {
        if (!favoriteMapper.exists(userId, positionId))
            throw new BusinessException(404, "未收藏该职位");
        favoriteMapper.delete(userId, positionId);
    }

    @Override
    public List<PositionVO> getMyFavorites(Integer userId) {
        List<Favorite> favs = favoriteMapper.findByUserId(userId);
        List<PositionVO> vos = new ArrayList<>();
        for (Favorite f : favs) {
            Position p = positionMapper.findById(f.getPositionId());
            if (p == null) continue;
            PositionVO vo = new PositionVO();
            vo.setId(p.getId()); vo.setCompanyId(p.getCompanyId());
            vo.setCategoryId(p.getCategoryId()); vo.setTitle(p.getTitle());
            vo.setDescription(p.getDescription()); vo.setRequirement(p.getRequirement());
            vo.setSalaryMin(p.getSalaryMin()); vo.setSalaryMax(p.getSalaryMax());
            vo.setEducation(p.getEducation()); vo.setExperience(p.getExperience());
            vo.setWorkplace(p.getWorkplace()); vo.setTags(p.getTags());
            vo.setStatus(p.getStatus()); vo.setViewCount(p.getViewCount());
            vo.setDeliveryCount(p.getDeliveryCount());
            vo.setExpireAt(p.getExpireAt()); vo.setCreatedAt(p.getCreatedAt());
            Company c = companyMapper.findById(p.getCompanyId());
            if (c != null) { vo.setCompanyName(c.getName()); vo.setCompanyLogo(c.getLogoUrl()); }
            vo.setIsFavorited(true);
            vos.add(vo);
        }
        return vos;
    }
}
