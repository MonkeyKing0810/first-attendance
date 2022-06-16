package com.example.back.service.impl;

import com.example.back.dao.LeaveMapper;
import com.example.back.dao.LeaveTypeMapper;
import com.example.back.model.LeaveType;
import com.example.back.service.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {
    @Autowired
    private LeaveTypeMapper leaveTypeMapper;
    @Override
    public List<LeaveType> getLeaveTypeAll() {
        return leaveTypeMapper.getLeaveTypeAll();
    }
}
