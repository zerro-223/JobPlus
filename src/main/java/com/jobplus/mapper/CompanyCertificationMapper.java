package com.jobplus.mapper;

import com.jobplus.entity.CompanyCertification;
import org.apache.ibatis.annotations.*;

public interface CompanyCertificationMapper {

    @Select("SELECT * FROM company_certification WHERE id = #{id}")
    CompanyCertification findById(@Param("id") Integer id);

    @Select("SELECT * FROM company_certification WHERE company_id = #{companyId}")
    CompanyCertification findByCompanyId(@Param("companyId") Integer companyId);

    @Insert("INSERT INTO company_certification (company_id, license_url, license_number, legal_person, status) " +
            "VALUES (#{companyId}, #{licenseUrl}, #{licenseNumber}, #{legalPerson}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CompanyCertification cert);

    @Update("UPDATE company_certification SET license_url=#{licenseUrl}, license_number=#{licenseNumber}, " +
            "legal_person=#{legalPerson}, status=#{status}, reject_reason=#{rejectReason}, " +
            "auditor_id=#{auditorId}, audited_at=#{auditedAt} WHERE id=#{id}")
    int update(CompanyCertification cert);
}
