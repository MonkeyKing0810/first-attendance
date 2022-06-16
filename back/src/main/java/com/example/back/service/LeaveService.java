package com.example.back.service;

import com.alibaba.fastjson.JSONObject;
import com.example.back.model.Leave;

import java.util.List;

public interface LeaveService {

    String insertLeave(Leave leave);

    String updateLeave(Leave leave);

    String deleteLeaveById(Integer id);

    JSONObject getLeaveAllByPage(int currentPage, int size);
    List<Leave>leaveTimes(Integer userId);
    List<Leave> queryUserLeaveByConditions(Integer userId,Integer leaveTypeId,String startTime, String endTime);
    JSONObject getSetLeaveAllByPage(int currentPage, int size);

    List<Leave> queryLeaveByConditions(Integer leaveTypeId,String startTime, String endTime);

    List<Leave> queryLeaveByConditionsSetLeave(Integer userId, Integer leaveTypeId, String startTime, String endTime, String status);
}
