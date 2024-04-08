package com.whatpl.project.model;

import com.whatpl.global.common.domain.enums.Career;
import com.whatpl.global.common.domain.enums.Subject;
import com.whatpl.global.common.domain.enums.WorkTime;
import com.whatpl.project.domain.Project;
import com.whatpl.project.domain.enums.MeetingType;
import com.whatpl.project.domain.enums.UpDown;

import java.time.LocalDate;

public class ProjectFixture {

    public static Project create() {
        return Project.builder()
                .title("테스트 타이틀")
                .subject(Subject.SOCIAL_MEDIA)
                .content("<p>테스트 콘텐츠 HTML<p>")
                .profitable(false)
                .meetingType(MeetingType.ONLINE)
                .startDate(LocalDate.of(2024, 4, 1))
                .endDate(LocalDate.of(2024, 6, 30))
                .wishCareer(Career.ONE)
                .wishCareerUpDown(UpDown.UP)
                .wishWorkTime(WorkTime.TEN_TO_TWENTY)
                .build();
    }
}
