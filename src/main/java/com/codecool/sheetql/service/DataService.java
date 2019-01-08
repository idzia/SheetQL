package com.codecool.sheetql.service;

import com.codecool.sheetql.dao.DataDao;
import com.codecool.sheetql.model.RequirementQuery;
import com.codecool.sheetql.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataService {

    private DataDao dataDao;
    private Parser parser;

    @Autowired
    public DataService(DataDao dataDao, Parser parser) {
        this.dataDao = dataDao;
        this.parser = parser;
    }

    public List<List<String>> inputQuery(String inputQuery){
        Optional<RequirementQuery> optional = parser.parse(inputQuery);
        System.out.println("Optional is present: " + optional.isPresent());
        List<List<String>> answer = new ArrayList<>();
        answer.add(new ArrayList<>());
        if (optional.isPresent()) {
            RequirementQuery requirement = optional.get();

            System.out.println("DataService.java requirementQuery is: " + optional.get().toString());

            answer = dataDao.select(requirement);

        } else throw new IllegalArgumentException("Query not valid");

        return answer;
    }

}
