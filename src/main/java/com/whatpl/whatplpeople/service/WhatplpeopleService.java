package com.whatpl.whatplpeople.service;

import com.whatpl.global.common.domain.enums.Job;
import com.whatpl.global.common.domain.enums.Subject;
import com.whatpl.global.exception.BizException;
import com.whatpl.global.exception.ErrorCode;
import com.whatpl.member.domain.Member;
import com.whatpl.member.domain.MemberSubject;
import com.whatpl.member.repository.MemberRepository;
import com.whatpl.project.repository.ProjectRepository;
import com.whatpl.whatplpeople.dto.WhatPeopleDto;
import com.whatpl.whatplpeople.dto.FindMeWhatplpeopleResponse;
import com.whatpl.whatplpeople.repository.WhatplpeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WhatplpeopleService {

    private final MemberRepository memberRepository;
    private final WhatplpeopleRepository whatplpeopleRepository;

    /*
    *  조건이 남은 직무
    *  */
    public FindMeWhatplpeopleResponse findMeWhatPeople(Long memberId,  Pageable pageable){
        Member me = memberRepository.findById(memberId)
                .orElseThrow(() -> new BizException(ErrorCode.NOT_FOUND_MEMBER));

        return FindMeWhatplpeopleResponse.fromPageMember(whatplpeopleRepository.findProjectWriterByJob(me.getJob()
                ,me.getMemberSubjects().stream()
                        .map(MemberSubject::getSubject)
                        .collect(Collectors.toSet())
                ,pageable));
    }
}
