package com.example.back.model;

import lombok.Data;

@Data
public class Attendance {
    private Integer recordId;
    private Integer userId;
    private String userName;
    private Integer requiredDays;
    private Integer actualDays;
    private Integer overtimeDays;

}
