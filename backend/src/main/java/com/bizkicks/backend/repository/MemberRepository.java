package com.bizkicks.backend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bizkicks.backend.entity.Member;

import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    public Member findById(Long Id){
        String selectMemberQuery = "SELECT m FROM Member m WHERE m.id = :id";
        List<Member> members = em.createQuery(selectMemberQuery, Member.class)
                                                    .setParameter("id", Id)
                                                    .getResultList();
        if(members.isEmpty()) return null;
        else return members.get(0);
    }

    public Member findByMemberId(String memberId){
        String selectMemberQuery = "SELECT m FROM Member m WHERE m.memberId = :member_id";
        List<Member> members = em.createQuery(selectMemberQuery, Member.class)
                                                    .setParameter("member_id", memberId)
                                                    .getResultList();
        if(members.isEmpty()) return null;
        else return members.get(0);
    }

    public Member findByPhoneNumber(String phoneNumber){
        String selectMemberQuery = "SELECT m FROM Member m WHERE m.phoneNumber = :phone_number";
        List<Member> members = em.createQuery(selectMemberQuery, Member.class)
                                                    .setParameter("phone_number", phoneNumber)
                                                    .getResultList();
        if(members.isEmpty()) return null;
        else return members.get(0);
    }
    
}
