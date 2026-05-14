package com.jobplus.service;

import com.jobplus.entity.Company;
import com.jobplus.entity.User;
import com.jobplus.entity.vo.PositionVO;
import com.jobplus.entity.vo.StatsVO;

import java.util.List;

/**
 * 管理员服务接口
 */
public interface AdminService {

    List<Company> getPendingCompanies();

    void auditCompany(Integer companyId, Integer approved, String reason, Integer adminId);

    List<PositionVO> getPendingPositions();

    void auditPosition(Integer positionId, Integer status, Integer adminId);

    List<User> getUsers();

    void updateUserStatus(Integer userId, Integer status);

    StatsVO getStats();
}
