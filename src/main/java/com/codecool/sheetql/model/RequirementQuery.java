package com.codecool.sheetql.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequirementQuery {

    private List<String> selectFromCondition;
    private List<String> whereCondition;

    private static final int TABLE_NAME = 1;
    private static final int FIELDS = 0;


    public List<String> getSelectFromCondition() {
        return selectFromCondition;
    }

    public List<String> getWhereCondition() {
        return whereCondition;
    }

    public String getTableName() {
        return selectFromCondition.get(TABLE_NAME);
    }

    public void setSelectFromCondition(List<String> selectFromCondition) {
        this.selectFromCondition = selectFromCondition;
    }

    public void setWhereCondition(List<String> whereCondition) {
        this.whereCondition = whereCondition;
    }

    public List<String> getQueryFieldsList() {
        List<String> queryFieldsList = Arrays.stream(selectFromCondition.get(FIELDS).split(","))
                .map(item-> item.trim()).collect(Collectors.toList());
        return queryFieldsList;
    }

    public List<String> getConditionList() {
        return whereCondition;
    }

    public String toString() {
        return "SELECT condition listis: " + selectFromCondition + " :: WHERE condition list: " + whereCondition;
    }
}
