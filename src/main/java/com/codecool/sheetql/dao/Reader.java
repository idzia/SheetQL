package com.codecool.sheetql.dao;

import java.util.List;

public interface Reader {
    public List<List<String>> read(String fileName);
}
