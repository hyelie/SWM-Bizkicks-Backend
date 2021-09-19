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

@Controller
@NoArgsConstructor
public class AlarmApi {

    @Autowired private AlarmService alarmService;
    @Autowired private MemberService memberService;

    // 사용자 정보를 security로 해결하면 그 정보로 조회할 수 있게 하기.

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
    public ResponseEntity<Object> updateAlarms(@RequestBody ListDto<AlarmDto> alarmsDto, @CookieValue(name = "company", required = false) String belongCompany){
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_NOT_EXIST);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        List<Alarm> alarms = new ArrayList<>();
        for (AlarmDto alarmDto : alarmsDto.getList()){
            alarms.add(alarmDto.toEntity());
        }
        alarmService.updateAlarms(customerCompany, alarms);

        JSONObject returnObject = new JSONObject();
        returnObject.put("msg", "Success");
        return new ResponseEntity<Object>(returnObject.toString(), HttpStatus.CREATED);
    }




}
