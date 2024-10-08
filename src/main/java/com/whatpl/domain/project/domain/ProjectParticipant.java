package com.whatpl.domain.project.domain;

import com.whatpl.global.common.model.BaseTimeEntity;
import com.whatpl.global.common.model.Job;
import com.whatpl.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "project_participant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectParticipant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Member participant;

    @Builder
    public ProjectParticipant(Job job, Project project, Member participant) {
        this.job = job;
        this.project = project;
        this.participant = participant;
    }

    public void addRelation(@NonNull Project project) {
        this.project = project;
    }
}
