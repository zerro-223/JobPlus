package com.jobplus.mapper;

import com.jobplus.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("SELECT * FROM user WHERE email = #{email}")
    User findByEmail(@Param("email") String email);

    @Insert("INSERT INTO user (email, phone, password, role, nickname, status) " +
            "VALUES (#{email}, #{phone}, #{password}, #{role}, #{nickname}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE user SET phone=#{phone}, nickname=#{nickname}, avatar_url=#{avatarUrl} WHERE id=#{id}")
    int update(User user);

    @Update("UPDATE user SET password=#{password} WHERE id=#{id}")
    int updatePassword(@Param("id") Integer id, @Param("password") String password);

    @Select("SELECT * FROM user")
    List<User> findAll();

    @Select("SELECT * FROM user WHERE role = #{role}")
    List<User> findByRole(@Param("role") Integer role);

    @Update("UPDATE user SET status=#{status} WHERE id=#{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    @Select("SELECT COUNT(*) FROM user")
    long count();
}
