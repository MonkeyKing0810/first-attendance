package com.example.back.model;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@Data
public class Leave  implements Serializable {
    private Integer leaveId;
    private Integer leaveTypeId;
    private String leaveTypeName;
    private String startTime;
    private String endTime;
    private String times;
    private String status;
    private Boolean showIf;
    private String cancelThings;

    private Integer leaveCounts;
    private String leaveThings;
    private String leaveUrl;
    private Integer userId;
    private String userName;


}
