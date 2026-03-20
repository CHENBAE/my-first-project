package com.example.adminsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_meeting")
public class Meeting {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private Date meetingTime;
    private String location;
    private Integer hostId;
    private Date createTime;
}