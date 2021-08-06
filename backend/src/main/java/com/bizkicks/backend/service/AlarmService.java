package com.bizkicks.backend.service;

import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.repository.AlarmRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AlarmService {

    @Autowired AlarmRepository alarmRepository;

    @Transactional
    public List<Alarm> findAlarms(String customerCompanyName){
        System.out.println("service : " + customerCompanyName);
        return alarmRepository.findByCustomerCompanyName(customerCompanyName);
    }

    @Transactional
    public void updateAlarms(List<Alarm> alarms){
        alarmRepository.deleteAll();
        alarmRepository.saveAll(alarms);
    }


}
