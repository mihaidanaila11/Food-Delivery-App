package database;

import Auth.PasswordHash;
import Exceptions.AuthExceptions.UserDoesNotExist;
import Location.City;
import Location.State;
import Users.Client;
import Users.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class DatabaseHandler {
    private Connection conn = null;

    public DatabaseHandler() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost/FoodDeliveryApp?" +
                            "user=ImiEFoame&password=ImiEFoamepa55");

            // Do something with the Connection
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public Connection getConnection() {
        return conn;
    }

    private void executeUpdate(String query) throws SQLException {
        Statement stmt = conn.createStatement();

        stmt.executeUpdate(query);
    }

    private void executeUpdate(PreparedStatement stmt) throws SQLException {
        if (stmt != null) {
            stmt.executeUpdate();
        }
    }

    private String getInsertQuery(String tableName, String[] columns, String[] values) {
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " (");
        for (String column : columns) {
            query.append(column).append(", ");
        }
        query.setLength(query.length() - 2);
        query.append(") VALUES (");
        for (String value : values) {
            if(value.equals("NULL")) {
                query.append(value).append(", ");
                continue;
            }
            query.append("'").append(value).append("', ");
        }
        query.setLength(query.length() - 2);
        query.append(")");
        return query.toString();
    }

    private void insertUser(User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO Users (userID, firstname, lastname, passwordhash, passwordsalt, email) " +
                        "VALUES (?, ?, ?, ?, ?, ?);");

        stmt.setString(1, user.getId().toString());
        stmt.setString(2, user.getFirstName());
        stmt.setString(3, user.getLastName());
        stmt.setBytes(4, user.getPasswordHash().getPasswordHash());
        stmt.setBytes(5, user.getPasswordHash().getPasswordSalt());
        stmt.setString(6, user.getEmail());
        executeUpdate(stmt);
    }

    public void insertClient(Client client) throws SQLException {
        insertUser(client);
        System.out.println("Client inserted: " + client.getId());
        executeUpdate(getInsertQuery("clients",
                new String[]{"UserID", "LocationID", "PhoneNumber"},
                new String[]{client.getId().toString(), "NULL", "NULL"}));
    }

    public void updateUserRoles(User user) throws SQLException {
        HashSet<User.Roles> roles = user.getRoles();
        ArrayList<String> rolesArray = new ArrayList<>();

        for(User.Roles role : roles){
            rolesArray.add(role.name());
        }

        HashMap<String, Integer> roleIds = new HashMap<>();

        String query = "SELECT RoleID FROM Roles WHERE RoleName = ?;";
        PreparedStatement stmt = conn.prepareStatement(query);
        for(String role : rolesArray){
            try{
                stmt.setString(1, role);
                ResultSet rs = stmt.executeQuery();

                if(rs.next()){
                    roleIds.put(role, rs.getInt("RoleID"));
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }

        for(String role : rolesArray) {
            try {
                String insertQuery =    "INSERT INTO UserRoles (UserID, RoleId) " +
                                        "VALUES (?, ?);";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, user.getId().toString());
                insertStmt.setInt(2, roleIds.get(role));

                executeUpdate(insertStmt);

            } catch (SQLException ignored) {

            }
        }
    }

    public ResultSet selectAllWhere(String tableName, String keyColumn, String keyValue) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE " + keyColumn + " = '" + keyValue + "`';";
        Statement statement = conn.createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet selectColumnWhere(String tableName, String columnName, String keyColumn, String keyValue) throws SQLException {
        String query = "SELECT " + columnName + " FROM " + tableName + " WHERE " + keyColumn + " = '" + keyValue + "';";
        Statement statement = conn.createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet selectAllWhere(String tableName, String keyColumn, int keyValue) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE " + keyColumn + " = " + keyValue + ";";
        Statement statement = conn.createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet selectAll(String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName + ";";
        Statement statement = conn.createStatement();
        return statement.executeQuery(query);
    }

    public User fetchUserById(UUID id) throws SQLException {
        ResultSet fetchedUser = selectAllWhere("Users", "userId", id.toString());

        if (!fetchedUser.next()) {
            throw new UserDoesNotExist();
        }

        return new User(
                fetchedUser.getString("userID"),
                fetchedUser.getString("firstName"),
                fetchedUser.getString("lastName"),
                fetchedUser.getString("email"),
                new PasswordHash(
                        fetchedUser.getBytes("passwordHash"),
                        fetchedUser.getBytes("passwordSalt")
                )
        );
    }

    public User fetchUserByEmail(String email) throws SQLException, UserDoesNotExist {
        ResultSet fetchedUser = selectAllWhere("Users", "email", email);

        if (!fetchedUser.next()) {
            throw new UserDoesNotExist();
        }

        return new User(
                fetchedUser.getString("userID"),
                fetchedUser.getString("firstName"),
                fetchedUser.getString("lastName"),
                fetchedUser.getString("email"),
                new PasswordHash(
                        fetchedUser.getBytes("passwordHash"),
                        fetchedUser.getBytes("passwordSalt")
                )
        );
    }

    public void updateUser(User user) throws SQLException {
        String query = "UPDATE Users SET firstName = ?, lastName = ?, email = ? WHERE userID = ?;";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, user.getFirstName());
        stmt.setString(2, user.getLastName());
        stmt.setString(3, user.getEmail());
        stmt.setString(4, user.getId().toString());
        executeUpdate(stmt);
    }

    public void insertCity(City city) {
        String query = "INSERT INTO Cities (CityName, StateID) VALUES (?, ?);";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, city.getName());
            stmt.setInt(2, city.getStateId());

            executeUpdate(stmt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }
    }

    public void insertState(State state) {
        String query = "INSERT INTO States (StateName, CountryID) VALUES (?, ?);";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, state.getStateName());
            stmt.setInt(2, state.getCountry().getID());

            executeUpdate(stmt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
