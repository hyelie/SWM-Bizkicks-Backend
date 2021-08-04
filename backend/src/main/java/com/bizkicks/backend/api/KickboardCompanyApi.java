package com.bizkicks.backend.api;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;


@Controller
public class KickboardCompanyApi {

    @RequestMapping(value = "/manage/alarms", method=RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<Object> alarms(){

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

    @RequestMapping(value = "/manage/measuredrate-price", method=RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<Object> price(){

        JSONObject price = new JSONObject();
        price.put("price", 15000);


        return ResponseEntity.ok(price.toString());
    }

    @RequestMapping(value = "/manage/products", method=RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<Object> products(){

        JSONObject product1 = new JSONObject();
        JSONObject product2 = new JSONObject();
        JSONObject product3 = new JSONObject();

        product1.put("company_name", "씽씽");
        product1.put("price_per_hour", 10000);

        JSONArray jal1 = new JSONArray();
        jal1.put("금천구");
        jal1.put("강북구");
        jal1.put("강동구");
        product1.put("service_location", jal1);
        product1.put("insurance", true);
        product1.put("helmet", true);

        product2.put("company_name", "킥고잉");
        product2.put("price_per_hour", 9000);

        JSONArray jal2 = new JSONArray();
        jal2.put("강남구");
        jal2.put("마포구");
        jal2.put("서초구");
        product2.put("service_location", jal2);

        product2.put("insurance", false);
        product2.put("helmet", true);

        product3.put("company_name", "스윙");
        product3.put("price_per_hour", 11000);

        JSONArray jal3 = new JSONArray();
        jal3.put("관악구");
        jal3.put("서초구");
        jal3.put("강동구");
        product3.put("service_location", jal3);

        product3.put("insurance", false);
        product3.put("helmet", false);

        JSONArray jal = new JSONArray();
        jal.put(product1);
        jal.put(product2);
        jal.put(product3);

        JSONObject products = new JSONObject();
        products.put("list", jal);

        return ResponseEntity.ok(products.toString());
    }

    @RequestMapping(value = "/manage/products/씽씽", method=RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<Object> kickdetail(){

        JSONObject kickdetail = new JSONObject();
        kickdetail.put("html", "/src/씽씽.html");

        return ResponseEntity.ok(kickdetail.toString());
    }

    @RequestMapping(value = "/manage/contracts", method=RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<Object> contracts(){

        JSONObject product1 = new JSONObject();
        JSONObject product2 = new JSONObject();
        JSONObject product3 = new JSONObject();

        product1.put("company_name", "씽씽");
        product1.put("price_per_hour", 10000);

        JSONArray jal1 = new JSONArray();
        jal1.put("금천구");
        jal1.put("강북구");
        jal1.put("강동구");
        product1.put("service_location", jal1);
        product1.put("insurance", true);
        product1.put("helmet", true);

        product2.put("company_name", "킥고잉");
        product2.put("price_per_hour", 9000);

        JSONArray jal2 = new JSONArray();
        jal2.put("강남구");
        jal2.put("마포구");
        jal2.put("서초구");
        product2.put("service_location", jal2);

        product2.put("insurance", false);
        product2.put("helmet", true);

        product3.put("company_name", "스윙");
        product3.put("price_per_hour", 11000);

        JSONArray jal3 = new JSONArray();
        jal3.put("관악구");
        jal3.put("서초구");
        jal3.put("강동구");
        product3.put("service_location", jal3);

        product3.put("insurance", false);
        product3.put("helmet", false);

        JSONArray jal = new JSONArray();
        jal.put(product1);
        jal.put(product2);
        jal.put(product3);

        JSONObject products = new JSONObject();
        products.put("type", "measureRate");
        products.put("duedate", "2021-12-31");
        products.put("list", jal);

        return ResponseEntity.ok(products.toString());
    }

    @PostMapping("/manage/contracts")
    public ResponseEntity<Object> addContract(){

        JSONObject a = new JSONObject();
        a.put("msg", "Success");
        return new ResponseEntity<>(a.toString(), HttpStatus.CREATED);
    }

    @PutMapping("/manage/contracts")
    public ResponseEntity<Object> updateContract(){

        JSONObject a = new JSONObject();
        a.put("msg", "Success");
        return new ResponseEntity<>(a.toString(), HttpStatus.OK);
    }

    @DeleteMapping("/manage/contracts")
    public ResponseEntity<Object> deleteContract(){

        JSONObject a = new JSONObject();
        a.put("msg", "Success");
        return new ResponseEntity<>(a.toString(), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/manage/alarms")
    public ResponseEntity<Object> updateAlarm(){

        JSONObject a = new JSONObject();
        a.put("msg", "Success");
        return new ResponseEntity<>(a.toString(), HttpStatus.OK);
    }

}