package com.example.back.model;


import lombok.Data;

@Data
public class WorkOverTime {
    private Integer workOvertimeId;
    private String startTime;
    private String endTime;
    private String workOvertimeTime;
    private String workOvertimeThings;
    private Integer userId;
    private String userName;

    private Integer overDays;
}
