package com.jobplus.mapper;

import com.jobplus.entity.JobCategory;

import java.util.List;

/**
 * 职位分类 Mapper
 */
public interface JobCategoryMapper {

    List<JobCategory> findAll();

    JobCategory findById(Integer id);
}
