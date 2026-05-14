package com.jobplus.service;

import com.jobplus.entity.Company;
import com.jobplus.entity.CompanyCertification;
import com.jobplus.entity.vo.DeliveryVO;
import com.jobplus.entity.vo.PositionVO;

import java.util.List;

/**
 * 企业服务接口
 */
public interface CompanyService {

    Company getProfile(Integer userId);

    void updateProfile(Company company, Integer userId);

    void submitCertification(CompanyCertification certification, Integer userId);

    Company getPublicInfo(Integer companyId);

    List<PositionVO> getPositions(Integer companyId);

    List<DeliveryVO> getDeliveries(Integer userId);
}
