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
        System.out.println("entered services");
        Optional<RequirementQuery> optional = parser.parse(inputQuery);
        System.out.println("optional was created: " + optional.isPresent());
        System.out.println(optional.get().getWhereCondition());
        List<List<String>> answer = new ArrayList<>();
        answer.add(new ArrayList<>());
        if (optional.isPresent()) {
            RequirementQuery requirement = optional.get();
            System.out.println("select where con from requirement " + requirement.getSelectFromCondition());
            answer = dataDao.select(requirement);

        } else throw new IllegalArgumentException("Query not valid");

        return answer;
    }

}
