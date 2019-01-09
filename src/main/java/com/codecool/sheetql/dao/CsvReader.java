package com.codecool.sheetql.dao;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class CsvReader implements Reader {
    private static final String PREFIX= ""; //"./main/resources/static/csv/";
    private static final String SUFFIX = ".csv";
    private static final String DELIMETER= ",";

    public List<List<String>> read(String fileName) {

        List<List<String>> csvStringList = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(PREFIX+fileName+SUFFIX))) {

            csvStringList = stream
                    .map(line -> Arrays.asList(line.split(DELIMETER)))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<String>> table = new ArrayList<>();
        List<String> row = Arrays.asList(new String[] {"id", "title", "author"});
        List<String> row1 = Arrays.asList(new String[] {"1", "title1", "author1"});
        table.add(row);
        table.add(row1);

        //return csvStringList;
        return table;
    }
}
