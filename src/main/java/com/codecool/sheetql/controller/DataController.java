package com.codecool.sheetql.controller;

import com.codecool.sheetql.service.DataService;
import com.codecool.sheetql.utils.googleAPI.DriveServiceUtil;
import com.codecool.sheetql.utils.googleAPI.GoogleAuthorizeUtil;
import com.google.api.client.auth.oauth2.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class DataController {

    @Autowired
    private DataService dataService;
    private Credential credential;


    @GetMapping("/index")
    public String string() {
        return "hello, please login to continue <a href=\"/login\">";
    }

    @GetMapping("/login")
    public String login() {
        credential = GoogleAuthorizeUtil.authorize();
        return "login please";
    }


//    @PostMapping("/index")
//    public List<List<String>> inputQuery(@RequestBody String query) {
//        return dataService.inputQuery(query);
//    }



    @GetMapping("/index/{select}")
    public List<List<String>> inputQuery(@PathVariable("select") String query) {
        return dataService.inputQuery(query);
    }

    @GetMapping("/index/{spreadsheetid}/{select}")
    public List<List<String>> inputQueryBySpreadsheetId(@PathVariable("select") String query) {
        return dataService.inputQuery(query);
    }

    @GetMapping("/spreadsheets")
    public String getFiles() {
        try {
            DriveServiceUtil.getFilesNames(credential);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "hop hop";
    }
}
