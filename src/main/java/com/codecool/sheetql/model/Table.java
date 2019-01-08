package com.codecool.sheetql.model;

import java.util.List;
import java.util.Map;

public class Table <T>{
    private String tableName;
    private List<List<T>> value ;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<List<T>> getValue() {
        return value;
    }

    public void setValue(List<List<T>> value) {
        this.value = value;
    }

    public Table(String tableName, List<List<T>> value) {
        this.tableName = tableName;
        this.value = value;
    }


}
