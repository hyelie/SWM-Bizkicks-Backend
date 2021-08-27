package com.bizkicks.backend.api;

import java.util.List;

import com.bizkicks.backend.dto.ConsumptionDto;
import com.bizkicks.backend.entity.Consumption;
import com.bizkicks.backend.entity.Coordinate;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.service.ConsumptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.NoArgsConstructor;

@Controller
@NoArgsConstructor
public class ConsumptionApi {
    @Autowired private ConsumptionService consumptionService;

    @PostMapping("/kickboard/consumption")
    public ResponseEntity<Object> saveConsumption(@RequestBody ConsumptionDto.Detail detail, @CookieValue(name = "userid", required = false) Long userId){
        if(userId == null) throw new CustomException(ErrorCode.USER_NOT_EXIST);

        Consumption consumption = detail.toConsumptionEntity();
        List<Coordinate> coordinates = detail.toCoordinateEntity();

        consumptionService.saveConsumptionWithCoordinates(userId, detail.getBrand(), consumption, coordinates);

        return new ResponseEntity<Object>(null, HttpStatus.CREATED);
    }
}
