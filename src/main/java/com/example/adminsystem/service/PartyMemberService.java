package com.example.adminsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.PartyMember;

import java.util.List;

public interface PartyMemberService {
    Page<PartyMember> page(int current, int size, String name, Integer branchId);
    List<PartyMember> list();
    PartyMember getById(Integer id);
    boolean save(PartyMember partyMember);
    boolean update(PartyMember partyMember);
    boolean delete(Integer id);
}