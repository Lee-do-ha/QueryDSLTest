package com.example.querydsl.member.controller;

import com.example.querydsl.member.dto.response.MemberSearchCond;
import com.example.querydsl.member.dto.response.MemberTeamDto;
import com.example.querydsl.member.repository.MemberQueryRepository;
import com.example.querydsl.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCond cond){
        return memberQueryRepository.searchByWhere(cond);
    }

    @GetMapping("/v2/members")
    public Page<MemberTeamDto> searchMemberV2(MemberSearchCond cond, Pageable pageable){
        return memberRepository.searchPageSimple(cond, pageable);
    }

    @GetMapping("/v3/members")
    public Page<MemberTeamDto> searchMemberV3(MemberSearchCond cond, Pageable pageable){
        return memberRepository.searchPageComplex(cond, pageable);
    }

}
