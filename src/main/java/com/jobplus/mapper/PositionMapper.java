package com.jobplus.mapper;

import com.jobplus.common.dto.PositionQuery;
import com.jobplus.entity.Position;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PositionMapper {

    Position findById(@Param("id") Integer id);

    int insert(Position position);

    int update(Position position);

    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    int incrementViewCount(@Param("id") Integer id);

    int incrementDeliveryCount(@Param("id") Integer id);

    List<Position> findByCompanyId(@Param("companyId") Integer companyId);

    /** Dynamic search via XML (sql/mapper/PositionMapper.xml) */
    List<Position> search(PositionQuery query);

    /** Count matching records via XML */
    long count(PositionQuery query);

    @Select("SELECT COUNT(*) FROM `position`")
    long countTotal();

    @Select("SELECT COUNT(*) FROM `position` WHERE status = #{status}")
    long countByStatus(@Param("status") Integer status);
}
