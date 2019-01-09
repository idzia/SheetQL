package com.codecool.sheetql.dao;


import java.util.List;

public interface PersistenceDAO {
    List<List<String>> read(String fileName);
}
