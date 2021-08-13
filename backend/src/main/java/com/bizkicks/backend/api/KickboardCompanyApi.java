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

    @RequestMapping(value = "/kickboard/usages", method=RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<Object> personalUsage(){

        JSONArray locationLists1 = new JSONArray();

        JSONArray locationList1 = new JSONArray();
        locationList1.put("131.0");
        locationList1.put("131.1");
        JSONArray locationList2 = new JSONArray();
        locationList2.put("131.1");
        locationList2.put("131.2");
        JSONArray locationList3 = new JSONArray();
        locationList3.put("131.4");
        locationList3.put("131.7");

        locationLists1.put(locationList1);
        locationLists1.put(locationList2);
        locationLists1.put(locationList3);

        JSONObject history1 = new JSONObject();
        history1.put("brand", "씽씽");
        history1.put("depart_time", "2020-10-10T14:20:15+09:00");
        history1.put("arrive_time", "2020-10-10T14:25:40+09:00");
        history1.put("lists", locationLists1);
        history1.put("interval", 5000);

        JSONArray locationLists2 = new JSONArray();

        JSONArray locationList4 = new JSONArray();
        locationList4.put("131.0");
        locationList4.put("131.1");
        JSONArray locationList5 = new JSONArray();
        locationList5.put("131.1");
        locationList5.put("131.2");
        JSONArray locationList6 = new JSONArray();
        locationList6.put("131.4");
        locationList6.put("131.7");

        locationLists2.put(locationList4);
        locationLists2.put(locationList5);
        locationLists2.put(locationList6);

        JSONObject history2 = new JSONObject();
        history2.put("brand", "씽씽");
        history2.put("depart_time", "2020-10-10T14:20:15+09:00");
        history2.put("arrive_time", "2020-10-10T14:25:40+09:00");
        history2.put("lists", locationLists2);
        history2.put("interval", 5000);

        JSONArray locationLists3 = new JSONArray();

        JSONArray locationList7 = new JSONArray();
        locationList7.put("131.0");
        locationList7.put("131.1");
        JSONArray locationList8 = new JSONArray();
        locationList8.put("131.1");
        locationList8.put("131.2");
        JSONArray locationList9 = new JSONArray();
        locationList9.put("131.4");
        locationList9.put("131.7");

        locationLists3.put(locationList7);
        locationLists3.put(locationList8);
        locationLists3.put(locationList9);

        JSONObject history3 = new JSONObject();
        history3.put("brand", "씽씽");
        history3.put("depart_time", "2020-10-10T14:20:15+09:00");
        history3.put("arrive_time", "2020-10-10T14:25:40+09:00");
        history3.put("lists", locationLists3);
        history3.put("interval", 5000);

        JSONObject personalUsage = new JSONObject();
        JSONArray usages = new JSONArray();
        usages.put(history1);
        usages.put(history2);
        usages.put(history3);
        personalUsage.put("unit", 10);
        personalUsage.put("total_time", 100);
        personalUsage.put("history", usages);



        return ResponseEntity.ok(personalUsage.toString());
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

    @PostMapping("kickboard/usages")
    public ResponseEntity<Object> addUsage(){

        JSONObject a = new JSONObject();
        a.put("msg", "Success");
        return new ResponseEntity<>(a.toString(), HttpStatus.OK);
    }

}