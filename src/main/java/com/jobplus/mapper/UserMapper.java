package com.jobplus.mapper;

import com.jobplus.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户 Mapper
 */
public interface UserMapper {

    User findById(@Param("id") Integer id);

    User findByEmail(@Param("email") String email);

    int insert(User user);

    int update(User user);

    int updatePassword(@Param("id") Integer id, @Param("password") String password);

    List<User> findAll();

    List<User> findByRole(@Param("role") Integer role);

    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    long count();
}
