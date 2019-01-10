package com.codecool.sheetql.service;

import com.codecool.sheetql.dao.PersistenceDAO;
import com.codecool.sheetql.model.RequirementQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class StreamService {

    private Map<String, Integer> fieldsNameMap;
    private PersistenceDAO persistenceDAO;
    private RequirementQuery requirementQuery;

    public final Integer HEADERS_INDEX  = 0;
    public final Integer SIGN_INDEX = 2;

    @Autowired
    public StreamService(PersistenceDAO persistenceDAO, RequirementQuery requirementQuery) {
        this.persistenceDAO = persistenceDAO;
        this.requirementQuery = requirementQuery;
    }

    public void execute(){
        String fileName = requirementQuery.getTableName();
        this.fieldsNameMap = getFieldsNameMap(fileName);
    }

    public Map<String, Integer> getFieldsNameMap(String fileName) {
        List<String> fieldsNameList = getFieldsNameList(fileName);
        Map<String, Integer> fieldsNameMap = new HashMap<>();

        for(String item : fieldsNameList) {
            fieldsNameMap.put(item.toLowerCase(), fieldsNameList.indexOf(item));
        }

        return fieldsNameMap;
    }

    private List<String> getFieldsNameList(String fileName) {
        List<String> fieldsNameList = persistenceDAO.read(fileName).get(HEADERS_INDEX).stream()
                .map(item -> item.toLowerCase())
                .collect(Collectors.toList());

        return fieldsNameList;
    }

    public List<List<String>> select(RequirementQuery requirementQuery) {
        this.requirementQuery = requirementQuery;
        execute();
        String fileName = requirementQuery.getTableName();

        List<String> fieldsNameList = getFieldsNameList(fileName);
        List<String> fieldsToSelectList = new ArrayList<>();

        for (String field : requirementQuery.getQueryFieldsList()) {
            if (field.equals("*")) {
                fieldsToSelectList.addAll(fieldsNameList);
            } else fieldsToSelectList.add(field);
        }

        List<Predicate<List<String>>> filterAnd = prepareFilterAndMethod(requirementQuery.getConditionList());
        List<List<String>> selectedRowContent = getSelectedRow(fileName, filterAnd);
        List<List<String>> selectedContent = getSelectedColumn(selectedRowContent, fieldsToSelectList);

        return selectedContent;
    }

    private List<List<String>> getSelectedColumn(List<List<String>> selectedRowContent, List<String> fieldsToSelect) {
        List<List<String>> selectedColumnContent = selectedRowContent.stream()
                .map(item -> {
                    return fieldsToSelect.stream()
                            .map(field -> item.get(fieldsNameMap.get(field))).collect(Collectors.toList());
                }).collect(Collectors.toList());

        return selectedColumnContent;
    }

    private List<List<String>> getSelectedRow(String fileName, List<Predicate<List<String>>> filterAnd) {
        List<List<String>> selectedRowContent = persistenceDAO.read(fileName).stream()
                .skip(1)
                .filter(filterAnd.stream().reduce(Predicate::and).orElse(p->false)).collect(Collectors.toList());
        return selectedRowContent;
    }

    private List<Predicate<List<String>>> prepareFilterAndMethod (List<List<String>> whereConditionList) {

        return whereConditionList.stream()
                .map(list -> predicate(list))
                .collect(Collectors.toList());
    }

    public Predicate<List<String>> predicate (List<String> validConditionList) {

        if (validConditionList.size()==0) {
            return item -> true;
        } else {
            String conditionSign = validConditionList.get(SIGN_INDEX);
            String conditionValue = validConditionList.get(1);
            Integer conditionIndexInList = fieldsNameMap.get(validConditionList.get(0));

            switch (conditionSign) {
                case "=":
                    return item ->
                            (item.get(conditionIndexInList)).toLowerCase().equals(conditionValue);
                case ">":
                    return item ->
                            Integer.valueOf(item.get(conditionIndexInList)) > Integer.valueOf(conditionValue);
                case "<":
                    return item ->
                            Integer.valueOf(item.get(conditionIndexInList)) < Integer.valueOf(conditionValue);
                case "<>":
                    return item ->
                            Integer.valueOf(item.get(conditionIndexInList)) != Integer.valueOf(conditionValue);
                case "LIKE":
                    return item -> {
                        Pattern patern = Pattern.compile(conditionValue.substring(1, (conditionValue.length()) - 1));
                        return patern.matcher((item.get(conditionIndexInList)).toLowerCase()).find(); };
            }
        }
        return item -> false;
    }




//end
}
