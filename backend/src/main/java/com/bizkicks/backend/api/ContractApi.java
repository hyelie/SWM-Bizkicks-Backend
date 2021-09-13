package com.bizkicks.backend.api;

import com.bizkicks.backend.dto.ContractDto;
import com.bizkicks.backend.dto.ListDto;
import com.bizkicks.backend.dto.PlanDto;
import com.bizkicks.backend.entity.Membership;
import com.bizkicks.backend.entity.Plan;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.service.MembershipService;
import com.bizkicks.backend.service.PlanService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/manage/contracts/plan")
    public ResponseEntity<Object> showPlans(@CookieValue(name = "company", required = false) String belongCompany){
        if (belongCompany == null) throw new CustomException(ErrorCode.INVALID_TOKEN);

        List<Plan> plans = planService.findPlan(belongCompany);
        if(plans.isEmpty()){

            List<ContractDto.PlanGetDto> collect = new ArrayList<>();

            ContractDto contractDto = ContractDto.<ContractDto.PlanGetDto>builder()
                    .type(null)
                    .list(collect)
                    .build();
            return new ResponseEntity<Object>(contractDto, HttpStatus.OK);
        }

        List<ContractDto.PlanGetDto> collect = plans.stream()
                .map(m -> new ContractDto.PlanGetDto(m.getKickboardBrand().getBrandName(),m.getStartDate(), m.getKickboardBrand().getPricePerHour(), m.getKickboardBrand().getDistricts(), m.getKickboardBrand().isHelmet(), m.getKickboardBrand().isInsurance(), m.getTotalTime(), m.getUsedTime()))
                .collect(Collectors.toList());

        ContractDto contractDto = ContractDto.<ContractDto.PlanGetDto>builder()
                .type("plan")
                .list(collect)
                .build();

        return new ResponseEntity<Object>(contractDto, HttpStatus.OK);
    }

    @GetMapping("manage/contracts/membership")
    public ResponseEntity<Object> showMemberships(@CookieValue(name = "company", required = false) String belongCompany){
        if (belongCompany == null) throw new CustomException(ErrorCode.INVALID_TOKEN);

        List<Membership> memberships = membershipService.findMembership(belongCompany);
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
                .map(m -> new ContractDto.MembershipGetDto(m.getKickboardBrand().getBrandName(), m.getKickboardBrand().getDistricts(), m.getKickboardBrand().isInsurance(), m.getKickboardBrand().isHelmet(), m.getUsedTime()))
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
    public ResponseEntity<Object> savePlan(@RequestBody ContractDto<ContractDto.PlanPostDto> planDto,
                                           @CookieValue(name = "company", required = false) String belongCompany){
        if (belongCompany == null) throw new CustomException(ErrorCode.INVALID_TOKEN);
        planService.savePlan(belongCompany, planDto);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @PostMapping("manage/contracts/membership")
    public ResponseEntity<Object> saveMembership(@RequestBody ContractDto contractMembership,
                                                 @CookieValue(name = "company", required = false) String belongCompany){
        if (belongCompany == null) throw new CustomException(ErrorCode.INVALID_TOKEN);
        membershipService.saveMembership(belongCompany, contractMembership);
        return new ResponseEntity<Object>(HttpStatus.OK);

    }


    @PutMapping("/manage/contracts/membership")
    public ResponseEntity<Object> updateMembership(@RequestBody ContractDto contractMembership,
                                                   @CookieValue(name = "company", required = false) String belongCompany){
        if (belongCompany == null) throw new CustomException(ErrorCode.INVALID_TOKEN);
        membershipService.updateMembership(belongCompany, contractMembership);
        return new ResponseEntity<Object>(HttpStatus.OK);

    }

    @PutMapping("/manage/contracts/plan")
    public ResponseEntity<Object> updatePlan(@RequestBody ContractDto<ContractDto.PlanPostDto> planDto,
                                                   @CookieValue(name = "company", required = false) String belongCompany){

        planService.updatePlan(belongCompany, planDto);
        return new ResponseEntity<Object>(HttpStatus.OK);

    }

    @DeleteMapping("/manage/contracts/membership")
    public ResponseEntity<Object> deleteMembership(@CookieValue(name = "company", required = false) String belongCompany){

        membershipService.delete(belongCompany);

        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/manage/contracts/plan")
    public ResponseEntity<Object> deletePlan(@RequestBody ListDto listDto,
                                                 @CookieValue(name = "company", required = false) String belongCompany){

        planService.delete(belongCompany, listDto.getList());

        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

}
