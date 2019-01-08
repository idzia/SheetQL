package com.codecool.sheetql;

import com.codecool.sheetql.dao.CsvReader;
import com.codecool.sheetql.dao.Reader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class SheetqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SheetqlApplication.class, args);

    }

}

