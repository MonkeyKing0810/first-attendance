package com.example.back.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.back.model.User;
import com.example.back.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "用户相关接口")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录操作")
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public Map userLogin(@RequestBody User user){
        return userService.login(user.getLinkNum(),user.getPassword());
    }

    @ApiOperation(value = "查询全部用户信息")
    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public List<User> getUser(){
        return userService.getUser();
    }

    @ApiOperation(value = "查询全部用户信息")
    @RequestMapping(value = "/getUserAll",method = RequestMethod.GET)
    public List<User> getUserAll(){
        return userService.findUserAll();
    }
    /**
     * 分页查询
     */
    @PostMapping("/getUserAllByPage/{currentPage}/{size}")
    public JSONObject getUserAllByPage(@PathVariable("currentPage") int currentPage, @PathVariable("size") int size) {
        return userService.getUserAllByPage(currentPage, size);
    }
    @ApiOperation(value = "根据条件查询用户信息")
    @RequestMapping(value = "/queryUserByConditions/{username}/{linknum}",method = RequestMethod.POST)
    public List<User> queryUserByConditions(@PathVariable("username") String username,@PathVariable("linknum") String linknum){
        return userService.queryUserByConditions(username,linknum);
    }

    @ApiOperation(value = "根据id查询用户信息")
    @RequestMapping(value = "/getUserById/{userId}",method = RequestMethod.POST)
    public User getUserById(@PathVariable("userId") Integer userId){
        return userService.getUserById(userId);
    }

    @ApiOperation(value = "新增用户")
    @RequestMapping(value = "/insertUser",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String insertUser(@RequestBody User user){
        return userService.insertUser(user);
    }

    @ApiOperation(value = "编辑用户")
    @RequestMapping(value = "/updateUser",method = RequestMethod.PUT,produces = "application/json;charset=UTF-8")
    public String updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @ApiOperation(value = "删除一个用户")
    @RequestMapping(value = "/deleteUserById/{id}",method = RequestMethod.DELETE,produces = "application/json;charset=UTF-8")
    public String deleteUserById(@PathVariable("id")Integer id){
        return userService.deleteUserById(id);

    }
}
