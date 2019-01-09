package com.codecool.sheetql.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class RequirementQuery {

    private List<String> selectFromCondition;
    private List<String> whereCondition;

    private static final int TABLE_NAME = 1;
    private static final int FIELDS = 0;


    public RequirementQuery(List<String> selectFromCondition, List<String> whereCondition) {
        this.selectFromCondition = selectFromCondition;
        this.whereCondition = whereCondition;
    }

    public List<String> getSelectFromCondition() {
        return selectFromCondition;
    }

    public List<String> getWhereCondition() {
        return whereCondition;
    }

    public String getTableName() {
        System.out.println("TABLE NAME: " + selectFromCondition);
        return selectFromCondition.get(TABLE_NAME);
    }

    public List<String> getQueryFieldsList() {
        List<String> queryFieldsList = Arrays.stream(selectFromCondition.get(FIELDS).split(","))
                .map(item-> item.trim()).collect(Collectors.toList());
        return queryFieldsList;
    }

    public List<String> getConditionList() {
        return whereCondition;
    }
}
