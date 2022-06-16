package com.example.back.service.impl;

import com.example.back.dao.RoleMapper;
import com.example.back.model.Role;
import com.example.back.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> selectRoleAll() {
        return roleMapper.selectRoleAll();
    }
}
