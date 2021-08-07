package com.bizkicks.backend.api;

import com.bizkicks.backend.dto.AlarmDto;
import com.bizkicks.backend.entity.Alarm;
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

import java.util.Map;
import java.util.List;
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

        return new ResponseEntity<>(new ListResult(collect), HttpStatus.OK);
    }

    @PostMapping("/manage/alarms")
    public ResponseEntity<Object> updateAlarms(@RequestBody Map<String,List<AlarmDto>> alarms, @CookieValue(name = "company") String belongcompany){
        System.out.println("belong : " + belongcompany);
        System.out.println("json : " + alarms);
        System.out.println("list : " + alarms.get("list"));
        List<AlarmDto> alarmList =  alarms.get("list");
        alarmService.updateAlarms(belongcompany, alarmList);
        // service에서 dto 반환.

        JSONObject returnObject = new JSONObject();
        returnObject.put("msg", "Success");
        return new ResponseEntity<>(returnObject.toString(), HttpStatus.CREATED);
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
