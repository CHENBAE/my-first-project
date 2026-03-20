package com.example.adminsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_party_branch")
public class PartyBranch {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String branchName;
    private Integer secretaryId;
    private Integer memberCount;
    private String remark;
    private Date createTime;
}