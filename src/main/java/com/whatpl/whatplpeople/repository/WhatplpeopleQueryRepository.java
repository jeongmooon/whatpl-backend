package com.whatpl.whatplpeople.repository;

import com.whatpl.global.common.domain.enums.Job;
import com.whatpl.global.common.domain.enums.Subject;
import com.whatpl.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface WhatplpeopleQueryRepository {
    Page<Member> findProjectWriterByJob(Job job, Set<Subject> subject, Pageable pageable, Set<Long> retrievedUserId);
}
