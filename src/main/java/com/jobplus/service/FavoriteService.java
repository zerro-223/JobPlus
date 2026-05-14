package com.jobplus.service;

import com.jobplus.entity.vo.PositionVO;

import java.util.List;

/**
 * 收藏服务接口
 */
public interface FavoriteService {

    void addFavorite(Integer userId, Integer positionId);

    void removeFavorite(Integer userId, Integer positionId);

    List<PositionVO> getMyFavorites(Integer userId);
}
