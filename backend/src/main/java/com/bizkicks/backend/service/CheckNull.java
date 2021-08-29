package com.bizkicks.backend.service;

import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.entity.User;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.repository.CustomerCompanyRepository;
import com.bizkicks.backend.repository.KickboardBrandRepository;
import com.bizkicks.backend.repository.UserRepository;

public class CheckNull {
    User getUserWithNullCheck(UserRepository userRepository, Long userId){
        User user = userRepository.findById(userId);
        if(user == null) throw new CustomException(ErrorCode.USER_NOT_EXIST);
        return user;
    }

    KickboardBrand getKickboardBrandWithNullCheck(KickboardBrandRepository kickboardBrandRepository, String brandName){
        KickboardBrand kickboardBrand = kickboardBrandRepository.findByBrandName(brandName);
        if(kickboardBrand == null) throw new CustomException(ErrorCode.KICKBOARD_BRAND_NOT_EXIST);
        return kickboardBrand;
    }

    CustomerCompany getCustomerCompanyWithNullCheck(CustomerCompanyRepository customerCompanyRepository, String customerCompanyName){
        CustomerCompany customerCompany = customerCompanyRepository.findByCustomerCompanyName(customerCompanyName);
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        return customerCompany;
    }
    

   


}