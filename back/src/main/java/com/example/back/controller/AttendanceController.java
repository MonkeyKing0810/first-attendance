package com.example.back.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.back.model.Attendance;
import com.example.back.model.AttendanceExcel;
import com.example.back.service.AttendanceService;
import com.example.back.utils.EasyExcelUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/getAttendanceAll")
    public List<Attendance>getAttendanceAll(){
        return attendanceService.getAttendanceAll();
    }

    @ApiOperation(value = "分页查询信息")
    @RequestMapping(value = "/getAttendanceAllByPage/{currentPage}/{size}",method = RequestMethod.POST)
    public JSONObject getAttendanceAllByPage(@PathVariable("currentPage") int currentPage, @PathVariable("size") int size){
        return attendanceService.getAttendanceAllByPage(currentPage, size);
    }

    @RequestMapping(value = "/queryAttendanceByConditions/{userId}",method =  RequestMethod.POST)
    public List<Attendance> queryAttendanceByConditions(@PathVariable("userId")Integer userId)
    {
        return attendanceService.queryAttendanceByConditions(userId);
    }
    @RequestMapping(value = "/exportAttendance",method = RequestMethod.GET)
    public void exportFlight(HttpServletResponse response, String fileName) throws IOException {
        List<AttendanceExcel>users=attendanceService.exportAttendance();
        EasyExcelUtil.exportDefaultExcel(response,fileName,AttendanceExcel.class,users);
    }
}
