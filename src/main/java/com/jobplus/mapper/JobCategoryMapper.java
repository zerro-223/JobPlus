package com.jobplus.mapper;

import com.jobplus.entity.JobCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface JobCategoryMapper {

    @Select("SELECT * FROM job_category ORDER BY sort_order")
    List<JobCategory> findAll();

    @Select("SELECT * FROM job_category WHERE parent_id = #{parentId} ORDER BY sort_order")
    List<JobCategory> findByParentId(@Param("parentId") Integer parentId);

    @Select("SELECT * FROM job_category WHERE id = #{id}")
    JobCategory findById(@Param("id") Integer id);
}
