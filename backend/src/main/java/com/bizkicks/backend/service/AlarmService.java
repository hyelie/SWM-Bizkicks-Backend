package com.bizkicks.backend.service;

import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
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
        CustomerCompany customerCompany = alarmRepository.isCustomerCompanyExist(customerCompanyName);
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        return alarmRepository.findByCustomerCompany(customerCompany);
    }

    @Transactional
    public void updateAlarms(String customerCompanyName, List<Alarm> alarms){
        CustomerCompany customerCompany = alarmRepository.isCustomerCompanyExist(customerCompanyName);
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        //alarmRepository.deleteAllAlarmsInCustomerCompany(customerCompanyName);
        alarmRepository.saveAllAlarmsInCustomerCompany(customerCompany, alarms);
    }


}
