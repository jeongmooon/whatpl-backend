package com.whatpl.whatplpeople.service;

import com.whatpl.global.exception.BizException;
import com.whatpl.global.exception.ErrorCode;
import com.whatpl.member.domain.Member;
import com.whatpl.member.domain.MemberSubject;
import com.whatpl.member.repository.MemberRepository;

import com.whatpl.whatplpeople.domains.SearchHistory;
import com.whatpl.whatplpeople.dto.FindMeWhatplpeopleResponse;
import com.whatpl.whatplpeople.repository.SearchHistoryRepository;
import com.whatpl.whatplpeople.repository.WhatplpeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WhatplpeopleService {

    private final MemberRepository memberRepository;
    private final WhatplpeopleRepository whatplpeopleRepository;
    private final SearchHistoryRepository searchHistoryRepository;

    /*
    *  조건이 남은 직무
    *  */
    public FindMeWhatplpeopleResponse findMeWhatPeople(Long memberId,  String page){
        Member me = memberRepository.findById(memberId)
                .orElseThrow(() -> new BizException(ErrorCode.NOT_FOUND_MEMBER));

        Page<Member> pageMember =  whatplpeopleRepository.findProjectWriterByJob(me.getJob()
                ,me.getMemberSubjects().stream()
                        .map(MemberSubject::getSubject)
                        .collect(Collectors.toSet())
                ,PageRequest.of(Integer.parseInt(page == null ? "0" : page), 10)
                ,page==null ? null : searchHistoryRepository.findSearchedMembersByLoginMember(me, LocalDateTime.now().minusHours(1)).stream().map(Member::getId).collect(Collectors.toSet())
        );

        if(page != null){
            SearchHistory searchHistory = searchHistoryRepository.findByLoginMember(me)
                    .orElseGet(()-> SearchHistory.builder()
                            .loginMember(me)
                            .build());
            searchHistory.updateSearchedMembers(pageMember.toSet());
        }

        return FindMeWhatplpeopleResponse.fromPageMember(pageMember);
    }
}
