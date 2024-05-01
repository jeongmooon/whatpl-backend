package com.whatpl.whatplpeople.dto;

import com.whatpl.global.common.domain.enums.Career;
import com.whatpl.global.common.domain.enums.Job;
import com.whatpl.global.common.domain.enums.Skill;
import com.whatpl.member.domain.Member;
import com.whatpl.member.domain.MemberSkill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class WhatPeopleDto {
    private Long id;
    private String nickName;
    private Job job;
    private Career career;
    private boolean portfolio;
    private Set<Skill> skills;

    public static WhatPeopleDto fromEntity(Member member){
        return WhatPeopleDto.builder()
                .id(member.getId())
                .nickName(member.getNickname())
                .job(member.getJob())
                .career(member.getCareer())
                .portfolio(!member.getMemberPortfolios().isEmpty())
                .skills(member.getMemberSkills().stream()
                        .map(MemberSkill::getSkill)
                        .collect(Collectors.toSet()))
                .build();
    }
}
