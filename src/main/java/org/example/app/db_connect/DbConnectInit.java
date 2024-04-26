package org.example.app.db_connect;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.app.tables.UsersTable;
import org.example.app.utils.SqlRawCreator;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbConnectInit {
    private final Dotenv dotenv;
    private Connection connection;
    private final String dbUrl;
    private boolean isConnected;
    private final List<String> connectErrors;
    private static final Logger DbConnectInitLogger = Logger.getLogger(DbConnectInit.class.getName());

    public DbConnectInit() {
        this.dotenv = Dotenv.configure().directory("src/main/resources").load();
        this.dbUrl = String.format("jdbc:mysql://%s:%s/%s", this.dotenv.get("MYSQL_HOST"), this.dotenv.get("MYSQL_HOST_PORT"), this.dotenv.get("MYSQL_DATABASE"));
        this.isConnected = false;
        this.connectErrors = new ArrayList<>();
        setConnection();
        initDBUserTable();
    }

    private void initDBUserTable() {
        if (this.isConnected) {
            UsersTable usersTable = new UsersTable();
            SqlRawCreator<UsersTable> sqlCreator = new SqlRawCreator<>();
            sqlCreator.setTable(usersTable);
            String stmt = sqlCreator.createTableSqlRaw();
            try {
                this.connection.createStatement().executeUpdate(stmt);
            } catch (SQLException e) {
                this.connectErrors.add(e.getMessage());
                DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    private void setConnection() {
        try {
            this.connection = DriverManager.getConnection(this.dbUrl, this.dotenv.get("MYSQL_USER"), this.dotenv.get("MYSQL_PASSWORD"));
            this.isConnected = true;
        } catch (SQLException e) {
            this.connectErrors.add(e.getMessage());
            this.isConnected = false;
            DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            this.connection.close();
            this.isConnected = false;
        } catch (SQLException e) {
            this.connectErrors.add(e.getMessage());
            DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
        }
    }


    public Connection getConnection() {
        return this.connection;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public List<String> getConnectErrors() {
        return this.connectErrors;
    }


}


