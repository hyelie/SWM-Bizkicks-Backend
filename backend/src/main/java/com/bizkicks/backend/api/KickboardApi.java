package com.bizkicks.backend.api;

import com.bizkicks.backend.dto.KickboardDto;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.Kickboard;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.service.CustomerCompanyService;
import com.bizkicks.backend.service.KickboardService;
import com.bizkicks.backend.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class KickboardApi {

    private final CustomerCompanyService customerCompanyService;
    private final MembershipService membershipService;
    private final KickboardService kickboardService;

    // cookie 대신 getCurrentMemberInfo 사용해서 수정해야 할 듯.
    @GetMapping("kickboard/location")
    public ResponseEntity<Object> showContracts(@CookieValue(name = "company", required = false) String belongCompany) {
        if (belongCompany == null) throw new CustomException(ErrorCode.INVALID_TOKEN);

        CustomerCompany customerCompany = customerCompanyService.findByCustomerCompanyName(belongCompany);
        String type = customerCompany.getType();
        
        if (type == null){
            throw new CustomException(ErrorCode.COMPANY_NOT_EXIST); // 수정 필요  
        }
        else if(type.equals("plan")){
            List<Kickboard> kickboards = kickboardService.findKickboards(customerCompany);
            List<KickboardDto.LocationGetDto> collect = kickboards.stream()
                    .map(m -> new KickboardDto.LocationGetDto(m.getKickboardBrand().getBrandName(), m.getLng(),
                            m.getLat(), m.getBattery(), m.getModel(), m.getPastPicture()))
                    .collect(Collectors.toList());

            KickboardDto kickboardDto = KickboardDto.<KickboardDto.LocationGetDto>builder()
                    .list(collect)
                    .build();

            return new ResponseEntity<Object>(kickboardDto, HttpStatus.OK);

        }
        else if(type.equals("membership")){
            List<Kickboard> kickboards = kickboardService.findAllKickboards();
            List<KickboardDto.LocationGetDto> collect = kickboards.stream()
                    .map(m -> new KickboardDto.LocationGetDto(m.getKickboardBrand().getBrandName(), m.getLng(),
                            m.getLat(), m.getBattery(), m.getModel(), m.getPastPicture()))
                    .collect(Collectors.toList());

            KickboardDto kickboardDto = KickboardDto.<KickboardDto.LocationGetDto>builder()
                    .list(collect)
                    .build();

            return new ResponseEntity<Object>(kickboardDto, HttpStatus.OK);
        }
        // 여기도 중복되는 코드 있는데 굳이 넣을 필요 없을 듯. 
        
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
