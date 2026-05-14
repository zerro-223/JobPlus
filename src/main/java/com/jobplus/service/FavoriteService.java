package com.jobplus.service;

import com.jobplus.common.dto.PositionVO;

import java.util.List;

public interface FavoriteService {
    void addFavorite(Integer userId, Integer positionId);
    void removeFavorite(Integer userId, Integer positionId);
    List<PositionVO> getMyFavorites(Integer userId);
}
