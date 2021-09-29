package com.bizkicks.backend.service;

import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.repository.CustomerCompanyRepository;
import com.bizkicks.backend.util.GetWithNullCheck;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CustomerCompanyService {

    @Autowired CustomerCompanyRepository customerCompanyRepository;
    @Autowired private GetWithNullCheck getWithNullCheck;

    public CustomerCompany findByCustomerCompanyName(String companyName){
        return getWithNullCheck.getCustomerCompanyWithName(customerCompanyRepository, companyName);
    }

}
