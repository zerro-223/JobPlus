package com.jobplus.mapper;

import com.jobplus.entity.Interview;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 面试邀请 Mapper
 */
public interface InterviewMapper {

    Interview findById(@Param("id") Integer id);

    int insert(Interview interview);

    int update(Interview interview);

    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    List<Interview> findByUserId(@Param("userId") Integer userId);

    List<Interview> findByCompanyId(@Param("companyId") Integer companyId);

    long count();
}
