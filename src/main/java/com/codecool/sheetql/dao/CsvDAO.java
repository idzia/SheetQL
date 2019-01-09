package com.codecool.sheetql.dao;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class CsvDAO implements PersistenceDAO {
    private static final String PREFIX= ""; //"./main/resources/static/csv/";
    private static final String SUFFIX = ".csv";
    private static final String DELIMETER= ",";

    public List<List<String>> read(String fileName) {

        List<List<String>> csvStringList = new ArrayList<>();

        csvStringList.add(Arrays.asList("id", "title", "author"));
        csvStringList.add(Arrays.asList("1", "title1", "author1"));
        csvStringList.add(Arrays.asList("2", "title2", "author2"));
//        try (Stream<String> stream = Files.lines(Paths.get(PREFIX+fileName+SUFFIX))) {
//
//            csvStringList = stream
//                    .map(line -> Arrays.asList(line.split(DELIMETER)))
//                    .collect(Collectors.toList());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return csvStringList;

    }
}
