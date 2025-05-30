package database;

import Users.Client;
import Users.User;

import java.sql.*;
import java.util.Arrays;

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
        Statement stmt = null;
        stmt = conn.createStatement();

        stmt.executeUpdate(query);
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
        System.out.println("insert");
        executeUpdate(getInsertQuery("users",
                new String[]{"userID", "firstname", "lastname", "passwordhash", "passwordsalt", "email"},
                new String[]{user.getId(), user.getFirstName(), user.getLastName(),
                        Arrays.toString(user.getPasswordHash().getPasswordHash()),
                        Arrays.toString(user.getPasswordHash().getPasswordSalt()), user.getEmail()}));
    }

    public void insertClient(Client client) throws SQLException {
        insertUser(client);
        System.out.println("Client inserted: " + client.getId());
        executeUpdate(getInsertQuery("clients",
                new String[]{"UserID", "LocationID", "PhoneNumber"},
                new String[]{client.getId(), "NULL", "NULL"}));
    }

    public ResultSet selectAllWhere(String tableName, String keyColumn, String keyValue) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE " + keyColumn + " = \'" + keyValue + "\';";
        Statement statement = conn.createStatement();
        return statement.executeQuery(query);
    }

}
