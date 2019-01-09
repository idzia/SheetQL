package com.codecool.sheetql.dao;

import com.codecool.sheetql.utils.googleAPI.SheetsServiceUtil;
import com.google.api.services.sheets.v4.Sheets;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
public class GoogleSheetsReader implements Reader {

    private static final String DEFAULT_SPREADSHEETID = "1OrTcgTSKQnNMNIy6rfOK3VEKc1lgsZaN0pq6ht8yYoA";

    private String spreadsheetid = DEFAULT_SPREADSHEETID;
    private Sheets sheetsService;

    public GoogleSheetsReader() {
        this.sheetsService = SheetsServiceUtil.getSheetsService();
    }

    public GoogleSheetsReader(String spreadSheetID) {
        super();
        this.spreadsheetid = spreadSheetID;
    }

    @Override
    public List<List<String>> read(String name) {
        List<List<String>> result= null;

        try {
             result = SheetsServiceUtil
                    .pullAllSheetValues(sheetsService,spreadsheetid,name)
                    .getValues()
                     .stream()
                     .map(list -> list.stream()
                             .map(item -> (String) item)
                             .collect(Collectors.toList()))
                     .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }
}
