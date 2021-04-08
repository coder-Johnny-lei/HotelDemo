package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;

public interface UserService {
     List<User> getUserListAll();
     User findUserByUserName(String userName);
     User findUserById(Integer userId);
}
