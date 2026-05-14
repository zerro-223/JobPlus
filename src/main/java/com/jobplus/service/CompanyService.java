package com.jobplus.service;

import com.jobplus.common.dto.DeliveryVO;
import com.jobplus.entity.*;

import java.util.List;

public interface CompanyService {
    Company getProfile(Integer userId);
    void updateProfile(Company company, User user);
    void submitCertification(CompanyCertification cert, User user);
    Company getPublicInfo(Integer companyId);
    List<Position> getPositions(Integer companyId);
    List<DeliveryVO> getDeliveries(User user);
}
