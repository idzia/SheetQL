package com.codecool.sheetql.dao;

import com.codecool.sheetql.model.RequirementQuery;

import java.util.List;
import java.util.Map;


public interface DataDao{

    Map<String, Integer> getFieldsNameMap(String fileName);

    List<List<String>> select(RequirementQuery requirementQuery);

}
