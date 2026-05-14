package com.jobplus.mapper;

import com.jobplus.entity.Favorite;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FavoriteMapper {

    @Select("SELECT * FROM favorite WHERE user_id = #{userId} AND position_id = #{positionId}")
    Favorite findByUserAndPosition(@Param("userId") Integer userId, @Param("positionId") Integer positionId);

    @Insert("INSERT INTO favorite (user_id, position_id) VALUES (#{userId}, #{positionId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Favorite favorite);

    @Delete("DELETE FROM favorite WHERE user_id = #{userId} AND position_id = #{positionId}")
    int delete(@Param("userId") Integer userId, @Param("positionId") Integer positionId);

    @Select("SELECT * FROM favorite WHERE user_id = #{userId} ORDER BY created_at DESC")
    List<Favorite> findByUserId(@Param("userId") Integer userId);

    @Select("SELECT COUNT(*) > 0 FROM favorite WHERE user_id = #{userId} AND position_id = #{positionId}")
    boolean exists(@Param("userId") Integer userId, @Param("positionId") Integer positionId);
}
