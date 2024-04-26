package org.example.app.repository;

import org.example.app.db_connect.DbConnectInit;
import org.example.app.entity.User;
import org.example.app.repository.interfaces.IRepository;
import org.example.app.tables.UsersTable;
import org.example.app.utils.ActionAnswer;
import org.example.app.utils.SqlRawCreator;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.List;
import java.util.Optional;

public class UserRepository implements IRepository<User> {
    private final Connection connection;
    private final SqlRawCreator<UsersTable> sqlCreator;
    private static final Logger DbConnectInitLogger = Logger.getLogger(DbConnectInit.class.getName());

    public UserRepository(Connection connection) {
        this.connection = connection;
        this.sqlCreator = new SqlRawCreator<>();
        UsersTable usersTable = new UsersTable();
        this.sqlCreator.setTable(usersTable);
    }

    @Override
    public ActionAnswer create(User obj) {
        ActionAnswer actionAnswer = new ActionAnswer();
        boolean isEmailExists = checkEmailExists(obj.getEmail());
        if (isEmailExists) {
            actionAnswer.addError("Email already exists");
            return actionAnswer;
        }
        String baseStmt = this.sqlCreator.createEntitySqlRaw();
        try (PreparedStatement createStmt = this.connection.prepareStatement(baseStmt)) {
            createStmt.setString(1, obj.getFirstName());
            createStmt.setString(2, obj.getLastName());
            createStmt.setString(3, obj.getEmail());
            createStmt.setString(4, obj.getPhone());

            createStmt.executeUpdate();
            actionAnswer.setIsSuccess();
            actionAnswer.setSuccessMsg("User created successfully");
            return actionAnswer;
        } catch (SQLException e) {
            DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
            actionAnswer.addError(e.getMessage());
            return actionAnswer;
        }
    }

    public boolean checkEmailExists(String emailValue) {
        String checkEmailExistsSqlRaw = this.sqlCreator.checkByUniqueFieldExistEntitySqlRaw("email");
        try (PreparedStatement checkEmailExistsStmt = this.connection.prepareStatement(checkEmailExistsSqlRaw)) {
            checkEmailExistsStmt.setString(1, emailValue);
            ResultSet rs = checkEmailExistsStmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<List<User>> readAll(List<String> excludeColumns, int limit, int offset) {
        try (Statement getStmt = this.connection.createStatement()) {
            String getAllSql = this.sqlCreator.readAllEntitySqlRaw(excludeColumns,limit,offset);
            ResultSet rs = getStmt.executeQuery(getAllSql);
            List<User> querySetResult = addDataToQuerySet(rs, excludeColumns);
            if (querySetResult.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(querySetResult);
        } catch (SQLException e) {
            DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public ActionAnswer update(User obj) {
        ActionAnswer actionAnswer = new ActionAnswer();
        String baseStmt = this.sqlCreator.updateEntitySqlRaw();
        try (PreparedStatement updateStmt = this.connection.prepareStatement(baseStmt)) {
            updateStmt.setString(1, obj.getFirstName());
            updateStmt.setString(2, obj.getLastName());
            updateStmt.setString(3, obj.getEmail());
            updateStmt.setString(4, obj.getPhone());
            updateStmt.setLong(5, obj.getId());

            updateStmt.executeUpdate();
            actionAnswer.setIsSuccess();
            actionAnswer.setSuccessMsg("User update successfully");
            return actionAnswer;
        } catch (SQLException e) {
            DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
            actionAnswer.addError(e.getMessage());
            return actionAnswer;
        }
    }

    @Override
    public ActionAnswer delete(Long id) {
        ActionAnswer actionAnswer = new ActionAnswer();
        String baseStmt = this.sqlCreator.deleteEntitySqlRaw();
        try (PreparedStatement deleteStmt = this.connection.prepareStatement(baseStmt)) {
            deleteStmt.setLong(1, id);
            deleteStmt.executeUpdate();
            actionAnswer.setIsSuccess();
            actionAnswer.setSuccessMsg("User update successfully");
            return actionAnswer;
        } catch (SQLException e) {
            DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
            actionAnswer.addError(e.getMessage());
            return actionAnswer;
        }
    }

    @Override
    public Optional<List<User>> readById(Long id, List<String> excludeColumns) {
        String getUserByIdSqlRaw = this.sqlCreator.readByIdEntitySqlRaw(excludeColumns);
        try (PreparedStatement getByIdStmt = this.connection.prepareStatement(getUserByIdSqlRaw)) {
            getByIdStmt.setLong(1, id);
            ResultSet rs = getByIdStmt.executeQuery();
            List<User> querySetResult = addDataToQuerySet(rs, excludeColumns);
            if (querySetResult.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(querySetResult);
        }catch (SQLException e) {
            DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
            return Optional.empty();
        }

    }

    private List<User> addDataToQuerySet(ResultSet rs, List<String> excludeColumns) throws SQLException {
        List<User> querySet = new ArrayList<>();
        while (rs.next()) {
            querySet.add(new User(
                    !excludeColumns.contains("id") ? rs.getLong("id") : -1,
                    !excludeColumns.contains("firstName") ? rs.getString("firstName") : "",
                    !excludeColumns.contains("lastName") ? rs.getString("lastName") : "",
                    !excludeColumns.contains("email") ? rs.getString("email") : "",
                    !excludeColumns.contains("phone") ? rs.getString("phone") : ""
            ));
        }
        return querySet;
    }

}
