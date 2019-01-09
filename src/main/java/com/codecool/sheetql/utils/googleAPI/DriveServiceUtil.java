package com.codecool.sheetql.utils.googleAPI;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class DriveServiceUtil {
    private static final String APPLICATION_NAME = "SheetQL";

    public static Drive getDriveService(Credential credential) {
//        Credential credential = GoogleAuthorizeUtil.authorize();
        try {
            return new Drive.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(), credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFilesNames(Credential credential) throws IOException {

        Drive driveService = getDriveService(credential);
        FileList result = driveService.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }
        return "hop hop";


//        List<String> names = new ArrayList<>();
//        Spreadsheet spreadsheet = sheetsService.spreadsheets().get(spreadsheetID).execute();
//        for(Sheet sheet : spreadsheet.getSheets()) {
//            names.add(sheet.getProperties().getTitle());
//
//        }
//        return names;
    }
}
