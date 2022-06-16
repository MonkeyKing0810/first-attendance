package com.example.back.controller;

import com.example.back.model.Role;
import com.example.back.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/role")
public class RoleContoller {
    @Autowired
    private RoleService roleService;
    @RequestMapping(value = "/getRoleAll",method = RequestMethod.GET)
    public List<Role>getRoleAll(){
        return roleService.selectRoleAll();
    }
}
