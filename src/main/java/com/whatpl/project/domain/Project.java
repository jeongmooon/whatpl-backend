package com.whatpl.project.domain;

import com.whatpl.attachment.domain.Attachment;
import com.whatpl.global.common.BaseTimeEntity;
import com.whatpl.global.common.domain.enums.Subject;
import com.whatpl.member.domain.Member;
import com.whatpl.project.domain.enums.MeetingType;
import com.whatpl.project.domain.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Boolean profitable;

    private Integer term;

    private Long views;

    @Setter
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    private MeetingType meetingType;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectSkill> projectSkills = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecruitJob> recruitJobs = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectComment> projectComments = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectLike> projectLikes = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectParticipant> projectParticipants = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "represent_image_id")
    private Attachment representImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @Builder
    public Project(String title, Boolean profitable, Integer term,
                   Subject subject, ProjectStatus status, MeetingType meetingType,
                   String content, Attachment representImage) {
        this.title = title;
        this.profitable = profitable;
        this.term = term;
        this.status = status;
        this.subject = subject;
        this.meetingType = meetingType;
        this.content = content;
        this.representImage = representImage;
        this.views = 0L;
    }

    //==연관관계 메서드==//
    public void addProjectSkill(ProjectSkill projectSkill) {
        if (projectSkill == null) {
            return;
        }
        this.projectSkills.add(projectSkill);
        projectSkill.setProject(this);
    }

    public void addRecruitJob(RecruitJob recruitJob) {
        if (recruitJob == null) {
            return;
        }
        this.recruitJobs.add(recruitJob);
        recruitJob.setProject(this);
    }

    public void addRepresentImageAndWriter(Attachment representImage, Member writer) {
        this.representImage = representImage;
        this.writer = writer;
    }

    //==비즈니스 로직==//
    public void increaseViews() {
        if (this.views == null) {
            this.views = 0L;
        }
        this.views++;
    }
}
