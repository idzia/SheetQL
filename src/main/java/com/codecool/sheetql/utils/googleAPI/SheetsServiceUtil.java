package com.codecool.sheetql.utils.googleAPI;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class SheetsServiceUtil {

    private static final String APPLICATION_NAME = "SheetQL";

    public static Sheets getSheetsService() {
        Credential credential = GoogleAuthorizeUtil.authorize();
        try {
            return new Sheets.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(), credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getSheetsNames(Sheets sheetsService, String spreadsheetID) throws IOException {

        List<String> names = new ArrayList<>();
        Spreadsheet spreadsheet = sheetsService.spreadsheets().get(spreadsheetID).execute();
        for(Sheet sheet : spreadsheet.getSheets()) {
            names.add(sheet.getProperties().getTitle());

        }
                return names;
    }

    public static ValueRange pullAllSheetValues(Sheets sheetsService, String spreadsheetID,String SheetName) throws IOException {

        ValueRange result = sheetsService.spreadsheets().values().get(spreadsheetID, SheetName).execute();
        return result;
    }


}
