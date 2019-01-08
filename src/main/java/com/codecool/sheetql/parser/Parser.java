package com.codecool.sheetql.parser;

import com.codecool.sheetql.model.RequirementQuery;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class Parser {
    private List<String> parsedQueryList;
    private List<String> validWhereConditionList;
    private RequirementQuery requirementQuery;
    private static final String DELIMETER = "SELECT|FROM|WHERE|select|from|where";
    private static final String OR_AND = "OR|AND|or|and";
    private static final String REGEX = "^SELECT\\s.+\\sFROM\\s.+$";
    private static final String REGEX_WHERE = "^SELECT\\s.+\\sFROM\\s.+\\sWHERE\\s.+$";

    private static final int FIELDS = 0;
    private static final int FILE_NAME = 1;
    private static final int WHERE_CONDITION = 2;




    public Optional<RequirementQuery> parse (String query) {

        List<String> selectFromList = null;
        List<String> whereList= null;

        if (validate(query.trim().toUpperCase())) {
            selectFromList = Arrays.stream(query.split(DELIMETER))
                    .map(item -> item.trim())
                    .skip(1)
                    .collect(Collectors.toList());

            this.requirementQuery = new RequirementQuery();

            this.requirementQuery.setSelectFromCondition(selectFromList);
            if (validateWhere(getWhere())) {
                whereList = getValidWhereCondition();
                this.requirementQuery.setWhereCondition(whereList);
            }
        }
        Optional<RequirementQuery> optional = Optional.ofNullable(requirementQuery);
        return optional;
    }

    private boolean validate(String query) {

        Pattern compileSelectFrom = Pattern.compile(REGEX);
        Pattern compileSelectFromWhere = Pattern.compile(REGEX_WHERE);
        if (compileSelectFrom.matcher(query).matches() || compileSelectFromWhere.matcher(query).matches()){
            return true;
        }
        return false;
    }


    public List<String> getQueryFields() {
        List<String> queryFieldsList = Arrays.stream(requirementQuery.getSelectFromCondition().get(FIELDS).split(","))
                .map(item-> item.trim()).collect(Collectors.toList());
        return queryFieldsList;
    }

//    public String getFileName() {
//        return parsedQueryList.get(FILE_NAME);
//    }

    public String getWhere() {
        if (getParsedQueryList().size()==3){
            return getParsedQueryList().get(WHERE_CONDITION).toUpperCase();
        } else return "";
    }

    private List<String> getParsedQueryList() {
        return this.requirementQuery.getSelectFromCondition();
    }

    public List<String> getValidWhereCondition() {
        String whereCondition = getWhere();
        List<String> conditionList = new ArrayList<>();

        if (whereCondition.contains(" LIKE '")) {

            conditionList = Arrays.stream(whereCondition.split("LIKE"))
                    .map(item -> item.trim())
                    .map(item -> item.toLowerCase())
                    .collect(Collectors.toList());
            conditionList.add("LIKE");

        } else if (whereCondition.contains("<>")) {

            conditionList = Arrays.stream(whereCondition.split("<>"))
                    .map(item -> item.trim())
                    .map(item -> item.toLowerCase())
                    .collect(Collectors.toList());
            conditionList.add("<>");

        } else if (whereCondition.contains(">")) {

            conditionList = Arrays.stream(whereCondition.split(">"))
                    .map(item -> item.trim())
                    .map(item -> item.toLowerCase())
                    .collect(Collectors.toList());
            conditionList.add(">");

        } else if (whereCondition.contains("<")) {

            conditionList = Arrays.stream(whereCondition.split("<"))
                    .map(item -> item.trim())
                    .map(item -> item.toLowerCase())
                    .collect(Collectors.toList());
            conditionList.add("<");

        } else if (whereCondition.contains("=")) {

            conditionList = Arrays.stream(whereCondition.split("="))
                    .map(item -> item.trim())
                    .map(item -> item.toLowerCase())
                    .collect(Collectors.toList());
            conditionList.add("=");
        }

        return conditionList;
    }

    public boolean validateWhere(String whereConditionString) {

        Pattern compileWhereCondition = Pattern.compile(".+=.+|.+<>.+|.+>.+|.+<.+|.+[^<>=]\\sLIKE\\s'.+'");
//".+=.+|.+<>.+|.+>[^<].+|.+[^>]<.+|.+[^<>=]\\sLIKE\\s'.+'");
        if (compileWhereCondition.matcher(whereConditionString).matches() || whereConditionString.equals("")) {
            return true;
        }
        return false;
    }


}
