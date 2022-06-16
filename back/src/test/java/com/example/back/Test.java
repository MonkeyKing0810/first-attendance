package com.example.back;

import com.example.back.dao.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test {
    @Autowired
    private AttendanceMapper attendanceMapper;

    @org.junit.jupiter.api.Test
    public void test(){
        attendanceMapper.insertAttendanceLeave(66);
    }
}
