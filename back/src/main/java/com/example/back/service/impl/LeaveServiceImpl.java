package com.example.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.back.dao.AttendanceMapper;
import com.example.back.dao.LeaveMapper;
import com.example.back.dao.WorkOvertimeMapper;
import com.example.back.model.Attendance;
import com.example.back.model.Leave;
import com.example.back.model.User;
import com.example.back.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private WorkOvertimeMapper workOvertimeMapper;
    @Override
//    @Cacheable(cacheNames = "getLeaveAllByPage",unless = "#result==null")
    public JSONObject getLeaveAllByPage(int currentPage, int size) {
        int Page=(currentPage-1)*size;
        List<Leave> leaves = leaveMapper.getLeaveAllByPage(Page, size);
        int total = leaveMapper.selectCount();

        JSONObject res=new JSONObject();
        res.put("leaveInfo",leaves);
        res.put("total",total);


        return res;
    }

    @Override
    public List<Leave> leaveTimes(Integer userId) {
        return leaveMapper.leaveTimes(userId);
    }


    @Override
    public JSONObject getSetLeaveAllByPage(int currentPage, int size) {
        int Page=(currentPage-1)*size;
        List<Leave> leaves = leaveMapper.getSetLeaveAllByPage(Page, size);
        int total = leaveMapper.selectSetLeaveCount();
        JSONObject res=new JSONObject();
        res.put("leaveInfo",leaves);
        res.put("total",total);


        return res;
    }

    @Override
    public List<Leave> queryLeaveByConditions(Integer leaveTypeId,String startTime, String endTime) {
        return leaveMapper.queryLeaveByConditions(leaveTypeId,startTime,endTime);
    }
    @Override
    public List<Leave> queryUserLeaveByConditions(Integer userId,Integer leaveTypeId,String startTime, String endTime) {
        return leaveMapper.queryUserLeaveByConditions(userId,leaveTypeId,startTime,endTime);
    }
    @Override
    public List<Leave> queryLeaveByConditionsSetLeave(Integer userId, Integer leaveTypeId, String startTime, String endTime, String status) {
        return leaveMapper.queryLeaveByConditionsSetLeave(userId,leaveTypeId,startTime,endTime,status);
    }

    @Override
    public String insertLeave(Leave leave) {
        String msg=null;
        int i = leaveMapper.insertLeave(leave);

        attendanceMapper.updateLeave(leave.getUserId(),leave.getLeaveCounts());
//        System.out.println(leave.getUserId());
//
////        ?????????????????????????????????userId
//        Attendance attendance = attendanceMapper.selectAttendanceUserId(leave.getUserId());
//        if(!Objects.isNull(attendance)){
//            //        ????????????userId?????????????????????????????????????????????????????????leaveId????????????  ???????????????????????????leaveId
//            Integer leaveId = attendance.getLeaveId();
//            if(leaveId==null){
//                attendanceMapper.insertAttendanceLeave(leave.getLeaveId());
//            }else{
//             //  leaveId?????????????????????????????????????????????????????????????????????
//                attendanceMapper.updateAttendanceLeave(leave.getUserId(),leave.getLeaveId());
//            }
//        }

       // System.out.println(leave.getLeaveId());

        if (i>0){
            msg="????????????";

        }else {
            msg="????????????";
        }
        return msg;
    }

    @Override
    public String updateLeave(Leave leave) {
        String msg=null;
        int i = leaveMapper.updateLeave(leave);
        if (i>0){
            msg="????????????";

        }else {
            msg="????????????";
        }
        return msg;
    }

    @Override
    public String deleteLeaveById(Integer id) {
        String msg=null;
        int i = leaveMapper.deleteLeaveById(id);
        if (i>0){
            msg="????????????";

        }else {
            msg="????????????";
        }
        return msg;
    }


}
