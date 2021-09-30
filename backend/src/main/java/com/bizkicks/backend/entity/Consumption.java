package com.bizkicks.backend.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bizkicks.backend.auth.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
    indexes = @Index(name="usage_index", columnList = "member_id")
)
public class Consumption{
    @Id
    @Column(name = "consumption_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime departTime;

    @Column(nullable = false)
    private LocalDateTime arriveTime;

    @Column(nullable = false)
    private Integer cycle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // this can be removed when not needed.
    public void setRelationWithMember(Member member){
        this.member = member;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private KickboardBrand KickboardBrand;
    
    public void setRelationWithKickboardBrand(KickboardBrand kickboardBrand){
        this.KickboardBrand = kickboardBrand;
    }

    @Builder
    public Consumption(LocalDateTime departTime, LocalDateTime arriveTime, Integer cycle, Member member){
        if(departTime == null || arriveTime == null || cycle == null){
            throw new IllegalStateException("Cannot create consumption due to necessary value is null");
        }
        this.departTime = departTime;
        this.arriveTime = arriveTime;
        this.cycle = cycle;
        this.member = member;
    }
}
