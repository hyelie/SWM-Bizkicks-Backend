package com.bizkicks.backend.api;

import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.service.MemberService;
import com.bizkicks.backend.dto.ContractDto;
import com.bizkicks.backend.dto.ListDto;
import com.bizkicks.backend.dto.PlanDto;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.Membership;
import com.bizkicks.backend.entity.Plan;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.service.MembershipService;
import com.bizkicks.backend.service.PlanService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ContractApi {

    private final PlanService planService;
    private final MembershipService membershipService;
    @Autowired private MemberService memberService;

    @GetMapping("/manage/contracts/plan")
    public ResponseEntity<Object> showPlans(){
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        List<Plan> plans = planService.findPlan(customerCompany);
        if(plans.isEmpty()){
            List<ContractDto.PlanGetDto> collect = new ArrayList<>();

            ContractDto contractDto = ContractDto.<ContractDto.PlanGetDto>builder()
                    .type(null)
                    .list(collect)
                    .build();
            return new ResponseEntity<Object>(contractDto, HttpStatus.OK);
        }

        List<ContractDto.PlanGetDto> collect = plans.stream()
                .map(m -> new ContractDto.PlanGetDto(m.getKickboardBrand().getBrandName(),m.getStartDate(), m.getKickboardBrand().getPricePerHour(), m.getKickboardBrand().getDistricts(), m.getKickboardBrand().getHelmet(), m.getKickboardBrand().getInsurance(), m.getTotalTime(), m.getUsedTime()))
                .collect(Collectors.toList());

        ContractDto contractDto = ContractDto.<ContractDto.PlanGetDto>builder()
                .type("plan")
                .list(collect)
                .build();

        return new ResponseEntity<Object>(contractDto, HttpStatus.OK);
    }

    @GetMapping("manage/contracts/membership")
    public ResponseEntity<Object> showMemberships(){
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        List<Membership> memberships = membershipService.findMembership(customerCompany);
        if (memberships.isEmpty()){

            List<ContractDto.MembershipGetDto> collect = new ArrayList<>();

            ContractDto contractDto = ContractDto.<ContractDto.MembershipGetDto>builder()
                    .type("membership")
                    .startdate(null)
                    .duedate(null)
                    .list(collect)
                    .build();

            return new ResponseEntity<Object>(contractDto,HttpStatus.OK);
        }

        LocalDate startDate = memberships.get(0).getStartDate();
        LocalDate duedate = memberships.get(0).getDuedate();

        List<ContractDto.MembershipGetDto> collect = memberships.stream()
                .map(m -> new ContractDto.MembershipGetDto(m.getKickboardBrand().getBrandName(), m.getKickboardBrand().getDistricts(), m.getKickboardBrand().getInsurance(), m.getKickboardBrand().getHelmet(), m.getUsedTime()))
                .collect(Collectors.toList());

        ContractDto contractDto = ContractDto.<ContractDto.MembershipGetDto>builder()
                .type("membership")
                .startdate(startDate)
                .duedate(duedate)
                .list(collect)
                .build();

        return new ResponseEntity<Object>(contractDto, HttpStatus.OK);

    }



    @PostMapping("manage/contracts/plan")
    public ResponseEntity<Object> savePlan(@RequestBody ContractDto<ContractDto.PlanPostDto> planDto){
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        
        planService.savePlan(customerCompany, planDto);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @PostMapping("manage/contracts/membership")
    public ResponseEntity<Object> saveMembership(@RequestBody ContractDto contractMembership){
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        membershipService.saveMembership(customerCompany, contractMembership);
        return new ResponseEntity<Object>(HttpStatus.OK);

    }

    @PutMapping("/manage/contracts/membership")
    public ResponseEntity<Object> updateMembership(@RequestBody ContractDto contractMembership){
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        membershipService.updateMembership(customerCompany, contractMembership);
        return new ResponseEntity<Object>(HttpStatus.OK);

    }

    @PutMapping("/manage/contracts/plan")
    public ResponseEntity<Object> updatePlan(@RequestBody ContractDto<ContractDto.PlanPostDto> planDto){
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        planService.updatePlan(customerCompany, planDto);
        return new ResponseEntity<Object>(HttpStatus.OK);

    }

    @DeleteMapping("/manage/contracts/membership")
    public ResponseEntity<Object> deleteMembership(){
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        membershipService.delete(customerCompany);

        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/manage/contracts/plan")
    public ResponseEntity<Object> deletePlan(@RequestBody ListDto listDto){
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
                                                     

        planService.delete(customerCompany, listDto.getList());

        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

}
