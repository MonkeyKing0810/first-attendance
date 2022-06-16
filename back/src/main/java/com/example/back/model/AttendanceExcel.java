package com.example.back.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class AttendanceExcel {

    @ExcelProperty(value = "姓名",index = 0)
    private String userName;

    @ExcelProperty(value = "应出勤天数",index = 1)
    private Integer requiredDays;

    @ExcelProperty(value = "实际出勤天数（不含加班）",index = 2)
    private Integer actualDays;

    @ExcelProperty(value = "加班天数",index = 3)
    private Integer overtimeDays;
}