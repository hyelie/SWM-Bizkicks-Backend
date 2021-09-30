package com.bizkicks.backend.service;

import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.Kickboard;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.repository.CustomerCompanyRepository;
import com.bizkicks.backend.repository.KickboardRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        if(image.isEmpty()){
            savePath = null;
        }
        else{
            Date currentDate = new Date();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String currentPath = new File("").getAbsolutePath() + "\\" + "/images/kickboard/";
            File checkPathFile = new File(currentPath);
            if(!checkPathFile.exists()){
                checkPathFile.mkdirs();
            }

            File savingImage = new File(currentPath + kickboardId + "_" + transFormat.format(currentDate) + ".jpg");
            image.transferTo(savingImage.toPath());
            savePath = savingImage.toPath().toString();
        }

        kickboardRepository.updatePastPicture(kickboardId, savePath);
    }
}
