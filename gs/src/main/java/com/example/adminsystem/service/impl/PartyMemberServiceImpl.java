package com.example.adminsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.adminsystem.entity.PartyMember;
import com.example.adminsystem.mapper.PartyMemberMapper;
import com.example.adminsystem.service.PartyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyMemberServiceImpl implements PartyMemberService {

    @Autowired
    private PartyMemberMapper partyMemberMapper;

    @Override
    public Page<PartyMember> page(int current, int size, String name, Integer branchId) {
        Page<PartyMember> page = new Page<>(current, size);
        QueryWrapper<PartyMember> wrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like("name", name);
        }
        if (branchId != null) {
            wrapper.eq("branch_id", branchId);
        }
        return partyMemberMapper.selectPage(page, wrapper);
    }

    @Override
    public List<PartyMember> list() {
        return partyMemberMapper.selectList(null);
    }

    @Override
    public PartyMember getById(Integer id) {
        return partyMemberMapper.selectById(id);
    }

    @Override
    public boolean save(PartyMember partyMember) {
        return partyMemberMapper.insert(partyMember) > 0;
    }

    @Override
    public boolean update(PartyMember partyMember) {
        return partyMemberMapper.updateById(partyMember) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return partyMemberMapper.deleteById(id) > 0;
    }
}