package org.example.app.tables;

public class TableColumn {
    private final String name;
    private final String type;
    private final boolean isPrimaryKey;
    private final boolean isNullable;
    private final boolean isAutoincrement;
    private final boolean isUnique;

    public TableColumn(String name,
                       String type,
                       boolean isPrimaryKey,
                       boolean isNullable,
                       boolean isAutoincrement,
                       boolean isUnique
    ) {
        this.name = name;
        this.type = type;
        this.isPrimaryKey = isPrimaryKey;
        this.isNullable = isNullable;
        this.isAutoincrement = isAutoincrement;
        this.isUnique = isUnique;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }


    public boolean isNullable() {
        return isNullable;
    }

    public boolean isAutoincrement() {
        return isAutoincrement;
    }

    public boolean isUnique() {
        return isUnique;
    }
}
