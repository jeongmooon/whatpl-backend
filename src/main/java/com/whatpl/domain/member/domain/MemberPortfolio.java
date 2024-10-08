package com.whatpl.domain.member.domain;

import com.whatpl.domain.attachment.domain.Attachment;
import com.whatpl.global.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Entity
@Table(name = "member_portfolio")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPortfolio extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberPortfolio(Attachment attachment) {
        this.attachment = attachment;
    }

    public void addRelation(@NonNull Member member) {
        this.member = member;
    }
}
