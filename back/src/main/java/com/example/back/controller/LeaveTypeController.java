package com.example.back.controller;

import com.example.back.model.LeaveType;
import com.example.back.service.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "leaveType")
public class LeaveTypeController {

    @Autowired
    private LeaveTypeService leaveTypeService;

    @RequestMapping(value = "/getLeaveTypeAll",method = RequestMethod.GET)
    public List<LeaveType> getLeaveTypeAll(){
        return leaveTypeService.getLeaveTypeAll();
    }
}
