package com.bizkicks.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bizkicks.backend.entity.Consumption;
import com.bizkicks.backend.entity.User;
import com.bizkicks.backend.filter.DateFilter;
import com.bizkicks.backend.filter.PagingFilter;

import org.springframework.stereotype.Repository;

@Repository
public class ConsumptionRepository {
    @PersistenceContext
    private EntityManager em;


    public List<Consumption> findByFilter(User user, DateFilter dateFilter, PagingFilter pagingFilter){
        String filterQuery = "SELECT c FROM Consumption c WHERE c.user = :user AND :start_date < c.arriveTime AND c.arriveTime < :end_date ORDER BY c.id ASC";
        List<Consumption> consumptions = em.createQuery(filterQuery, Consumption.class)
                                            .setParameter("user", user)
                                            .setParameter("start_date", dateFilter.getStartDate())
                                            .setParameter("end_date", dateFilter.getEndDate())
                                            .setFirstResult(pagingFilter.getPage()-1)
                                            .setMaxResults(pagingFilter.getUnit())
                                            .getResultList();
        return consumptions;
    }

    public Consumption save(Consumption consumption){
        em.persist(consumption);
        return consumption;
    }
}