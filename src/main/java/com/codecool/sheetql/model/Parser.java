package com.codecool.sheetql.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class Parser {

    private RequirementQuery requirementQuery;
    private static final String DELIMETER = "SELECT|FROM|WHERE|select|from|where";
    private static final String OR_AND = "OR|AND|or|and";
    private static final String REGEX = "^SELECT\\s.+\\sFROM\\s.+$";
    private static final String REGEX_WHERE = "^SELECT\\s.+\\sFROM\\s.+\\sWHERE\\s.+$";
    private static final String REGEX_CONDITION = ".+=.+|.+<>.+|.+>.+|.+<.+|.+[^<>=]\\sLIKE\\s'.+'";
    private static final String REGEX_CONDITION_AND = REGEX_CONDITION + " AND | and " + REGEX_CONDITION;
    private static final String REGEX_CONDITION_OR = REGEX_CONDITION + " OR | or " + REGEX_CONDITION;

//    private static final int FIELDS = 0;
//    private static final int FILE_NAME = 1;
    private static final int WHERE_CONDITION = 2;


    public Optional<RequirementQuery> parse (String query) {

        List<String> selectFromList = null;
        List<List<String>> whereList= null;

        if (validate(query.trim().toUpperCase())) {
            selectFromList = Arrays.stream(query.split(DELIMETER))
                    .map(item -> item.trim())
                    .skip(1)
                    .collect(Collectors.toList());

            this.requirementQuery = new RequirementQuery();
            this.requirementQuery.setSelectFromCondition(selectFromList);

            if (validateWhere(getWhere())) {
                whereList = getValidWhereCondition(parseWhereByAnd());
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

    public boolean validateWhere(String whereConditionString) {
        Pattern compileWhereCondition = Pattern.compile(REGEX_CONDITION);

        if (compileWhereCondition.matcher(whereConditionString).matches() || whereConditionString.equals("") ) {
            return true;
        }
        return false;
    }

//    public List<String> spliterAndOr(String whereCondition) {
//        Pattern compileWhereAndCondition = Pattern.compile(REGEX_CONDITION_AND);
//        Pattern compileWhereOrCondition = Pattern.compile(REGEX_CONDITION_OR);
//        List<String> wheresList = null;
//
//        if (compileWhereAndCondition.matcher(whereCondition).matches()) {
//            wheresList = Arrays.asList(whereCondition.split(" AND | and "));
//        } else if (compileWhereOrCondition.matcher(whereCondition).matches()) {
//            wheresList = Arrays.asList(whereCondition.split(" OR | or "));
//        }
//
//        return wheresList;
//    }

//    public boolean validateWhereOrAnd(String whereConditionString) {
//        Pattern compileWhereAndCondition = Pattern.compile(REGEX_CONDITION_AND);
//        Pattern compileWhereOrCondition = Pattern.compile(REGEX_CONDITION_OR);
//        if (compileWhereAndCondition.matcher(whereConditionString).matches() ||
//                compileWhereOrCondition.matcher(whereConditionString).matches()) {
//            return true;
//        }
//        return false;
//    }



    public String getWhere() {
        List<String> parsedQueryList = this.requirementQuery.getSelectFromCondition();
        if (parsedQueryList.size()==3){
            return parsedQueryList.get(WHERE_CONDITION).toUpperCase();
        } else return "";
    }

    public List<String> parseWhereByAnd() {
        List<String> listOfWhereCondition = Arrays.asList(getWhere().split(" AND | and "));
        return listOfWhereCondition;

    }

    public List<List<String>> getValidWhereCondition(List<String> parseWhereByAnd) {

        List<List<String>> conditionsList = new ArrayList<>();

        for (String whereCondition : parseWhereByAnd) {

            List<String> helpConditionList = new ArrayList<>();

            if (whereCondition.contains(" LIKE '")) {

                helpConditionList = Arrays.stream(whereCondition.split("LIKE"))
                        .map(item -> item.trim())
                        .map(item -> item.toLowerCase())
                        .collect(Collectors.toList());
                helpConditionList.add("LIKE");

            } else if (whereCondition.contains("<>")) {

                helpConditionList = Arrays.stream(whereCondition.split("<>"))
                        .map(item -> item.trim())
                        .map(item -> item.toLowerCase())
                        .collect(Collectors.toList());
                helpConditionList.add("<>");

            } else if (whereCondition.contains(">")) {

                helpConditionList = Arrays.stream(whereCondition.split(">"))
                        .map(item -> item.trim())
                        .map(item -> item.toLowerCase())
                        .collect(Collectors.toList());
                helpConditionList.add(">");

            } else if (whereCondition.contains("<")) {

                helpConditionList = Arrays.stream(whereCondition.split("<"))
                        .map(item -> item.trim())
                        .map(item -> item.toLowerCase())
                        .collect(Collectors.toList());
                helpConditionList.add("<");

            } else if (whereCondition.contains("=")) {

                helpConditionList = Arrays.stream(whereCondition.split("="))
                        .map(item -> item.trim())
                        .map(item -> item.toLowerCase())
                        .collect(Collectors.toList());
                helpConditionList.add("=");
            }
            conditionsList.add(helpConditionList);
        }

        return conditionsList;
    }




//    public List<String> getValidWhereCondition() {
//        String whereCondition = getWhere();
//        List<String> conditionList = new ArrayList<>();
//
//        if (whereCondition.contains(" LIKE '")) {
//
//            conditionList = Arrays.stream(whereCondition.split("LIKE"))
//                    .map(item -> item.trim())
//                    .map(item -> item.toLowerCase())
//                    .collect(Collectors.toList());
//            conditionList.add("LIKE");
//
//        } else if (whereCondition.contains("<>")) {
//
//            conditionList = Arrays.stream(whereCondition.split("<>"))
//                    .map(item -> item.trim())
//                    .map(item -> item.toLowerCase())
//                    .collect(Collectors.toList());
//            conditionList.add("<>");
//
//        } else if (whereCondition.contains(">")) {
//
//            conditionList = Arrays.stream(whereCondition.split(">"))
//                    .map(item -> item.trim())
//                    .map(item -> item.toLowerCase())
//                    .collect(Collectors.toList());
//            conditionList.add(">");
//
//        } else if (whereCondition.contains("<")) {
//
//            conditionList = Arrays.stream(whereCondition.split("<"))
//                    .map(item -> item.trim())
//                    .map(item -> item.toLowerCase())
//                    .collect(Collectors.toList());
//            conditionList.add("<");
//
//        } else if (whereCondition.contains("=")) {
//
//            conditionList = Arrays.stream(whereCondition.split("="))
//                    .map(item -> item.trim())
//                    .map(item -> item.toLowerCase())
//                    .collect(Collectors.toList());
//            conditionList.add("=");
//        }
//
//        return conditionList;
//    }

//    public boolean validateWhere(String whereConditionString) {
//
//        Pattern compileWhereCondition = Pattern.compile(REGEX_CONDITION);
//
//        if (compileWhereCondition.matcher(whereConditionString).matches() || whereConditionString.equals("")) {
//            return true;
//        }
//        return false;
//    }


}
