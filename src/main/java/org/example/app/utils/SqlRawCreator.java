package org.example.app.utils;

import org.example.app.tables.interfaces.ITable;
import org.example.app.tables.TableColumn;

import java.util.List;

public class SqlRawCreator<T extends ITable> {
    private T table;
    public SqlRawCreator() {
    }
    public void setTable(T table) {
        this.table = table;
    }
    public String createTableSqlRaw() {
            StringBuilder sqlRawCreate = new StringBuilder();
            String sqlBase = "CREATE TABLE IF NOT EXISTS ";
            String tableName = this.table.getTableName();
            List<TableColumn> columns = this.table.getColumns();
            sqlRawCreate.append(sqlBase).append(tableName).append(" (");
            for (TableColumn column : columns) {
                sqlRawCreate.append(column.getName()).append(" ").append(column.getType());
                if (column.isPrimaryKey()) {
                    sqlRawCreate.append(" PRIMARY KEY");
                }
                if (column.isAutoincrement()) {
                    sqlRawCreate.append(" AUTO_INCREMENT");
                }
                if (!column.isNullable()) {
                    sqlRawCreate.append(" NOT NULL");
                }
                if (column.isUnique()) {
                    sqlRawCreate.append(" UNIQUE");
                }
                sqlRawCreate.append(", ");
            }
            sqlRawCreate.delete(sqlRawCreate.length() - 2, sqlRawCreate.length());
            sqlRawCreate.append(");");
            return sqlRawCreate.toString();
        }

    public String createEntitySqlRaw() {
        StringBuilder sqlRawCreate = new StringBuilder();
        String sqlBase = "INSERT INTO ";
        String tableName = this.table.getTableName();
        List<TableColumn> columns = this.table.getColumns();
        sqlRawCreate.append(sqlBase).append(tableName).append(" (");
        int count = 0;
        for (TableColumn column : columns) {
            if (column.isAutoincrement()) {
                continue;
            }
            sqlRawCreate.append(column.getName()).append(", ");
            count++;
        }
        sqlRawCreate.delete(sqlRawCreate.length() - 2, sqlRawCreate.length());
        sqlRawCreate.append(") VALUES (");
        sqlRawCreate.append("?, ".repeat(Math.max(0, count)));
        sqlRawCreate.delete(sqlRawCreate.length() - 2, sqlRawCreate.length());
        sqlRawCreate.append(");");
        return sqlRawCreate.toString();
    }

    public String readAllEntitySqlRaw(List<String> excludeColumns, int limit, int offset)
            /*
             if excludeColumns is empty, then all columns will be selected
             if limit is 0, then all rows will be selected
             if offset is 0, then all rows will be selected from the beginning
             */
    {
        StringBuilder sqlRawCreate = new StringBuilder();
        String sqlBase = "SELECT ";
        String tableName = this.table.getTableName();
        List<TableColumn> columns = this.table.getColumns();
        sqlRawCreate.append(sqlBase);
        for (TableColumn column : columns) {
            if (excludeColumns.contains(column.getName())) {
                continue;
            }
            sqlRawCreate.append(column.getName()).append(", ");
        }
        sqlRawCreate.delete(sqlRawCreate.length() - 2, sqlRawCreate.length());
        sqlRawCreate.append(" FROM ").append(tableName).append(";");
        if (limit != 0) {
            sqlRawCreate.insert(sqlRawCreate.length() - 1, " LIMIT " + limit);
        }
        if (offset != 0) {
            sqlRawCreate.insert(sqlRawCreate.length() - 1, " OFFSET " + offset);
        }
        return sqlRawCreate.toString();
    }

    public String updateEntitySqlRaw() {
        StringBuilder sqlRawCreate = new StringBuilder();
        String sqlBase = "UPDATE ";
        String tableName = this.table.getTableName();
        List<TableColumn> columns = this.table.getColumns();
        sqlRawCreate.append(sqlBase).append(tableName).append(" SET ");
        for (TableColumn column : columns) {
            if (column.isPrimaryKey() || column.isAutoincrement()) {
                continue;
            }
            sqlRawCreate.append(column.getName()).append(" = ?, ");
        }
        sqlRawCreate.delete(sqlRawCreate.length() - 2, sqlRawCreate.length());
        sqlRawCreate.append(" WHERE ").append(this.table.getColumns().getFirst().getName()).append(" = ?;");
        return sqlRawCreate.toString();
    }

    public String deleteEntitySqlRaw() {
        StringBuilder sqlRawCreate = new StringBuilder();
        String sqlBase = "DELETE FROM ";
        String tableName = this.table.getTableName();
        sqlRawCreate.append(sqlBase).append(tableName).append(" WHERE ").append(this.table.getColumns().getFirst().getName()).append(" = ?;");
        return sqlRawCreate.toString();
    }

    public String readByIdEntitySqlRaw(List<String> excludeColumns) {
        StringBuilder sqlRawCreate = new StringBuilder();
        String sqlBase = "SELECT ";
        String tableName = this.table.getTableName();
        List<TableColumn> columns = this.table.getColumns();
        sqlRawCreate.append(sqlBase);
        for (TableColumn column : columns) {
            if (excludeColumns.contains(column.getName())) {
                continue;
            }
            sqlRawCreate.append(column.getName()).append(", ");
        }
        sqlRawCreate.delete(sqlRawCreate.length() - 2, sqlRawCreate.length());
        sqlRawCreate.append(" FROM ").append(tableName).append(" WHERE ").append(this.table.getColumns().getFirst().getName()).append(" = ?;");
        return sqlRawCreate.toString();
    }

    public String checkByUniqueFieldExistEntitySqlRaw(String uniqueField) {
        StringBuilder sqlRawCreate = new StringBuilder();
        String sqlBase = "SELECT ";
        String tableName = this.table.getTableName();
        List<TableColumn> columns = this.table.getColumns();
        sqlRawCreate.append(sqlBase);
        for (TableColumn column : columns) {
            if (column.getName().equals(uniqueField)) {
                sqlRawCreate.append(column.getName()).append(", ");
            }
        }
        sqlRawCreate.delete(sqlRawCreate.length() - 2, sqlRawCreate.length());
        sqlRawCreate.append(" FROM ").append(tableName).append(" WHERE ").append(uniqueField).append(" = ?;");
        return sqlRawCreate.toString();
    }


}
