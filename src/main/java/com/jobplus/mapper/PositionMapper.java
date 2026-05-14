package com.jobplus.mapper;

import com.jobplus.entity.Position;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 职位 Mapper
 */
public interface PositionMapper {

    Position findById(@Param("id") Integer id);

    int insert(Position position);

    int update(Position position);

    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    int incrementViewCount(@Param("id") Integer id);

    int incrementDeliveryCount(@Param("id") Integer id);

    List<Position> findByCompanyId(@Param("companyId") Integer companyId);

    List<Position> search(@Param("keyword") String keyword,
                          @Param("categoryId") Integer categoryId,
                          @Param("workplace") String workplace,
                          @Param("education") String education,
                          @Param("experience") String experience,
                          @Param("status") Integer status,
                          @Param("offset") int offset,
                          @Param("limit") int limit);

    long searchCount(@Param("keyword") String keyword,
                     @Param("categoryId") Integer categoryId,
                     @Param("workplace") String workplace,
                     @Param("education") String education,
                     @Param("experience") String experience,
                     @Param("status") Integer status);

    long count();

    long countByStatus(@Param("status") Integer status);
}
