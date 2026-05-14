package com.jobplus.mapper;

import com.jobplus.entity.Favorite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏 Mapper
 */
public interface FavoriteMapper {

    Favorite findByUserAndPosition(@Param("userId") Integer userId,
                                   @Param("positionId") Integer positionId);

    int insert(Favorite favorite);

    int delete(@Param("userId") Integer userId, @Param("positionId") Integer positionId);

    List<Favorite> findByUserId(@Param("userId") Integer userId);
}
