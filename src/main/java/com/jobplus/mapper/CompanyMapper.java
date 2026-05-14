package com.jobplus.mapper;

import com.jobplus.entity.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 企业 Mapper
 */
public interface CompanyMapper {

    Company findById(@Param("id") Integer id);

    Company findByUserId(@Param("userId") Integer userId);

    int insert(Company company);

    int update(Company company);

    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    List<Company> findByStatus(@Param("status") Integer status);

    long count();

    long countByStatus(@Param("status") Integer status);
}
