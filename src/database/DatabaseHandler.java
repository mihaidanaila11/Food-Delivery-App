package database;

import Auth.PasswordHash;
import Exceptions.AuthExceptions.UserDoesNotExist;
import Location.City;
import Location.Location;
import Location.State;
import Orders.Order;
import Products.Product;
import Stores.Restaurant;
import Stores.RestaurantOperations;
import Users.Client;
import Users.Owner;
import Users.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import Location.Country;

public class DatabaseHandler {
    private static DatabaseHandler instance = null;
    private Connection conn = null;

    public static DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

    private DatabaseHandler() {
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

    private void commit(){
        try {
            conn.commit();
        } catch (SQLException ignored){}

    }

    private void executeUpdate(PreparedStatement stmt) throws SQLException {
        if (stmt != null) {
            stmt.executeUpdate();
            commit();
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

    public void insertUser(User user) throws SQLException {
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
        String query = "INSERT INTO Clients (UserID, LocationID, PhoneNumber) " +
                "VALUES (?, ?, NULL);";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, client.getId().toString());
        stmt.setInt(2, getOrInsertLocation(client.getLocation()));
        System.out.println("Client inserted: " + client.getId());

        executeUpdate(stmt);
    }

    public void updateUserRoles(User user) throws SQLException {
        HashSet<User.Roles> roles = user.getRoles();

        HashMap<String, Integer> roleIds = new HashMap<>();

        String query = "SELECT RoleID FROM Roles WHERE RoleName = ?;";
        PreparedStatement stmt = conn.prepareStatement(query);
        for(User.Roles role : roles){
            try{
                stmt.setString(1, role.name());
                ResultSet rs = stmt.executeQuery();

                if(rs.next()){
                    roleIds.put(role.name(), rs.getInt("RoleID"));
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }

        for(User.Roles role : roles) {
            try {
                String insertQuery =    "INSERT INTO UserRoles (UserID, RoleId) " +
                                        "VALUES (?, ?);";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, user.getId().toString());
                insertStmt.setInt(2, roleIds.get(role.name()));

                executeUpdate(insertStmt);

            } catch (SQLException ignored) {}
        }
    }

    public ResultSet selectAllWhere(String tableName, String keyColumn, String keyValue) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE " + keyColumn + " = '" + keyValue + "';";
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
                ),
                fetchedUser.getBoolean("regComplete")
        );
    }

