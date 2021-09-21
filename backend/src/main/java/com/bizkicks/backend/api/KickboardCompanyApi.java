package com.bizkicks.backend.api;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import org.json.JSONObject;
import org.json.JSONArray;


@Controller
public class KickboardCompanyApi {

    @RequestMapping(value = "/manage/measuredrate-price", method=RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<Object> price(){

        JSONObject price = new JSONObject();
        price.put("price", 15000);


        return ResponseEntity.ok(price.toString());
    }

    @RequestMapping(value = "/kickboard/location", method=RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<Object> kickLocation(){

        JSONObject kick1 = new JSONObject();
        JSONObject kick2 = new JSONObject();
        JSONObject kick3 = new JSONObject();
        JSONObject kick4 = new JSONObject();
        JSONObject kick5 = new JSONObject();

        kick1.put("company_name", "씽씽");
        kick1.put("lat", 37.55377475931086);
        kick1.put("lng", 126.96435101606421);
        kick1.put("battery", 90);
        kick1.put("model", "AAAAA");
        kick1.put("past_picture", "http:// ... /kickboard.jpg");

        kick2.put("company_name", "라임");
        kick2.put("lat", 37.54994713018562);
        kick2.put("lng", 126.96518786529317);
        kick2.put("battery", 80);
        kick2.put("model", "BBBBB");
        kick2.put("past_picture", "http:// ... /kickboard.jpg");

        kick3.put("company_name", "킥고잉");
        kick3.put("lat", 37.54887535880441);
        kick3.put("lng", 126.97550900578369);
        kick3.put("battery", 100);
        kick3.put("model", "CCCCC");
        kick3.put("past_picture", "http:// ... /kickboard.jpg");

        kick4.put("company_name", "씽씽");
        kick4.put("lat", 37.554063949968516);
        kick4.put("lng", 126.97791226510789);
        kick4.put("battery", 50);
        kick4.put("model", "DDDDD");
        kick4.put("past_picture", "http:// ... /kickboard.jpg");

        kick5.put("company_name", "라임");
        kick5.put("lat", 37.55843569554293);
        kick5.put("lng", 126.97276242369888);
        kick5.put("battery", 70);
        kick5.put("model", "EEEEE");
        kick5.put("past_picture", "http:// ... /kickboard.jpg");

        JSONArray jal = new JSONArray();
        jal.put(kick1);
        jal.put(kick2);
        jal.put(kick3);
        jal.put(kick4);
        jal.put(kick5);


        JSONObject locations = new JSONObject();
        locations.put("list", jal);
        return ResponseEntity.ok(locations.toString());
    }



}