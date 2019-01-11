package com.codecool.sheetql.controller;

import com.codecool.sheetql.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;


    @GetMapping("/index")
    public String string() {
        return "mainPage";
    }


    @PostMapping("/index")
    public String inputQuery(@RequestBody String query, Model model) {
        model.addAttribute("userQuery", query);
        try {
            List<String> result = dataService.unpack(dataService.inputQuery(query));
            model.addAttribute("queryResult", result);

        } catch (IllegalArgumentException e) {
            model.addAttribute("queryResult", Arrays.asList("Query not valid!"));
        }
        return "mainPage";
    }

//    @GetMapping("/index/{select}")
//    public List<List<String>> inputQuery(@PathVariable("select") String query) {
//        return dataService.inputQuery(query);
//    }
}
