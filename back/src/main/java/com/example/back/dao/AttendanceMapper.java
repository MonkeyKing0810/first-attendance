package com.example.back.dao;

import com.example.back.model.Attendance;
import com.example.back.model.AttendanceExcel;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceMapper {
    List<Attendance>getAttendanceAll();
    List<Attendance>getAttendanceAllByPage(Integer Page,Integer size);
    int selectAttendanceCount();

    int insert(Integer userId);
    int updateLeave(Integer userId,Integer leaveCounts);
    int updateOvertime(Integer userId,Integer overDays);

//   条件时间查询 String startTime,String endTime
    List<Attendance> queryAttendanceByConditions(Integer userId);

    List<AttendanceExcel>exportAttendance();



}
