package com.bizkicks.backend.api;


import com.bizkicks.backend.service.CsvService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
public class CsvApi {

    @Autowired
    private CsvService csvService;

    // 오픈 스카우트 과제 요구사항 3)를 참고해서 구현하세요.
    @PostMapping("/admin/upload-csv")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

        InputStream inputStream = file.getInputStream();
        CsvParserSettings setting = new CsvParserSettings();
        setting.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(setting);
        List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
        csvService.save(parseAllRecords);

        JSONObject returnObject = new JSONObject();
        returnObject.put("msg", "Success");

        return new ResponseEntity<Object>(returnObject.toString(), HttpStatus.OK);
    }

}
