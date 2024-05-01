package com.whatpl.whatplpeople.domains;

import com.whatpl.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "searched_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchedMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member saerchMember;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "search_history_id")
    private SearchHistory searchHistory;

    public SearchedMember(Member saerchMember) {
        this.saerchMember = saerchMember;
    }
}
