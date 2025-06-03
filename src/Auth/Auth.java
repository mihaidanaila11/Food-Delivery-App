package Auth;

import Exceptions.AuthExceptions.IncorrectPassword;
import Exceptions.AuthExceptions.UserAlreadyExsists;
import Exceptions.AuthExceptions.UserDoesNotExist;
import Stores.Restaurant;
import Users.Client;
import Users.Owner;
import Users.User;
import database.DatabaseHandler;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class Auth {
    private final AuthUser authUser;

    private User loggedUser;
    private Owner loggedOwner;
    private Client loggedClient;
    private final DatabaseHandler db;

    public Auth(DatabaseHandler db) {
        authUser = new AuthUser();
        loggedUser = null;
        this.db = db;
    }

    public void registerUser(String firstName, String lastName, String email, String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExsists, SQLException {

        if (db.selectAllWhere("Users", "email", email).next()) {
            throw new UserAlreadyExsists();
        }

        User newUser = authUser.register(firstName, lastName, email, password);

        db.insertUser(newUser);
        db.updateUserRoles(newUser);
        setLoggedUser(newUser);
    }

    public void completeClientRegistration(Client newClient) throws SQLException {
        db.setClientLocation(newClient, newClient.getLocation());

        db.updateRegistrationComplete(newClient, true);
        db.insertClient(newClient);
        loggedUser.setRegComplete(true);
    }

    public void completeOwnerRegistration(Owner newOwner) throws SQLException {
        newOwner.addRole(User.Roles.OWNER);

        db.updateRegistrationComplete(newOwner, true);
        db.insertOwner(newOwner);
        db.updateUserRoles(newOwner);
        setLoggedOwner(newOwner);

        loggedUser.setRegComplete(true);
    }

    public void loginUser(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        ResultSet fetchedUser = db.selectAllWhere("Users", "email", email);

        if (!fetchedUser.next()) {
            throw new UserDoesNotExist();
        }

        User user = new User(
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

        if(!authUser.checkPassword(password, user.getPasswordHash())){
            throw new IncorrectPassword();
        }

        ResultSet userRolesIds = db.selectAllWhere("UserRoles", "userID", user.getId().toString());
        String getRoleIdQuery = "SELECT RoleName FROM Roles WHERE RoleId = ?;";
        HashSet<User.Roles> userRoles = new HashSet<>();
        while(userRolesIds.next()){
            PreparedStatement stmt = db.getConnection().prepareStatement(getRoleIdQuery);
            stmt.setInt(1, userRolesIds.getInt("roleID"));

            ResultSet roleName = stmt.executeQuery();
            if(roleName.next()){
                userRoles.add(User.Roles.valueOf(roleName.getString("roleName")));
            }
        }

        user.setUserRoles(userRoles);

        try{
            loggedOwner = db.fetchOwnerById(user.getId());
        } catch (UserDoesNotExist ignored) {}

        try{
            loggedClient = db.fetchClientById(user.getId());
        } catch (UserDoesNotExist ignored) {}


        loggedUser = user;
    }

    public void logout(){
        loggedUser = null;
    }

    public User getUserById(UUID id) throws SQLException, UserDoesNotExist {
        return db.fetchUserById(id);
    }

    public void deleteUser(String email) throws UserDoesNotExist, SQLException {
        User fetchedUser = db.fetchUserByEmail(email);
        if(fetchedUser == null){
            throw new UserDoesNotExist();
        }

        if(getLoggedUser().getId().equals(fetchedUser.getId())) {
            logout();
        }
    }

    public void changeLastName(UUID id, String newLastName) throws UserDoesNotExist, SQLException {
        User fetchedUser = getUserById(id);
        fetchedUser.setLastName(newLastName);

        db.updateUser(fetchedUser);

    }

    public void changeFirstName(UUID id, String newFirstName) throws UserDoesNotExist, SQLException {
        User fetchedUser = getUserById(id);
        fetchedUser.setLastName(newFirstName);

        db.updateUser(fetchedUser);

    }

    private void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean isLoggedUser() {
        return loggedUser != null;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet fetchedUsers = db.selectAll("Users");
            while (fetchedUsers.next()) {
                users.add(new User(
                        fetchedUsers.getString("userID"),
                        fetchedUsers.getString("firstName"),
                        fetchedUsers.getString("lastName"),
                        fetchedUsers.getString("email"),
                        new PasswordHash(
                                fetchedUsers.getBytes("passwordHash"),
                                fetchedUsers.getBytes("passwordSalt")
                        ),
                        fetchedUsers.getBoolean("regComplete")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserByEmail(String email) throws SQLException {
        return db.fetchUserByEmail(email);
    }

    private void setLoggedOwner(Owner loggedOwner) {
        this.loggedOwner = loggedOwner;
        setLoggedUser(loggedOwner);
    }

    public Client getLoggedClient(){
        return loggedClient;
    }

    public Owner getLoggedOwner() { return loggedOwner; }
}
