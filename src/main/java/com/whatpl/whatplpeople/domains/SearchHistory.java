package com.whatpl.whatplpeople.domains;

import com.whatpl.global.common.BaseTimeEntity;
import com.whatpl.member.domain.Member;
import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "search_history")
public class SearchHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member loginMember;

    @OneToMany(mappedBy = "searchHistory")
    private Set<SearchedMember> searchedMembers = new HashSet<>();

    @Builder
    public SearchHistory(Member loginMember, Set<SearchedMember> searchedMembers) {
        this.loginMember = loginMember;
        this.searchedMembers = searchedMembers;
    }

    public void addSeachedMember(SearchedMember searchedMember) {
        this.searchedMembers.add(searchedMember);
        searchedMember.setSearchHistory(this);
    }

    public void updateSearchedMembers(Set<Member> members) {
        searchedMembers.clear();
        if(!Collections.isEmpty(members)) members.stream().map(SearchedMember::new).forEach(this::addSeachedMember);
    }
}
