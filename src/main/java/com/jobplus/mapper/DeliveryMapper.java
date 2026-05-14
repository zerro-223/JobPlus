package com.jobplus.mapper;

import com.jobplus.entity.Delivery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 投递记录 Mapper
 */
public interface DeliveryMapper {

    Delivery findById(@Param("id") Integer id);

    Delivery findByUserAndPosition(@Param("userId") Integer userId,
                                   @Param("positionId") Integer positionId);

    int insert(Delivery delivery);

    int update(Delivery delivery);

    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    List<Delivery> findByUserId(@Param("userId") Integer userId);

    List<Delivery> findByCompanyId(@Param("companyId") Integer companyId);

    int markRead(@Param("id") Integer id);
    
    long count();
}
