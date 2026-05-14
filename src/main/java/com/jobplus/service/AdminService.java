package com.jobplus.service;

import com.jobplus.common.dto.*;
import com.jobplus.entity.*;

import java.util.List;

public interface AdminService {
    List<Company> getPendingCompanies();
    void auditCompany(Integer companyId, boolean approved, String rejectReason, User admin);
    List<PositionVO> getPendingPositions();
    void auditPosition(Integer positionId, boolean approved, String rejectReason, User admin);
    List<User> getUsers();
    void updateUserStatus(Integer userId, Integer status);
    StatsVO getStats();
}
