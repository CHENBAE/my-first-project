package com.example.adminsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.PartyBranch;

import java.util.List;

public interface PartyBranchService {
    Page<PartyBranch> page(int current, int size, String branchName);
    List<PartyBranch> list();
    PartyBranch getById(Integer id);
    boolean save(PartyBranch partyBranch);
    boolean update(PartyBranch partyBranch);
    boolean delete(Integer id);
}