package com.example.back.service;

import com.alibaba.fastjson.JSONObject;
import com.example.back.model.WorkOverTime;
import io.swagger.models.auth.In;

import java.util.List;

public interface WorkOvertimeService {


    List<WorkOverTime> findWorkOverTimeAll();
    List overTimes(Integer userId);

    String insertWorkOverTime(WorkOverTime workOverTime);

    String updateWorkOverTime(WorkOverTime workOverTime);

    String deleteWorkOverTimeById(Integer id);

    JSONObject getWorkOverTimeAllByPage(int currentPage, int size);

    JSONObject getWorkOverTimeUserAllByPage(Integer userId, int currentPage, int size);
    List<WorkOverTime>queryWorkOvertimeByConditions(String userName,String startTime, String endTime);

    List<WorkOverTime>queryUserWorkOvertimeByConditions(String startTime, String endTime);
}
