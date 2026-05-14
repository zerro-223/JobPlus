package com.jobplus.mapper;

import com.jobplus.entity.Company;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CompanyMapper {

    @Select("SELECT * FROM company WHERE id = #{id}")
    Company findById(@Param("id") Integer id);

    @Select("SELECT * FROM company WHERE user_id = #{userId}")
    Company findByUserId(@Param("userId") Integer userId);

    @Insert("INSERT INTO company (user_id, name, short_name, logo_url, industry, scale, description, website, address, contact, contact_phone, status) " +
            "VALUES (#{userId}, #{name}, #{shortName}, #{logoUrl}, #{industry}, #{scale}, #{description}, #{website}, #{address}, #{contact}, #{contactPhone}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Company company);

    @Update("UPDATE company SET name=#{name}, short_name=#{shortName}, logo_url=#{logoUrl}, " +
            "industry=#{industry}, scale=#{scale}, description=#{description}, website=#{website}, " +
            "address=#{address}, contact=#{contact}, contact_phone=#{contactPhone} WHERE id=#{id}")
    int update(Company company);

    @Update("UPDATE company SET status=#{status} WHERE id=#{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    @Select("SELECT * FROM company WHERE status = #{status}")
    List<Company> findByStatus(@Param("status") Integer status);

    @Select("SELECT COUNT(*) FROM company")
    long count();

    @Select("SELECT COUNT(*) FROM company WHERE status = #{status}")
    long countByStatus(@Param("status") Integer status);
}
