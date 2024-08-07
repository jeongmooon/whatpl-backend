package com.whatpl.project.model;

import com.whatpl.global.common.domain.enums.Job;
import com.whatpl.member.domain.Member;
import com.whatpl.project.domain.Apply;
import com.whatpl.project.domain.Project;
import com.whatpl.global.common.domain.enums.ApplyStatus;

public class ApplyFixture {

    public static Apply waiting(Job job, Member applicant, Project project) {
        return Apply.builder()
                .job(job)
                .status(ApplyStatus.WAITING)
                .applicant(applicant)
                .project(project)
                .build();
    }

    public static Apply accepted(Job job, Member applicant, Project project) {
        return Apply.builder()
                .job(job)
                .status(ApplyStatus.ACCEPTED)
                .applicant(applicant)
                .project(project)
                .build();
    }
}
