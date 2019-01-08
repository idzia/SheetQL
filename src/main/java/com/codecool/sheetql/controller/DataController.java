package com.codecool.sheetql.controller;

import com.codecool.sheetql.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DataController {

    @Autowired
    private DataService dataService;


    @GetMapping("/index")
    public String string() {
        return "HELLO";
    }


//    @PostMapping("/index")
//    public List<List<String>> inputQuery(@RequestBody String query) {
//        return dataService.inputQuery(query);
//    }

    @GetMapping("/index/{select}")
    public List<List<String>> inputQuery(@PathVariable("select") String query) {
        return dataService.inputQuery(query);
    }
}
