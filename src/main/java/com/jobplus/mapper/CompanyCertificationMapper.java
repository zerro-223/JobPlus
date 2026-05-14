package com.jobplus.mapper;

import com.jobplus.entity.CompanyCertification;
import org.apache.ibatis.annotations.Param;

/**
 * 企业认证资料 Mapper
 */
public interface CompanyCertificationMapper {

    CompanyCertification findById(@Param("id") Integer id);

    CompanyCertification findByCompanyId(@Param("companyId") Integer companyId);

    int insert(CompanyCertification cert);

    int update(CompanyCertification cert);
}
