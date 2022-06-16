package com.example.back.service;

import com.alibaba.fastjson.JSONObject;
import com.example.back.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    Map login(String linkNum, String password);

    List<User> findUserAll();

    String insertUser(User user);

    String updateUser(User user);

    String deleteUserById(Integer id);

    List<User> queryUserByConditions(String username,String linknum);

    JSONObject getUserAllByPage(int currentPage, int size);

    List<User> getUser();
    User getUserById(Integer userId);
}
