package com.jobplus.service;

import com.jobplus.common.dto.PageResult;
import com.jobplus.entity.JobCategory;
import com.jobplus.entity.Position;
import com.jobplus.entity.vo.PositionVO;

import java.util.List;

/**
 * 职位服务接口
 */
public interface PositionService {

    PageResult<PositionVO> search(String keyword, Integer categoryId, String workplace,
                                  String education, String experience, int page, int size,
                                  Integer userId);

    PositionVO getDetail(Integer positionId, Integer userId);

    PositionVO publish(Position position, Integer userId);

    void update(Integer positionId, Position position, Integer userId);

    void offline(Integer positionId, Integer userId);

    List<JobCategory> getCategories();
}
