package com.bizkicks.backend.api;

import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.service.MemberService;
import com.bizkicks.backend.dto.ConsumptionDto;
import com.bizkicks.backend.dto.UsageDto;
import com.bizkicks.backend.entity.Consumption;
import com.bizkicks.backend.entity.Coordinate;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.filter.DateFilter;
import com.bizkicks.backend.filter.PagingFilter;
import com.bizkicks.backend.service.ConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DashboardApi {

    @Autowired private MemberService memberService;
    @Autowired private ConsumptionService consumptionService;

    @GetMapping("/dashboard/usage")
    public ResponseEntity<Object> showUsage(@RequestParam(value="from", required = false)
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                  @RequestParam(value="to", required = false, defaultValue = "#{T(java.time.LocalDate).now()}")
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                  @RequestParam(value="page", required = false, defaultValue = "1") Integer page,
                                                  @RequestParam(value = "unit", required = false, defaultValue = "10") Integer unit){

        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        if(startDate == null){
            startDate = LocalDate.of(endDate.getYear(), endDate.getMonth(), 1);
        }

        DateFilter dateFilter = DateFilter.builder().startDate(startDate.atTime(00, 00, 00)).endDate(endDate.atTime(23, 23, 59)).build();
        PagingFilter pagingFilter = PagingFilter.builder().unit(unit).page(page).build();

        List<Member> members = memberService.findAllMemberByCompany(customerCompany);

        List<UsageDto.Detail> history = new ArrayList<UsageDto.Detail>();
        List<UsageDto.UseDetail> memberDetail = new ArrayList<UsageDto.UseDetail>();

        for (Member member1 : members) {

            LinkedHashMap<Consumption, List<Coordinate>> mapConsumptionToCoordinate = consumptionService.findConsumptionWithCoordinate(member1, dateFilter, pagingFilter);

            for(HashMap.Entry<Consumption, List<Coordinate>> entry : mapConsumptionToCoordinate.entrySet()){
                List<Coordinate> coordinates = entry.getValue();
                List<ConsumptionDto.Location> locations = new ArrayList<ConsumptionDto.Location>();
                if(coordinates != null) {
                    for(Coordinate coordinate : coordinates) {
                        locations.add(ConsumptionDto.Location.builder()
                                .latitude(coordinate.getLatitude())
                                .longitude(coordinate.getLongitude())
                                .build()
                        );
                    }
                }

                Consumption consumption = entry.getKey();
                UsageDto.UseDetail useDetail = UsageDto.UseDetail.builder()
                        .brand(consumption.getKickboardBrand().getBrandName())
                        .depart_time(consumption.getDepartTime())
                        .arrive_time(consumption.getArriveTime())
                        .cycle(consumption.getCycle())
                        .location_list(locations)
                        .build();



                memberDetail.add(useDetail);
            }

            UsageDto.Detail detail = UsageDto.Detail.builder()
                    .memberId(member.getId())
                    .memberDetail(memberDetail)
                    .build();

            history.add(detail);
        }

        UsageDto usageDto = UsageDto.builder().page(page).unit(unit).history(history).build();

        return new ResponseEntity<Object>(usageDto, HttpStatus.OK);

    }
}

    


