package com.bizkicks.backend.api;

import com.bizkicks.backend.dto.AlarmDto;
import com.bizkicks.backend.dto.ListDto;
import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.service.AlarmService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
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

    @GetMapping("/manage/alarms")
    public ResponseEntity<Object> showAlarms(@CookieValue(name = "company") String belongcompany){
        List<AlarmDto> collect = alarmService.findAlarms(belongcompany).stream()
                .map(m -> new AlarmDto(m.getType(), m.getValue()))
                .collect(Collectors.toList());

        if(true)
        throw new CustomException(ErrorCode.ID_DUPLICATED);

        return new ResponseEntity<>(new ListResult(collect), HttpStatus.OK);
    }

    @PostMapping("/manage/alarms")
    public ResponseEntity<Object> updateAlarms(@RequestBody ListDto<AlarmDto> alarmsDto, @CookieValue(name = "company") String belongCompany){
        List<Alarm> alarms = new ArrayList<>();
        for (AlarmDto alarmDto : alarmsDto.getList()){
            alarms.add(alarmDto.toEntity());
        }

        alarmService.updateAlarms(belongCompany, alarms);


        JSONObject returnObject = new JSONObject();
        returnObject.put("msg", "Success");
        return new ResponseEntity<Object>(returnObject.toString(), HttpStatus.CREATED);
    }

    @Data
    @AllArgsConstructor
    class ListResult<T>{
        private T list;
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> test(@CookieValue(name = "company", defaultValue = "비회원") String company){
        JSONObject test1 = new JSONObject();
        test1.put("회사이름", company);
        return ResponseEntity.ok(test1.toString());
    }


//    @GetMapping("/manage/alarms")
//    public ResponseEntity<Object> alarms(){
//
//
//    }


}
