package com.jobplus.mapper;

import com.jobplus.entity.Resume;
import org.apache.ibatis.annotations.Param;

/**
 * 简历 Mapper
 */
public interface ResumeMapper {

    Resume findById(@Param("id") Integer id);

    Resume findByUserId(@Param("userId") Integer userId);

    int insert(Resume resume);

    int update(Resume resume);
}
