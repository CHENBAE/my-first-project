package com.example.adminsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_person")
public class Person {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String gender;
    private String phone;
    private Integer departmentId;
    private String position;
    private String status;
    private Date createTime;
}