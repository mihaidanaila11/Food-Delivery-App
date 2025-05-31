package Auth;

import Exceptions.AuthExceptions.IncorrectPassword;
import Exceptions.AuthExceptions.UserAlreadyExsists;
import Exceptions.AuthExceptions.UserDoesNotExist;
import Users.Client;
import Users.User;
import database.DatabaseHandler;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

public class Auth {
    private AuthClient authClient;

    private HashMap<String, User> users;

    private User loggedUser;
    private DatabaseHandler db;

    public Auth(DatabaseHandler db) {
        authClient = new AuthClient();
        users = new HashMap<>();
        loggedUser = null;
        this.db = db;
    }

    private Boolean UserExists(String email) {
        for(User u : users.values()) {
            if(u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public User registerClient(String firstName, String lastName, String email, String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExsists, SQLException {

        if (db.selectAllWhere("Users", "email", email).next()) {
            throw new UserAlreadyExsists();
        }

        Client newClient = authClient.register(firstName, lastName, email, password);

        db.insertClient(newClient);
        db.updateUserRoles(newClient);


        setLoggedUser(newClient);
        return newClient;
    }

    public void loginClient(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        ResultSet fetchedUser = db.selectAllWhere("Users", "email", email);

        if (!fetchedUser.next()) {
            throw new UserDoesNotExist();
        }

        Client client = new Client(
                fetchedUser.getString("userID"),
                fetchedUser.getString("firstName"),
                fetchedUser.getString("lastName"),
                fetchedUser.getString("email"),
                new PasswordHash(
                        fetchedUser.getBytes("passwordHash"),
                        fetchedUser.getBytes("passwordSalt")
                )
        );

        if(!authClient.checkPassword(password, client.getPasswordHash())){
            throw new IncorrectPassword();
        }

        ResultSet userRolesIds = db.selectAllWhere("UserRoles", "userID", client.getId());
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

        client.setUserRoles(userRoles);

        loggedUser = client;
    }

    public void logout(){
        loggedUser = null;
    }

    public User getUserById(String id) {
        for(User u : users.values()) {
            if(u.getId().equals(id)) {
                return u;
            }
        }

        return null;
    }
    public User getUserByEmail(String email) {
        return users.get(email);
    }

    public void deleteUser(String id) throws UserDoesNotExist{
        User fetchedUser = getUserById(id);
        if(fetchedUser == null){
            throw new UserDoesNotExist();
        }

        if(getLoggedUser().getId().equals(id)) {
            logout();
        }

        users.remove(fetchedUser.getEmail());
    }

    public void changeLastName(String email, String newLastName) throws UserDoesNotExist{
        User fetchedUser = users.get(email);
        if(fetchedUser == null){
            throw new UserDoesNotExist();
        }

        users.get(email).setLastName(newLastName);

    }

    public void changeFirstName(String email, String newFirstName) throws UserDoesNotExist{
        User fetchedUser = users.get(email);
        if(fetchedUser == null){
            throw new UserDoesNotExist();
        }

        users.get(email).setFirstName(newFirstName);

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

    public HashMap<String, User> getUsers() {
        return users;
    }
}
