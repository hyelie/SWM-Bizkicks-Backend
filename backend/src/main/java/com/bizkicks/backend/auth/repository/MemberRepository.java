package com.bizkicks.backend.auth.repository;

import java.util.Optional;

import com.bizkicks.backend.auth.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    Optional<Member> findById(Long id);
    Optional<Member> findByMemberId(String memberId);
    boolean existsByMemberId(String memberId);
    Optional<Member> findByPhoneNumber(String phoneNumber);
    @Query("SELECT m FROM Member m JOIN FETCH m.customerCompany WHERE m.memberId = :member_id")
    Optional<Member> findByMemberIdWithCustomerCompany(@Param("member_id") String memberId);
}
