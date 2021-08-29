package com.bizkicks.backend.entity;

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
import javax.persistence.UniqueConstraint;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
    indexes = @Index(name="coordinate_index", columnList = "consumption_id, sequence"),
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"consumption_id", "sequence"})
    }
)
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;

    @Column(name = "sequence", nullable = false)
    private Long sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumption_id")
    private Consumption consumption;

    // this can be removed when not needed.
    public void setRelationWithConsumption(Consumption consumption){
        this.consumption = consumption;
    }

    @Builder
    public Coordinate(Double longitude, Double latitude, Long sequence, Consumption consumption){
        if(longitude == null || latitude == null || sequence == null){
            throw new IllegalStateException("Cannot create coordinate due to necessary value is null");
        }
        this.longitude = longitude;
        this.latitude = latitude;
        this.sequence = sequence;
        this.consumption = consumption;
    }
}
