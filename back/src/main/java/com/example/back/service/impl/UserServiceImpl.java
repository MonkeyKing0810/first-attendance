package com.example.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.back.dao.AttendanceMapper;
import com.example.back.dao.UserMapper;
import com.example.back.model.User;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AttendanceMapper attendanceMapper;


    @Override
    public Map<String, Object> login(String linkNum, String password) {
        HashMap<String, Object> map = new HashMap<>();
        String msg=null;
        User login = userMapper.login(linkNum, password);
        if (login!=null){
            msg="登录成功";
            map.put("roleId",login.getRoleId());
            map.put("userId",login.getUserId());
        }else {
            msg="用户名或密码错误";
        }
        map.put("msg",msg);
        System.out.println(login);

        return map;
    }

    @Override
    public List<User> findUserAll() {
        return userMapper.getUserAll();
    }

    @Override
    public String insertUser(User user) {
        String msg=null;
        int i = userMapper.insertUser(user);

        if (i>0){
            attendanceMapper.insert(userMapper.getUserId());
            msg="新增成功";

        }else {
            msg="新增失败";
        }
        return msg;
    }

    @Override
    public String updateUser(User user) {
        String msg=null;
        int i = userMapper.updateUser(user);
        if (i>0){
            msg="修改成功";

        }else {
            msg="修改失败";
        }
        return msg;
    }

    @Override
    public String deleteUserById(Integer id) {
        String msg=null;
        int i = userMapper.deleteUserById(id);
        if (i>0){
            msg="删除成功";

        }else {
            msg="删除失败";
        }
        return msg;
    }

    @Override
    public List<User> queryUserByConditions(String username,String linknum) {
        return userMapper.queryUserByConditions(username,linknum);
    }

    @Override
    public JSONObject getUserAllByPage(int currentPage, int size) {
        int Page=(currentPage-1)*size;
        List<User> users = userMapper.getUserAllByPage(Page, size);
        int total = userMapper.selectCount();
        JSONObject res=new JSONObject();
        res.put("userInfo",users);
        res.put("total",total);

        return res;
    }

    @Override
    public List<User> getUser() {
        return userMapper.getUser();
    }

    @Override
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }


}
