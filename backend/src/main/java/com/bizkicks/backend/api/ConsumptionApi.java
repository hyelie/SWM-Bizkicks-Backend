package com.bizkicks.backend.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.validation.constraints.Null;

import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.service.MemberService;
import com.bizkicks.backend.dto.ConsumptionDto;
import com.bizkicks.backend.dto.ConsumptionDto.Location;
import com.bizkicks.backend.entity.Consumption;
import com.bizkicks.backend.entity.Coordinate;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.filter.DateFilter;
import com.bizkicks.backend.filter.PagingFilter;
import com.bizkicks.backend.service.ConsumptionService;

import com.bizkicks.backend.service.MembershipService;
import com.bizkicks.backend.service.PlanService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.FlashMapManager;

import ch.qos.logback.core.util.Duration;
import lombok.NoArgsConstructor;

@Controller
@NoArgsConstructor
public class ConsumptionApi {
    @Autowired private ConsumptionService consumptionService;
    @Autowired private MemberService memberService;
    @Autowired private PlanService planService;
    @Autowired private MembershipService membershipService;

    // 해당 사용자의 고객 법인의 시간을 이용가능할 때 save할 수 있도록 save에 검사문 넣기

    @PostMapping("/kickboard/consumption")
    public ResponseEntity<Object> saveConsumption(@RequestBody ConsumptionDto.Detail detail){

        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        Long betweenTime = ChronoUnit.MINUTES.between(detail.getDepart_time(), detail.getArrive_time());
        if (customerCompany.getType().equals("plan")) {
            planService.addUsedTime(customerCompany, detail.getBrand(), betweenTime);
        }
        else if (customerCompany.getType().equals("membership")){
            membershipService.addUsedTime(customerCompany, detail.getBrand(), betweenTime);
        }

        Consumption consumption = detail.toConsumptionEntity();
        List<Coordinate> coordinates = detail.toCoordinateEntity();

        consumptionService.saveConsumptionWithCoordinates(member, detail.getBrand(), consumption, coordinates);

        JSONObject returnObject = new JSONObject();
        returnObject.put("msg", "Success");
        return new ResponseEntity<Object>(returnObject.toString(), HttpStatus.CREATED);
    }

    @GetMapping("/kickboard/consumption")
    public ResponseEntity<Object> showConsumption(@RequestParam(value="from", required = false)
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
        LinkedHashMap<Consumption, List<Coordinate>> mapConsumptionToCoordinate = consumptionService.findConsumptionWithCoordinate(member, dateFilter, pagingFilter);

        List<ConsumptionDto.Detail> history = new ArrayList<ConsumptionDto.Detail>();
        for(HashMap.Entry<Consumption, List<Coordinate>> entry : mapConsumptionToCoordinate.entrySet()){
            List<Coordinate> coordinates = entry.getValue();
            List<ConsumptionDto.Location> locations = new ArrayList<ConsumptionDto.Location>();
            if(coordinates != null) {
                for(Coordinate coordinate : coordinates) {
                    locations.add(Location.builder()
                                            .latitude(coordinate.getLatitude())
                                            .longitude(coordinate.getLongitude())
                                            .build()
                    );
                }
            }

            Consumption consumption = entry.getKey();
            ConsumptionDto.Detail detail = ConsumptionDto.Detail.builder()
                                                                .brand(consumption.getKickboardBrand().getBrandName())
                                                                .depart_time(consumption.getDepartTime())
                                                                .arrive_time(consumption.getArriveTime())
                                                                .cycle(consumption.getCycle())
                                                                .location_list(locations)
                                                                .build();

            

            history.add(detail);
        }

        ConsumptionDto consumptionDto = ConsumptionDto.builder().page(page).unit(unit).history(history).build();

        return new ResponseEntity<Object>(consumptionDto, HttpStatus.OK);
    }
}
