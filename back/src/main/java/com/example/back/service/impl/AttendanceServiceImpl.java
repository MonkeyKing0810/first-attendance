package com.example.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.back.dao.AttendanceMapper;
import com.example.back.model.Attendance;
import com.example.back.model.AttendanceExcel;
import com.example.back.model.Leave;
import com.example.back.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Override
    public List<Attendance> getAttendanceAll() {
        return attendanceMapper.getAttendanceAll();
    }
    @Override
    public JSONObject getAttendanceAllByPage(int currentPage, int size) {
        int Page=(currentPage-1)*size;
        List<Attendance> attendances = attendanceMapper.getAttendanceAllByPage(Page, size);
        int total = attendanceMapper.selectAttendanceCount();

        JSONObject res=new JSONObject();
        res.put("attendanceInfo",attendances);
        res.put("total",total);


        return res;
    }
//    时间String startTime, String endTime
    @Override
    public List<Attendance> queryAttendanceByConditions(Integer userId) {
        return attendanceMapper.queryAttendanceByConditions(userId);
    }

    @Override
    public List<AttendanceExcel> exportAttendance() {
        return attendanceMapper.exportAttendance();
    }
}
