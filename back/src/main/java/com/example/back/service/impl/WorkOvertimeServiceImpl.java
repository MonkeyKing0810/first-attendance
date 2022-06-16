package com.example.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.back.dao.AttendanceMapper;
import com.example.back.dao.LeaveMapper;
import com.example.back.dao.WorkOvertimeMapper;
import com.example.back.model.WorkOverTime;
import com.example.back.service.WorkOvertimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class WorkOvertimeServiceImpl implements WorkOvertimeService {
    @Autowired
    private WorkOvertimeMapper workOvertimeMapper;

    @Autowired
    private AttendanceMapper attendanceMapper;


    @Override
    public List<WorkOverTime> findWorkOverTimeAll() {
        return workOvertimeMapper.getWorkOverTimeAll();
    }

    @Override
    public List overTimes(Integer userId) {
        return workOvertimeMapper.overTimes(userId);
    }

    @Override
    public String insertWorkOverTime(WorkOverTime workOverTime) {
        String msg=null;
        int i = workOvertimeMapper.insertWorkOverTime(workOverTime);

        attendanceMapper.updateOvertime(workOverTime.getUserId(),workOverTime.getOverDays());
        if (i>0){
            msg="新增成功";

        }else {
            msg="新增失败";
        }
        return msg;
    }

    @Override
    public String updateWorkOverTime(WorkOverTime workOverTime) {
        String msg=null;
        int i = workOvertimeMapper.updateWorkOverTime(workOverTime);
        if (i>0){
            msg="修改成功";

        }else {
            msg="修改失败";
        }
        return msg;
    }

    @Override
    public String deleteWorkOverTimeById(Integer id) {
        String msg=null;
        int i = workOvertimeMapper.deleteWorkOverTimeById(id);
        if (i>0){
            msg="删除成功";

        }else {
            msg="删除失败";
        }
        return msg;
    }

    @Override
    public JSONObject getWorkOverTimeAllByPage(int currentPage, int size) {
        int Page=(currentPage-1)*size;
        JSONObject res=new JSONObject();
        List<WorkOverTime> workOverTimes=workOvertimeMapper.getWorkOverTimeAllByPage(Page,size);
        int total=workOvertimeMapper.selectCount();
        res.put("workOvertimeInfo",workOverTimes);
        res.put("total",total);
        return res;
    }

    @Override
    public JSONObject getWorkOverTimeUserAllByPage(Integer userId, int currentPage, int size) {
        int Page=(currentPage-1)*size;
        JSONObject res=new JSONObject();
        List<WorkOverTime> workOverTimeUsers=workOvertimeMapper.getWorkOverTimeUserAllByPage(userId,Page,size);
        int total=workOvertimeMapper.selectCount();
        res.put("workOvertimeUserInfo",workOverTimeUsers);
        res.put("total",total);
        return res;
    }

    @Override
    public List<WorkOverTime> queryWorkOvertimeByConditions(String userName, String startTime, String endTime) {
        return workOvertimeMapper.queryWorkOvertimeByConditions(userName,startTime,endTime);
    }

    @Override
    public List<WorkOverTime> queryUserWorkOvertimeByConditions(String startTime, String endTime) {
        return workOvertimeMapper.queryUserWorkOvertimeByConditions(startTime,endTime);
    }
}
