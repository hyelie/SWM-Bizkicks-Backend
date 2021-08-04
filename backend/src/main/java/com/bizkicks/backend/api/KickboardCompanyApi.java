package com.bizkicks.backend.api;

import com.bizkicks.backend.entity.Alarms;
import com.bizkicks.backend.entity.KickboardCompany;
import com.bizkicks.backend.entity.KickboardLocation;
import com.bizkicks.backend.entity.Price;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;


@Controller
public class KickboardCompanyApi {

    @RequestMapping(value = "/manage/measuredrate-price", method=RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public Price price(){

        Price price = new Price();
        price.setPrice(15000);

        return price;
    }

    @RequestMapping(value = "/manage/products", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<Object> restTest() {

        List<Object> kickboardcompany = new ArrayList<>();

        KickboardCompany kickboardCompany1 = new KickboardCompany();
        kickboardCompany1.setCompany_name("씽씽");
        kickboardCompany1.setPrice_per_Hour(10000L);
        kickboardCompany1.setHelmet(false);
        kickboardCompany1.setInsurance(true);

        List<String> serviceLocation = new ArrayList<>();
        serviceLocation.add("관악구");
        serviceLocation.add("서초구");
        serviceLocation.add("강남구");
        kickboardCompany1.setService_location(serviceLocation);

        KickboardCompany kickboardCompany2 = new KickboardCompany();
        kickboardCompany2.setCompany_name("킥고잉");
        kickboardCompany2.setPrice_per_Hour(9000L);
        kickboardCompany2.setHelmet(true);
        kickboardCompany2.setInsurance(true);

        List<String> serviceLocation2 = new ArrayList<>();
        serviceLocation2.add("관악구");
        serviceLocation2.add("서초구");
        serviceLocation2.add("강남구");
        kickboardCompany2.setService_location(serviceLocation2);

        kickboardcompany.add(kickboardCompany1);
        kickboardcompany.add(kickboardCompany2);

        JSONObject rj = new JSONObject();
        rj.put("list", kickboardcompany);

        return ResponseEntity.ok(rj.toString());
    }

    @RequestMapping(value = "/kickboard/location", produces = "application/json; charset=utf8")
    @ResponseBody
    public List<Object> kickboardlocation() {

        List<Object> kickboardlocation = new ArrayList<>();

        KickboardLocation kickboardLocation1 = new KickboardLocation();
        kickboardLocation1.setCompany_name("씽씽");
        kickboardLocation1.setLat(37.55377475931086);
        kickboardLocation1.setLng(126.96435101606421);
        kickboardLocation1.setBattery(80L);
        kickboardLocation1.setModel("AAAAA");
        kickboardLocation1.setImg_url("http://****.jpg");

        KickboardLocation kickboardLocation2 = new KickboardLocation();
        kickboardLocation2.setCompany_name("킥고잉");
        kickboardLocation2.setLat(37.54994713018562);
        kickboardLocation2.setLng(126.96518786529317);
        kickboardLocation2.setBattery(90L);
        kickboardLocation2.setModel("CCCCC");
        kickboardLocation2.setImg_url("http://****.jpg");

        KickboardLocation kickboardLocation3 = new KickboardLocation();
        kickboardLocation3.setCompany_name("라임");
        kickboardLocation3.setLat(37.54887535880441);
        kickboardLocation3.setLng(126.97550900578369);
        kickboardLocation3.setBattery(100L);
        kickboardLocation3.setModel("BBBBB");
        kickboardLocation3.setImg_url("http://****.jpg");

        KickboardLocation kickboardLocation4 = new KickboardLocation();
        kickboardLocation4.setCompany_name("씽씽");
        kickboardLocation4.setLat(37.554063949968516);
        kickboardLocation4.setLng(126.97791226510789);
        kickboardLocation4.setBattery(70L);
        kickboardLocation4.setModel("DDDDD");
        kickboardLocation4.setImg_url("http://****.jpg");

        KickboardLocation kickboardLocation5 = new KickboardLocation();
        kickboardLocation5.setCompany_name("킥고잉");
        kickboardLocation5.setLat(37.55843569554293);
        kickboardLocation5.setLng(126.97276242369888);
        kickboardLocation5.setBattery(60L);
        kickboardLocation5.setModel("EEEEE");
        kickboardLocation5.setImg_url("http://****.jpg");

        kickboardlocation.add(kickboardLocation1);
        kickboardlocation.add(kickboardLocation2);
        kickboardlocation.add(kickboardLocation3);
        kickboardlocation.add(kickboardLocation4);
        kickboardlocation.add(kickboardLocation5);

        return kickboardlocation;

    }

    

    @RequestMapping(value = "/manage/alarms", produces = "application/json; charset=utf8")
    @ResponseBody
    public List<Object> alarms(){

        List<Object> alarms = new ArrayList<>();

        Alarms alarm1 = new Alarms();
        alarm1.setType("cost");
        alarm1.setValue(15000);

        Alarms alarm2 = new Alarms();
        alarm2.setType("time");
        alarm2.setValue(15);

        alarms.add(alarm1);
        alarms.add(alarm2);

//        List<AlarmDto> collect = alarms.stream()
//                .map(m -> new AlarmDto()))
        return alarms;
    }

    @RequestMapping(value = "/manage/alarms1", method=RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<Object> alarms1(){

        JSONObject alarm1 = new JSONObject();
        JSONObject alarm2 = new JSONObject();
        JSONObject alarm3 = new JSONObject();

        alarm1.put("cost", "150000");
        alarm2.put("cost", "150000");
        alarm3.put("time", "100");

        JSONArray jal = new JSONArray();
        jal.put(alarm1);
        jal.put(alarm2);
        jal.put(alarm3);

        JSONObject alarm = new JSONObject();
        alarm.put("list", jal);


        return ResponseEntity.ok(alarm.toString());
    }
}