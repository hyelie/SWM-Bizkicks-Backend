package com.bizkicks.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bizkicks.backend.entity.Consumption;

import org.springframework.stereotype.Repository;

@Repository
public class ConsumptionRepository {
    @PersistenceContext
    private EntityManager em;


    // public List<Object[]> findConsumptionLeftJoinCoordinate(LocalDateTime startDate, LocalDateTime endDate, Integer page, Integer unit){
    //     String joinQuery = "SELECT con, cor FROM Consumption con LEFT JOIN Coordinate cor" + 
    //                         "WHERE :start_date < con.arriveTime AND con.arriveTime < :end_date" +
    //                         "ORDER BY con.arrive_time DESC cor.sequence ASC" + 
    //                         "LIMIT :page, :unit";
    //     List<Object[]> result = em.createQuery(joinQuery)
    //                                 .setParameter("start_date", startDate)
    //                                 .setParameter("end_date", endDate)
    //                                 .setParameter("page", unit)
    //                                 .setParameter("unit", page)
    //                                 .getResultList();
    //     return result;
    // }

    public Consumption save(Consumption consumption){
        em.persist(consumption);
        return consumption;
    }
}