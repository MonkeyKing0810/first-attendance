package com.example.back.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.back.model.Leave;
import com.example.back.model.User;
import com.example.back.model.WorkOverTime;
import com.example.back.service.WorkOvertimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "加班管理相关接口")
@RestController
@RequestMapping(value = "/workOvertime")
public class WorkOvertimeController {

    @Autowired
    private WorkOvertimeService workOvertimeService;

    /**
     * 分页查询
     */
    @PostMapping("/getWorkOverTimeAllByPage/{currentPage}/{size}")
    public JSONObject getWorkOverTimeAllByPage(@PathVariable("currentPage") int currentPage, @PathVariable("size") int size) {
        return workOvertimeService.getWorkOverTimeAllByPage(currentPage, size);
    }

    @ApiOperation(value = "查询全部加班时间信息")
    @RequestMapping(value = "/getOverTimes/{userId}",method = RequestMethod.POST)
    public List<Leave> getOverTimes(@PathVariable("userId")Integer userId){
        return workOvertimeService.overTimes(userId);
    }
    /**
     * 员工分页查询
     */
    @PostMapping("/getWorkOverTimeUserAllByPage/{userId}/{currentPage}/{size}")
    public JSONObject getWorkOverTimeUserAllByPage(@PathVariable("userId")Integer userId, @PathVariable("currentPage") int currentPage, @PathVariable("size") int size) {
        return workOvertimeService.getWorkOverTimeUserAllByPage(userId,currentPage, size);
    }

    @ApiOperation(value = "查询所有加班信息")
    @RequestMapping(value = "/getWorkOverTimeAll",method = RequestMethod.GET)
    public List<WorkOverTime> getWorkOverTimeAll(){
        return workOvertimeService.findWorkOverTimeAll();
    }

    @ApiOperation(value = "根据条件查询加班信息")
    @RequestMapping(value = "/queryWorkOvertimeByConditions/{userName}/{startTime}/{endTime}",method = RequestMethod.POST)
    public List<WorkOverTime> queryWorkOvertimeByConditions(@PathVariable("userName") String userName,
                                              @PathVariable("startTime") String startTime,
                                              @PathVariable("endTime") String endTime){
        return workOvertimeService.queryWorkOvertimeByConditions(userName,startTime,endTime);
    }
    @ApiOperation(value = "根据条件查询用户加班信息")
    @RequestMapping(value = "/queryUserWorkOvertimeByConditions/{startTime}/{endTime}",method = RequestMethod.POST)
    public List<WorkOverTime> queryUserWorkOvertimeByConditions(
                                                            @PathVariable("startTime") String startTime,
                                                            @PathVariable("endTime") String endTime){
        return workOvertimeService.queryUserWorkOvertimeByConditions(startTime,endTime);
    }
    @ApiOperation(value = "新增用户")
    @RequestMapping(value = "/insertWorkOverTime",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String insertWorkOverTime(@RequestBody WorkOverTime workOverTime){
        return workOvertimeService.insertWorkOverTime(workOverTime);
    }

    @ApiOperation(value = "编辑用户")
    @RequestMapping(value = "/updateWorkOverTime",method = RequestMethod.PUT,produces = "application/json;charset=UTF-8")
    public String updateWorkOverTime(@RequestBody WorkOverTime workOverTime){
        return workOvertimeService.updateWorkOverTime(workOverTime);
    }

    @ApiOperation(value = "删除一个用户")
    @RequestMapping(value = "/deleteWorkOverTimeById/{id}",method = RequestMethod.DELETE,produces = "application/json;charset=UTF-8")
    public String deleteWorkOverTimeById(@PathVariable("id")Integer id){
        return workOvertimeService.deleteWorkOverTimeById(id);

    }
}
