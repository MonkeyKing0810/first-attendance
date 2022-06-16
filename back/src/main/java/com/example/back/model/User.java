package com.example.back.model;

import lombok.Data;

@Data
public class User {
    private Integer userId;
    private String userName;
    private String linkNum;
    private String password;
    private Integer roleId;
    private String roleName;
}
