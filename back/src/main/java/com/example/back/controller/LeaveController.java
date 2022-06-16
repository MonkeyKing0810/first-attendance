package com.example.back.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.back.model.Leave;
import com.example.back.model.User;
import com.example.back.service.LeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "请假管理相关接口")
@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @ApiOperation(value = "查询全部请假单信息")
    @RequestMapping(value = "/getLeaveAllByPage/{currentPage}/{size}",method = RequestMethod.POST)
    public JSONObject getLeaveAll(@PathVariable("currentPage") int currentPage, @PathVariable("size") int size){
        return leaveService.getLeaveAllByPage(currentPage, size);
    }
    @ApiOperation(value = "查询全部请假时间信息")
    @RequestMapping(value = "/getleaveTimes/{userId}",method = RequestMethod.POST)
    public List<Leave> getleaveTimes(@PathVariable("userId")Integer userId){
        return leaveService.leaveTimes(userId);
    }

    @ApiOperation(value = "查询全部批假单信息")
    @RequestMapping(value = "/getSetLeaveAllByPage/{currentPage}/{size}",method = RequestMethod.POST)
    public JSONObject getSetLeaveAllByPage(@PathVariable("currentPage") int currentPage, @PathVariable("size") int size){
        return leaveService.getSetLeaveAllByPage(currentPage, size);
    }
    @ApiOperation(value = "根据条件查询请假信息")
    @RequestMapping(value = "/queryLeaveByConditions/{leaveTypeId}/{startTime}/{endTime}",method = RequestMethod.POST)
    public List<Leave> queryLeaveByConditions(@PathVariable("leaveTypeId") Integer leaveTypeId,
                                              @PathVariable("startTime") String startTime,
                                              @PathVariable("endTime") String endTime){
        return leaveService.queryLeaveByConditions(leaveTypeId,startTime,endTime);
    }
    @ApiOperation(value = "根据条件查询批假信息")
    @RequestMapping(value = "/queryLeaveByConditionsSetLeave/{userId}/{leaveTypeId}/{startTime}/{endTime}/{status}",method = RequestMethod.POST)
    public List<Leave> queryLeaveByConditionsSetLeave(
                                                     @PathVariable("userId") Integer userId,
                                                      @PathVariable("leaveTypeId") Integer leaveTypeId,
                                                      @PathVariable("startTime") String startTime,
                                                      @PathVariable("endTime") String endTime,
                                                      @PathVariable("status") String status
){
        return leaveService.queryLeaveByConditionsSetLeave(userId,leaveTypeId,startTime,endTime,status);
    }

    @ApiOperation(value = "新建请假单")
    @RequestMapping(value = "/insertLeave",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String insertLeave(@RequestBody Leave leave){
        return leaveService.insertLeave(leave);
    }

    @ApiOperation(value = "编辑请假单")
    @RequestMapping(value = "/updateLeave",method = RequestMethod.PUT,produces = "application/json;charset=UTF-8")
    public String updateLeave(@RequestBody Leave leave){
        return leaveService.updateLeave(leave);
    }

    @ApiOperation(value = "删除请假单")
    @RequestMapping(value = "/deleteLeaveById/{id}",method = RequestMethod.DELETE,produces = "application/json;charset=UTF-8")
    public String deleteLeaveById(@PathVariable("id")Integer id){
        return leaveService.deleteLeaveById(id);

    }
}
