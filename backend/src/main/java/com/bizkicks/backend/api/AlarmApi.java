package com.bizkicks.backend.api;

import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.service.MemberService;
import com.bizkicks.backend.dto.AlarmDto;
import com.bizkicks.backend.dto.ListDto;
import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.service.AlarmService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@Controller
@NoArgsConstructor
public class AlarmApi {

    @Autowired private AlarmService alarmService;
    @Autowired private MemberService memberService;

    // 알림은 각 계약으로 들어가야 하는 것 아닌감??

    @GetMapping("/manage/alarms")
    public ResponseEntity<Object> showAlarms(){
        CustomerCompany customerCompany = memberService.getCurrentMemberInfo().getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.MEMBER_NOT_EXIST);

        List<AlarmDto> collect = alarmService.findAlarms(customerCompany).stream()
                .map(m -> new AlarmDto(m.getType(), m.getValue()))
                .collect(Collectors.toList());
                

        return new ResponseEntity<Object>(new ListDto<>(collect), HttpStatus.OK);
    }

    @PostMapping("/manage/alarms")
    public ResponseEntity<Object> updateAlarms(@RequestBody ListDto<AlarmDto> alarmsDto){
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        List<Alarm> alarms = new ArrayList<>();
        for (AlarmDto alarmDto : alarmsDto.getList()){
            alarms.add(alarmDto.toEntity());
        }
        alarmService.updateAlarms(customerCompany, alarms);

        JSONObject returnObject = new JSONObject();
        returnObject.put("msg", "Success");
        log.info("{} 회사에서 알림 추가", customerCompany.getCompanyName());
        for(AlarmDto alarmDto : alarmsDto.getList()){
            log.info(" - type : {}, value : {}", alarmDto.getType(), alarmDto.getValue());
        }
        return new ResponseEntity<Object>(returnObject.toString(), HttpStatus.CREATED);
    }




}
