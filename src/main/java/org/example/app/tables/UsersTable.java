package org.example.app.tables;

import org.example.app.tables.interfaces.ITable;

import java.util.List;

public class UsersTable implements ITable {
    public final String tableName = "users";

    public final TableColumn id = new TableColumn("id", "INTEGER", true, false, true, true);
    public final TableColumn firstName = new TableColumn("firstName", "VARCHAR(128)", false, false, false, false);
    public final TableColumn lastName = new TableColumn("lastName", "VARCHAR(128)", false, false, false, false);
    public final TableColumn email = new TableColumn("email", "VARCHAR(128)", false, false, false, true);
    public final TableColumn phone = new TableColumn("phone", "VARCHAR(128)", false, false, false, false);

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public List<TableColumn> getColumns() {
        return List.of(id, firstName, lastName, email, phone);
    }

    @Override
    public List<String> getColumnsNames() {
        return List.of(id.getName(), firstName.getName(), lastName.getName(), email.getName(), phone.getName());
    }
}
