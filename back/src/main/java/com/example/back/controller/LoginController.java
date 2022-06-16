//package com.example.back.controller;
//
//import com.example.back.model.Admin;
//import com.example.back.service.AdminService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class LoginController {
//
//    @Autowired
//    private AdminService adminService;
//
//    @PostMapping("/login")
//    public String login(@RequestBody Admin admin){
//       return adminService.login(admin.getUsername(),admin.getPassword());
//    }
//}
