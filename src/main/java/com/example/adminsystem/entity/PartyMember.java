package com.example.adminsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_party_member")
public class PartyMember {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer branchId;
    private Date joinDate;
    private Date createTime;
}