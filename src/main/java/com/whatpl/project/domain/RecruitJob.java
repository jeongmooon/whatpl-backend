package com.whatpl.project.domain;

import com.whatpl.global.common.domain.enums.Job;
import com.whatpl.global.exception.BizException;
import com.whatpl.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Entity
@Table(name = "recruit_job")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Job job;

    private Integer totalAmount;

    private Integer currentAmount;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Builder
    public RecruitJob(Job job, Integer totalAmount, Integer currentAmount) {
        this.job = job;
        this.totalAmount = totalAmount;
        this.currentAmount = currentAmount;
    }

    //==비즈니스 로직==//
    public boolean isFullJob() {
        return Objects.equals(totalAmount, currentAmount);
    }

    public void increaseCurrentAmount() {
        // 모집인원이 초과될 경우 Error
        if (isFullJob()) {
            throw new BizException(ErrorCode.RECRUIT_COMPLETED_APPLY_JOB);
        }
        this.currentAmount++;
    }
}