package com.example.back.dao;

import com.example.back.model.Leave;
import com.example.back.model.WorkOverTime;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WorkOvertimeMapper {
    List<WorkOverTime> getWorkOverTimeAll();

    int insertWorkOverTime(WorkOverTime workOverTime);

    int insertOvertimeUserId(Integer userId);

    int updateWorkOverTime(WorkOverTime workOverTime);

    int deleteWorkOverTimeById(Integer id);
    List overTimes(Integer userId);

    List<WorkOverTime>getWorkOverTimeAllByPage(int Page, int size);


    List<WorkOverTime>getWorkOverTimeUserAllByPage(Integer userId,int Page, int size);
    int selectCount();

    List<WorkOverTime>queryWorkOvertimeByConditions(String userName,String startTime, String endTime);

    List<WorkOverTime> queryUserWorkOvertimeByConditions(String startTime, String endTime);
}
