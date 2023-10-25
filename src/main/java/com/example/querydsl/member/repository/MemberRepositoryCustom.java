package com.example.querydsl.member.repository;

import com.example.querydsl.member.dto.response.MemberSearchCond;
import com.example.querydsl.member.dto.response.MemberTeamDto;
import com.example.querydsl.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {

    List<MemberTeamDto> searchByWhere(MemberSearchCond cond);
    Page<MemberTeamDto> searchPageSimple(MemberSearchCond cond, Pageable pageable);
    Page<MemberTeamDto> searchPageComplex(MemberSearchCond cond, Pageable pageable);

}
