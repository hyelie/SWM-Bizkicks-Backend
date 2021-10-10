package com.bizkicks.backend.api;

import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.service.MemberService;
import com.bizkicks.backend.dto.ImageDto;
import com.bizkicks.backend.dto.KickboardDto;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.Kickboard;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.entity.Plan;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.service.KickboardService;
import com.bizkicks.backend.service.MembershipService;
import com.bizkicks.backend.service.PlanService;
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
    @Autowired private MembershipService membershipService;
    @Autowired private PlanService planService;

    @GetMapping("kickboard/location")
    public ResponseEntity<Object> showContracts() {
        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        String contractType = customerCompany.getType();

        if (contractType == null){
            throw new CustomException(ErrorCode.CONTRACT_NOT_EXIST);
        }
        else if(contractType.equals("plan")){
            List<Kickboard> kickboards = kickboardService.findKickboards(customerCompany);
            List<KickboardDto.LocationGetDto> collect = kickboards.stream()
                    .map(m -> new KickboardDto.LocationGetDto(m.getId(), m.getKickboardBrand().getBrandName(), m.getLat(),
                            m.getLng(), m.getBattery(), m.getModel(), m.getPastPicture()))
                    .collect(Collectors.toList());

            KickboardDto kickboardDto = KickboardDto.<KickboardDto.LocationGetDto>builder()
                    .list(collect)
                    .build();

            return new ResponseEntity<Object>(kickboardDto, HttpStatus.OK);

        }
        else if(contractType.equals("membership")){
            List<Kickboard> kickboards = kickboardService.findAllKickboards();
            List<KickboardDto.LocationGetDto> collect = kickboards.stream()
                    .map(m -> new KickboardDto.LocationGetDto(m.getId(), m.getKickboardBrand().getBrandName(), m.getLat(),
                            m.getLng(), m.getBattery(), m.getModel(), m.getPastPicture()))
                    .collect(Collectors.toList());

            KickboardDto kickboardDto = KickboardDto.<KickboardDto.LocationGetDto>builder()
                    .list(collect)
                    .build();

            return new ResponseEntity<Object>(kickboardDto, HttpStatus.OK);
        }
        
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @GetMapping("/kickboard/location/{kickboardId}")
    public ResponseEntity<Object> showLastParkedKickboardImage(@PathVariable(value="kickboardId", required = true) Long kickboardId) throws IOException{

        Member member = memberService.getCurrentMemberInfo();
        if(member == null) throw new CustomException(ErrorCode.MEMBER_STATUS_LOGOUT);
        CustomerCompany customerCompany = member.getCustomerCompany();
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        String contractType = customerCompany.getType();
        Boolean checkAvailable = Boolean.TRUE;
        if(contractType.equals("plan")){

            Plan plan = planService.findPlanByBrandAndCompany(customerCompany, kickboardId);
            Integer usedTime = plan.getUsedTime();
            Integer totalTime = plan.getTotalTime();

            if (usedTime > totalTime){
                checkAvailable = Boolean.FALSE;
            }
        }

        String encodedString = kickboardService.getKickboardImage(kickboardId);
        ImageDto imageDto = ImageDto.builder()
                .checkAvailable(checkAvailable)
                .image(encodedString).build();

        HttpStatus httpStatus;
        if(encodedString == null) httpStatus = HttpStatus.NO_CONTENT;
        else httpStatus = HttpStatus.OK;

        return new ResponseEntity<Object>(imageDto, httpStatus);
    }

}
