package org.example.app.tables.interfaces;

import org.example.app.tables.TableColumn;

import java.util.List;

public interface ITable {
    String getTableName();
    List<TableColumn> getColumns();
    List<String> getColumnsNames();
}
