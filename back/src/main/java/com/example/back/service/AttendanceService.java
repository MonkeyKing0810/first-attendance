package com.example.back.service;

import com.alibaba.fastjson.JSONObject;
import com.example.back.model.Attendance;
import com.example.back.model.AttendanceExcel;

import java.util.List;

public interface AttendanceService {
    List<Attendance> getAttendanceAll();
    JSONObject getAttendanceAllByPage(int currentPage, int size);
//   时间查询 String startTime,String endTime
    List<Attendance> queryAttendanceByConditions(Integer userId);
    List<AttendanceExcel>exportAttendance();
}
