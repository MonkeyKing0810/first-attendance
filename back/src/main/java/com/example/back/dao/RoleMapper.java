package com.example.back.dao;

import com.example.back.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleMapper {
    List<Role> selectRoleAll();
}
