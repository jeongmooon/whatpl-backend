package com.whatpl.domain.member.domain;

import com.whatpl.global.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Entity
@Table(name = "member_reference")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberReference extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberReference(String reference) {
        this.reference = reference;
    }

    public void addRelation(@NonNull Member member) {
        this.member = member;
    }
}
