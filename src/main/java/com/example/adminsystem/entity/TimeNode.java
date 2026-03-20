package com.example.adminsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_time_node")
public class TimeNode {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String event;
    private Integer monthNum;
    private Integer dayNum;
    private String type;
    private Date createTime;
}