package com.bizkicks.backend.service;

import com.bizkicks.backend.dto.ResourceDto;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.Kickboard;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.repository.CustomerCompanyRepository;
import com.bizkicks.backend.repository.KickboardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class KickboardService {

    @Autowired KickboardRepository kickboardRepository;
    @Autowired CustomerCompanyRepository customerCompanyRepository;

    public List<Kickboard> findKickboards(CustomerCompany customerCompany){
        return kickboardRepository.findKickboardByCustomerCompany(customerCompany);
    }

    public List<Kickboard> findAllKickboards() {
        return kickboardRepository.findAllKickboards();
    }

    public void saveKickboardImage(MultipartFile image, Long kickboardId) throws IOException{
        if(!kickboardRepository.existsById(kickboardId))
            throw new CustomException(ErrorCode.KICKBOARD_NOT_EXIST);

        String savePath;
        if(image == null || image.isEmpty()){
            savePath = null;
        }
        else{
            Date currentDate = new Date();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String currentPath = "./images/kickboard/";
            File checkPathFile = new File(currentPath);
            if(!checkPathFile.exists()){
                checkPathFile.mkdirs();
            }

            File savingImage = new File(currentPath + kickboardId + "_" + transFormat.format(currentDate) + ".jpg");
            image.transferTo(savingImage.toPath());
            savePath = savingImage.toPath().toString();
        }

        kickboardRepository.updatePastPicture(kickboardId, savePath);
        log.info("킥보드 사용 사진 저장, 경로 : {}", savePath);
    }

    public ResourceDto getKickboardImage(Long kickboardId) throws IOException{         
        if(!kickboardRepository.existsById(kickboardId)) throw new CustomException(ErrorCode.KICKBOARD_NOT_EXIST);
        Kickboard kickboard = kickboardRepository.findById(kickboardId);

        String savedPath = kickboard.getPastPicture();

        HttpHeaders headers = new HttpHeaders();
        Resource resource;
        HttpStatus httpStatus;
        ResourceDto resourceDto = ResourceDto.builder().build();

        File file = new File(savedPath);
        if(file.exists()){
            Path filePath = Paths.get(savedPath);
            resource = new FileSystemResource(filePath);
            headers.add("Content-Type", Files.probeContentType(filePath));
            httpStatus = HttpStatus.OK;
        }
        else{
            resource = null;
            httpStatus = HttpStatus.NO_CONTENT;
        }

        resourceDto.setResource(resource);
        resourceDto.setHttpHeaders(headers);
        resourceDto.setHttpStatus(httpStatus);
        return resourceDto;
    }
}
