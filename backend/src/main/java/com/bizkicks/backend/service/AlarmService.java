package com.bizkicks.backend.service;

import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.repository.AlarmRepository;
import com.bizkicks.backend.repository.CustomerCompanyRepository;
import com.bizkicks.backend.util.GetWithNullCheck;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AlarmService {
    @Autowired AlarmRepository alarmRepository;
    @Autowired CustomerCompanyRepository customerCompanyRepository;

    @Transactional
    public List<Alarm> findAlarms(CustomerCompany customerCompany){
        return alarmRepository.findByCustomerCompany(customerCompany);
    }


    @Transactional
    public void updateAlarms(CustomerCompany customerCompany, List<Alarm> alarms){
        alarmRepository.deleteAllAlarmsInCustomerCompany(customerCompany);
        alarmRepository.saveAllAlarmsInCustomerCompany(customerCompany, alarms);
    }
}
