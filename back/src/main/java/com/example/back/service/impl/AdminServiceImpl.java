//package com.example.back.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.example.back.dao.AdminMapper;
//import com.example.back.model.Admin;
//import com.example.back.service.AdminService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AdminServiceImpl implements AdminService {
//
//    @Autowired
//    private AdminMapper adminMapper;
//
//    @Override
//    public String login(String username, String password){
//        String msg=null;
//        Admin login = adminMapper.login(username, password);
//        if (login!=null){
//            msg="登录成功";
//        }else {
//            msg="用户名或密码错误";
//        }
//        return msg;
//    }
//
//}
