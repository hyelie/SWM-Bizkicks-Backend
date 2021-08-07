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
    public void updateAlarms(String customerCompanyName, List<Alarm> alarms){
        alarmRepository.deleteAllAlarmsInCustomerCompany(customerCompanyName);
        alarmRepository.saveAll(alarms);
        //service가 dto를 entity로 반환하게 하기.
    }


}