    public User fetchUserById(String id) throws SQLException {
        ResultSet fetchedUser = selectAllWhere("Users", "userId", id);

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
                ),
                fetchedUser.getBoolean("regComplete")
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
                ),
                fetchedUser.getBoolean("regComplete")
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

    private int getOrInsertLocation(Location location) throws SQLException {
        String query = "SELECT LocationID FROM Locations WHERE CityID = ? " +
                "AND UPPER(StreetName) = UPPER(?) " +
                "AND UPPER(PostalCode) = UPPER(?) " +
                "AND LocationNumber = ?;";


            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, location.getCity().getID());
            stmt.setString(2, location.getStreet());
            stmt.setString(3, location.getPostalCode());
            stmt.setInt(4, location.getLocationNumber());

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getInt("LocationID");
            } else {
                String insertQuery = "INSERT INTO Locations (CityID, StreetName, PostalCode, LocationNumber) " +
                        "VALUES (?, ?, ?, ?);";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                insertStmt.setInt(1, location.getCity().getID());
                insertStmt.setString(2, location.getStreet());
                insertStmt.setString(3, location.getPostalCode());
                insertStmt.setInt(4, location.getLocationNumber());

                executeUpdate(insertStmt);

                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if(generatedKeys.next()){
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Inserting location failed, no ID obtained.");
                }
            }
    }

    public Country fetchCountryById(int countryId) throws SQLException {
        String query = "SELECT * FROM Countries WHERE CountryID = ?;";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, countryId);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            return new Country(
                    rs.getString("CountryName"),
                    rs.getInt("CountryID")
            );
        } else {
            throw new SQLException("Country not found with ID: " + countryId);
        }
    }

    public State fetchStateById(int stateId) throws SQLException {
        String query = "SELECT * FROM States WHERE StateID = ?;";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, stateId);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            return new State(
                    rs.getInt("StateID"),
                    rs.getString("StateName"),
                    fetchCountryById(rs.getInt("CountryID"))
            );
        } else {
            throw new SQLException("State not found with ID: " + stateId);
        }
    }

    public City fetchCityById(int cityId) throws SQLException {
        String query = "SELECT * FROM Cities WHERE CityID = ?;";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, cityId);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            return new City(
                    rs.getInt("CityID"),
                    rs.getString("CityName"),
                    fetchStateById(rs.getInt("StateID"))
            );
        } else {
            throw new SQLException("City not found with ID: " + cityId);
        }
    }

    public Location fetchLocationById(int locationId) throws SQLException {
        String query = "SELECT * FROM Locations WHERE LocationID = ?;";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, locationId);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            return new Location(
                    fetchCityById(rs.getInt("CityID")),
                    rs.getString("StreetName"),
                    rs.getString("PostalCode"),
                    rs.getInt("LocationNumber")
            );
        } else {
            throw new SQLException("Location not found with ID: " + locationId);
        }
    }

    public void setClientLocation(Client client, Location location) {
        String query = "UPDATE Clients SET locationID = ? WHERE userID = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, getOrInsertLocation(location));
            stmt.setString(2, client.getId().toString());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateRegistrationComplete(User user, boolean b) {
        String query = "UPDATE Users SET regComplete = ? WHERE userID = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setBoolean(1, b);
            stmt.setString(2, user.getId().toString());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void insertOwner(Owner newOwner) throws SQLException {
        String query = "INSERT INTO Owners (UserID) VALUES (?);";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, newOwner.getId().toString());
        executeUpdate(stmt);

    }

    public ResultSet selectAllOrderedBy(String tableName, String orderByColumn) throws SQLException {
        String query = "SELECT * FROM " + tableName + " ORDER BY " + orderByColumn + ";";
        Statement statement = conn.createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet selectAllWhereOrdered(String tableName, String orderByColumn,
                                           String keyColumn, int keyValue) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE " + keyColumn + " = " + keyValue +
                " ORDER BY " + orderByColumn + ";";

        Statement statement = conn.createStatement();
        return statement.executeQuery(query);
    }

    public void insertRestaurant(Restaurant restaurant) throws SQLException {
        String query = "INSERT INTO Restaurants (RestaurantID, RestaurantName, LocationID, Description) " +
                "VALUES (?, ?, ?, ?);";


        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, restaurant.getID().toString());
        stmt.setString(2, restaurant.getName());
        stmt.setInt(3, getOrInsertLocation(restaurant.getLocation()));
        stmt.setString(4, restaurant.getDescription());

        executeUpdate(stmt);

    }

    public void addRestaurantOwner(Restaurant restaurant, Owner owner) throws SQLException {
        String query = "INSERT INTO ownsRestaurant (RestaurantID, UserID) VALUES (?, ?);";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, restaurant.getID().toString());
        stmt.setString(2, owner.getId().toString());

        executeUpdate(stmt);

    }

    private ArrayList<Restaurant> fetchRestaurantsByOwnerId(UUID id) throws SQLException {
        String query = "SELECT * FROM Restaurants r " +
                "JOIN ownsRestaurant o ON r.RestaurantID = o.RestaurantID " +
                "WHERE o.UserID = ?;";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, id.toString());
        ResultSet rs = stmt.executeQuery();

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        while(rs.next()) {
            Restaurant restaurant = new Restaurant(
                    rs.getString("RestaurantName"),
                    fetchLocationById(rs.getInt("LocationID"))
            );
            restaurant.setID(UUID.fromString(rs.getString("RestaurantID")));

            ArrayList <Product> products = new ArrayList<>();
            ResultSet productRs = fetchProductsByRestaurant(restaurant);
            while(productRs.next()) {
                Product product = new Product(
                        productRs.getString("ProductName"),
                        productRs.getFloat("Price"),
                        productRs.getString("ProductDescription"),
                        UUID.fromString(productRs.getString("ProductID")),
                        restaurant
                );
                products.add(product);
            }

            restaurant.setProducts(products);
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    public Owner fetchOwnerById(UUID id) throws SQLException {
        String query = "SELECT * FROM Owners WHERE UserID = ?;";


        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, id.toString());
        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            Owner newOwner = new Owner(
                    fetchUserById(rs.getString("UserID"))
            );

            ArrayList<Restaurant> ownedRestaurants = fetchRestaurantsByOwnerId(newOwner.getId());

            for(Restaurant restaurant : ownedRestaurants) {
                restaurant.setOwner(newOwner);
            }

            newOwner.setRestaurants(ownedRestaurants);

            return newOwner;
        } else {
            throw new UserDoesNotExist();
        }
    }

    public Client fetchClientById(UUID id) throws SQLException {
        String query = "SELECT * FROM Clients WHERE UserID = ?;";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, id.toString());
        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            return new Client(
                    fetchUserById(rs.getString("UserID")),
                    fetchLocationById(rs.getInt("LocationID"))
            );
        } else {
            throw new UserDoesNotExist();
        }

    }

    public ResultSet fetchRestaurantsByCity(City city) throws SQLException {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        String query = "SELECT RestaurantID, RestaurantName, LocationID, Description " +
                "FROM Restaurants JOIN Locations USING (LocationID) " +
                "JOIN Cities USING (CityID) " +
                "WHERE cityID = ?";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, city.getID());
        ResultSet rs = stmt.executeQuery();

        return rs;
    }

    public void insertProduct(Product product, Restaurant restaurant) throws SQLException{
        String query = "INSERT INTO Products (ProductID, RestaurantID, ProductName, Price, ProductDescription) " +
                "VALUES (?, ?, ?, ?, ?);";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, product.getID().toString());
        stmt.setString(2, restaurant.getID().toString());
        stmt.setString(3, product.getName());
        stmt.setFloat(4, product.getPrice());
        stmt.setString(5, product.getDescription());

        executeUpdate(stmt);
    }

    public ResultSet fetchProductsByRestaurant(Restaurant restaurant) throws SQLException{
        String query = "SELECT * FROM Products WHERE RestaurantID = ?;";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, restaurant.getID().toString());
        return stmt.executeQuery();

    }

    public void deleteUser(User user) throws SQLException{
        String query = "DELETE FROM Users WHERE userID = ?;";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, user.getId().toString());

        executeUpdate(stmt);
    }

    public void updateRestaurantName(Restaurant restaurant, String newName) throws SQLException {
        String query = "UPDATE Restaurants SET RestaurantName = ? WHERE RestaurantID = ?;";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, newName);
        stmt.setString(2, restaurant.getID().toString());
        executeUpdate(stmt);

    }

    public void updateRestaurantDescription(Restaurant restaurant, String newDescription) throws SQLException {
        String query = "UPDATE Restaurants SET Description = ? WHERE RestaurantID = ?;";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, newDescription);
        stmt.setString(2, restaurant.getID().toString());
        executeUpdate(stmt);

    }

    public void deleteRestaurant(Restaurant restaurant) throws SQLException {
        String query = "DELETE FROM Restaurants WHERE RestaurantID = ?;";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, restaurant.getID().toString());
        executeUpdate(stmt);
    }

    public void updateProductName(Product product, String newName) throws SQLException{
        String query = "UPDATE Products SET ProductName = ? WHERE ProductID = ? " +
                "AND RestaurantID = ?;";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, newName);
        stmt.setString(2, product.getID().toString());
        stmt.setString(3, product.getRestaurant().getID().toString());
        executeUpdate(stmt);

    }

    public void updateProductPrice(Product product, Float newPrice) throws SQLException{
        String query = "UPDATE Products SET Price = ? WHERE ProductID = ? " +
                "AND RestaurantID = ?;";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setFloat(1, newPrice);
        stmt.setString(2, product.getID().toString());
        stmt.setString(3, product.getRestaurant().getID().toString());
        executeUpdate(stmt);

    }

    public void updateProductDescription(Product product, String newDescription) throws SQLException{
        String query = "UPDATE Products SET ProductDescription = ? WHERE ProductID = ? " +
                "AND RestaurantID = ?;";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, newDescription);
        stmt.setString(2, product.getID().toString());
        stmt.setString(3, product.getRestaurant().getID().toString());
        executeUpdate(stmt);
    }

    public void deleteProduct(Product product) throws SQLException {
        String query = "DELETE FROM Products WHERE ProductID = ? AND RestaurantID = ?;";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, product.getID().toString());
        stmt.setString(2, product.getRestaurant().getID().toString());
        executeUpdate(stmt);
    }

    public ResultSet getAvailableCouriers(Location location) throws SQLException {
        String query = "SELECT * FROM Couriers " +
                "WHERE UserID NOT IN (SELECT CourierID FROM Orders WHERE Delivered = false) " +
                "AND WorkingCityID = ?;";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, location.getCity().getID());

        return stmt.executeQuery();
    }

    public void assignCourierToOrder(UUID orderId, UUID courierId) throws SQLException {
        String query = "UPDATE Orders SET CourierID = ? WHERE OrderID = ?;";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, courierId.toString());
        stmt.setString(2, orderId.toString());

        executeUpdate(stmt);
    }

    public void insertOrder(Order order) throws SQLException {
        String query = "INSERT INTO Orders (OrderID, ClientID, CourierID, Delivered) " +
                "VALUES (?, ?, NULL, false);";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, order.getID().toString());
        stmt.setString(2, order.getClient().getId().toString());
        executeUpdate(stmt);

        for (Product product : order.getCart().getProducts()) {
            String insertProductQuery = "INSERT INTO ordercontainedproducts (OrderID, ProductID, Amount) VALUES (?, ?, ?);";
            PreparedStatement productStmt = conn.prepareStatement(insertProductQuery);
            productStmt.setString(1, order.getID().toString());
            productStmt.setString(2, product.getID().toString());
            productStmt.setInt(3, order.getCart().getQuantity(product));
            executeUpdate(productStmt);
        }

    }
}
