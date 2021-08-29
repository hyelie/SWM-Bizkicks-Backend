package com.bizkicks.backend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bizkicks.backend.entity.Consumption;
import com.bizkicks.backend.entity.Coordinate;

import org.springframework.stereotype.Repository;



@Repository
public class CoordinateRepository {
    @PersistenceContext
    private EntityManager em;

    public void saveAllCoordinatesInConsumption(Consumption consumption, List<Coordinate> coordinates){
        for (Coordinate coordinate : coordinates){
            coordinate.setRelationWithConsumption(consumption);
            em.persist(coordinate);
        }
    }

    public List<Coordinate> findAllCoordinatesInConsumption(Consumption consumption){
        String selectCoordinateQuery = "SELECT c FROM Coordinate c WHERE c.consumption = :consumption";
        List<Coordinate> coordinates = em.createQuery(selectCoordinateQuery, Coordinate.class)
                                            .setParameter("consumption", consumption)
                                            .getResultList();
        return coordinates;
    }

    public List<Coordinate> findCoordinatesInConsumptions(List<Consumption> consumptions){
        String selectCoordinateQuery = "SELECT c FROM Coordinate c WHERE c.consumption IN :consumptions ORDER BY c.consumption.id ASC, c.sequence ASC";
        List<Coordinate> coordinates = em.createQuery(selectCoordinateQuery, Coordinate.class)
                                            .setParameter("consumptions", consumptions)
                                            .getResultList();
        return coordinates;
    }
}
