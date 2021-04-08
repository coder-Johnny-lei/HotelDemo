package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UserMapper {
    List<User> getUserListAll();

    User findUserByUserName(@Param("userName") String userName);

    User findUserById(@Param("userId") Integer userId);
}
