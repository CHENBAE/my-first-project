package com.example.adminsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_affair")
public class Affair {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private Integer initiatorId;
    private Integer responsibleId;
    private Date affairTime;
    private Date createTime;
}