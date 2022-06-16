package com.example.back.dao;

import com.example.back.model.Leave;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveMapper {

    int insertLeave(Leave leave);

    int insertLeaveUserId(Integer userId);

    int updateLeave(Leave leave);

    int deleteLeaveById(Integer id);

    List<Leave> getLeaveAllByPage(int Page, int size);
    int selectCount();

    List<Leave>leaveTimes(Integer userId);
    List<Leave> queryLeaveByConditions(Integer leaveTypeId,String startTime, String endTime);

    List<Leave>queryUserLeaveByConditions(Integer userId,Integer leaveTypeId,String startTime, String endTime);

    List<Leave> getSetLeaveAllByPage(int Page, int size);
    int selectSetLeaveCount();




    List<Leave>queryLeaveByConditionsSetLeave(Integer userId,Integer leaveTypeId,String startTime, String endTime,String status);


}
