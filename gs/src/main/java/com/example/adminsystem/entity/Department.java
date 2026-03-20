package com.example.adminsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_department")
public class Department {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String departmentName;
    private Integer leaderId;
    private Integer sort;
    private Date createTime;
}