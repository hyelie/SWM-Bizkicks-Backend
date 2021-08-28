package com.bizkicks.backend.api;

import com.bizkicks.backend.dto.AlarmDto;
import com.bizkicks.backend.dto.ListDto;
import com.bizkicks.backend.entity.Alarm;
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
    
    // company를 받는 게 아니라, userid를 받으면 해당 user가 속해있는 company에 있는 alarm을 주게 바꿀 것임.
    // .

    @GetMapping("/manage/alarms")
    public ResponseEntity<Object> showAlarms(@CookieValue(name = "company", required = false) String belongCompany){
        if(belongCompany == null) throw new CustomException(ErrorCode.INVALID_TOKEN);

        List<AlarmDto> collect = alarmService.findAlarms(belongCompany).stream()
                .map(m -> new AlarmDto(m.getType(), m.getValue()))
                .collect(Collectors.toList());

        return new ResponseEntity<Object>(new ListDto<>(collect), HttpStatus.OK);
    }

    @PostMapping("/manage/alarms")
    public ResponseEntity<Object> updateAlarms(@RequestBody ListDto<AlarmDto> alarmsDto, @CookieValue(name = "company", required = false) String belongCompany){
        if(belongCompany == null) throw new CustomException(ErrorCode.INVALID_TOKEN);

        List<Alarm> alarms = new ArrayList<>();
        for (AlarmDto alarmDto : alarmsDto.getList()){
            alarms.add(alarmDto.toEntity());
        }

        alarmService.updateAlarms(belongCompany, alarms);

        JSONObject returnObject = new JSONObject();
        returnObject.put("msg", "Success");
        return new ResponseEntity<Object>(returnObject.toString(), HttpStatus.CREATED);
    }




}
