package com.whatpl.whatplpeople.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whatpl.global.common.domain.enums.Job;
import com.whatpl.global.common.domain.enums.Subject;
import com.whatpl.member.domain.Member;
import com.whatpl.project.domain.Project;
import com.whatpl.project.domain.QProject;
import com.whatpl.project.domain.enums.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.whatpl.attachment.domain.QAttachment.attachment;
import static com.whatpl.member.domain.QMember.member;
import static com.whatpl.project.domain.QProjectSkill.projectSkill;
import static com.whatpl.project.domain.QRecruitJob.recruitJob;

@RequiredArgsConstructor
public class WhatplpeopleQueryRepositoryImpl implements WhatplpeopleQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Member> findProjectWriterByJob(Job job, Set<Subject> subject, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(recruitJob.job.eq(job));
        builder.and(recruitJob.totalAmount.gt(recruitJob.currentAmount));
        builder.and(QProject.project.status.eq(ProjectStatus.RECRUITING));

        BooleanExpression subjectExpression = null;

        if(!subject.isEmpty()) {
            subjectExpression = QProject.project.subject.in(subject);
            builder.and(subjectExpression);
        }

        List<Member> content = queryFactory.select(QProject.project.writer)
                .leftJoin(QProject.project.recruitJobs, recruitJob).fetchJoin()
                .leftJoin(QProject.project.writer, member).fetchJoin()
                .where(builder)
                .orderBy(
                        subjectExpression.desc(),
                        QProject.project.subject.asc()
                )
                .distinct()
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(QProject.project.writer.count())
                .leftJoin(QProject.project.recruitJobs, recruitJob).fetchJoin()
                .leftJoin(QProject.project.writer, member).fetchJoin()
                .where(builder)
                .distinct();

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
