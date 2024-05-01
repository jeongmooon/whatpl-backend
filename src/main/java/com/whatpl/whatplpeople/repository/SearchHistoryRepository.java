package com.whatpl.whatplpeople.repository;

import com.whatpl.member.domain.Member;
import com.whatpl.whatplpeople.domains.SearchHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    Optional<SearchHistory> findByLoginMember(Member loginMember);

    @Query("SELECT sh.searchedMembers FROM SearchHistory sh WHERE sh.loginMember = :member AND sh.createdAt >= :oneHourAgo")
    Set<Member> findSearchedMembersByLoginMember(Member member, LocalDateTime oneHourAgo);
}
