package com.jobplus.service;

import com.jobplus.common.dto.*;
import com.jobplus.entity.*;

import java.util.List;

public interface PositionService {
    PageResult<PositionVO> search(PositionQuery query);
    PositionVO getDetail(Integer id, Integer userId);
    PositionVO create(PositionCreateReq req, User user);
    void update(PositionUpdateReq req, User user);
    void updateStatus(Integer id, Integer status, User user);
    void audit(Integer id, Integer adminId, boolean approved, String reason);
    List<PositionVO> getCompanyPositions(Integer companyId);
}
