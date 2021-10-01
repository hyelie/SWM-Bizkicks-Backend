package com.bizkicks.backend.api;

import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.service.MemberService;
import com.bizkicks.backend.dto.ImageDto;
import com.bizkicks.backend.dto.KickboardDto;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.Kickboard;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.service.KickboardService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequiredArgsConstructor
public class KickboardApi {
    private final KickboardService kickboardService;
    @Autowired private MemberService memberService;

    // cookie 대신 getCurrentMemberInfo 사용해서 수정해야 할 듯.
    @GetMapping("kickboard/location")
    public ResponseEntity<Object> showContracts() {
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        String type = customerCompany.getType();
        
        if (type == null){
            throw new CustomException(ErrorCode.CONTRACT_NOT_EXIST); // 수정 필요  
        }
        else if(type.equals("plan")){
            List<Kickboard> kickboards = kickboardService.findKickboards(customerCompany);
            List<KickboardDto.LocationGetDto> collect = kickboards.stream()
                    .map(m -> new KickboardDto.LocationGetDto(m.getId(), m.getKickboardBrand().getBrandName(), m.getLng(),
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
                    .map(m -> new KickboardDto.LocationGetDto(m.getId(), m.getKickboardBrand().getBrandName(), m.getLng(),
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

    @GetMapping("/kickboard/location/{kickboardId}")
    public ResponseEntity<Object> showLastParkedKickboardImage(@PathVariable(value="kickboardId", required = true) Long kickboardId) throws IOException{
        String encodedString = kickboardService.getKickboardImage(kickboardId);
        ImageDto imageDto = ImageDto.builder().image(encodedString).build();
        HttpStatus httpStatus;
        if(encodedString == null) httpStatus = HttpStatus.NO_CONTENT;
        else httpStatus = HttpStatus.OK;

        return new ResponseEntity<Object>(imageDto, httpStatus);
    }

}
