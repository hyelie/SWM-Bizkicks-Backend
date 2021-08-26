package com.bizkicks.backend.entity;

import java.time.LocalDate;
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

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
    indexes = @Index(name="usage_index", columnList = "user_id")
)
public class Consumption{
    @Id
    @Column(name = "usage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime departTime;

    @Column(nullable = false)
    private LocalDateTime arriveTime;

    @Column(nullable = false)
    private Integer cycle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // this can be removed when not needed.
    public void setRelationWithUser(User user){
        this.user = user;
    }

    // add below code and index when brand is added.
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "brand_id")
    // private KickboardBrand KickboardBrand;
    // public void setRelationWithKickboardBrand(KickboardBrand kickboardBrand){
    //     this.KickboardBrand = kickboardBrand;
    // }

    @Builder
    public Consumption(LocalDateTime departTime, LocalDateTime arriveTime, Integer cycle, User user){
        if(departTime == null || arriveTime == null || cycle == null){
            throw new IllegalStateException("Cannot create consumption due to necessary value is null");
        }
        this.departTime = departTime;
        this.arriveTime = arriveTime;
        this.cycle = cycle;
        this.user = user;
    }
}
