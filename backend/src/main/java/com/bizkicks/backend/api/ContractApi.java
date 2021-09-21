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
import com.bizkicks.backend.service.CustomerCompanyService;
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

    private final CustomerCompanyService customerCompanyService;

    @GetMapping("manage/contracts")
    public ResponseEntity<Object> showContracts() {
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        String type = customerCompany.getType();
        if (type == null){
            throw new CustomException(ErrorCode.COMPANY_NOT_EXIST); // 수정해야함
        }
        else if (type.equals("plan")){
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
        else if (type.equals("membership")){
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

        return new ResponseEntity<Object>(HttpStatus.OK); // 수정해야함
        // 에러코드로 수정
    }

    @PostMapping("manage/contracts")
    public ResponseEntity<Object> saveContracts(@RequestBody ContractDto<ContractDto.PlanPostDto> planDto) {
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        if (planDto.getType().equals("membership")){
            membershipService.saveMembership(customerCompany, planDto);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        else if (planDto.getType().equals("plan")){
            planService.savePlan(customerCompany, planDto);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        return new ResponseEntity<Object>(HttpStatus.OK); // 수정해야함
        // 에러코드로 수정
    }
    
    @PutMapping("manage/contracts")
    public ResponseEntity<Object> updateContract(@RequestBody ContractDto<ContractDto.PlanPostDto> planDto){
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        if(planDto.getType().equals("membership")){
            membershipService.updateMembership(customerCompany, planDto);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        else if(planDto.getType().equals("plan")){
            planService.updatePlan(customerCompany, planDto);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        return new ResponseEntity<Object>(HttpStatus.OK); // todo
        //에러코드로 변경
    }

    @DeleteMapping("manage/contracts")
    public ResponseEntity<Object> deleteContracts(@RequestBody ContractDto contractDto){
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        if (contractDto.getType().equals("membership")){
            membershipService.delete(customerCompany);

            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        else if (contractDto.getType().equals("plan")){
            planService.delete(customerCompany, contractDto.getList());

            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT); // todo
    }

}
