package com.whatpl.whatplpeople.dto;

import com.whatpl.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class FindMeWhatplpeopleResponse {
    private List<WhatPeopleDto> findMeWhatPeoples;
    private long size;
    private long totalPage;
    private long currentPage;

    public static FindMeWhatplpeopleResponse fromPageMember(Page<Member> pageMember){
        return FindMeWhatplpeopleResponse.builder()
                .size(pageMember.getSize())
                .totalPage(pageMember.getTotalPages())
                .currentPage(pageMember.getNumber()+1)
                .findMeWhatPeoples(pageMember.stream()
                        .map(WhatPeopleDto::fromEntity)
                        .toList())
                .build();
    }
}
