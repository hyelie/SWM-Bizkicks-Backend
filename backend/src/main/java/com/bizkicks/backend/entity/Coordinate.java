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

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
    indexes = @Index(name="coordinate_index", columnList = "usage_id, sequence")
)
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double attitude;

    @Column(name = "sequence", nullable = false)
    private Long sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_id")
    private Consumption consumption;

    // this can be removed when not needed.
    public void setRelationWithConsumption(Consumption consumption){
        this.consumption = consumption;
    }

    @Builder
    public Coordinate(Double longitude, Double attitude, Long sequence, Consumption consumption){
        if(longitude == null || attitude == null || sequence == null){
            throw new IllegalStateException("Cannot create coordinate due to necessary value is null");
        }
        this.longitude = longitude;
        this.attitude = attitude;
        this.sequence = sequence;
        this.consumption = consumption;
    }
}
