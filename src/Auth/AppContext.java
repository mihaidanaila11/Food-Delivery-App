package Auth;

import Exceptions.AuthExceptions.UserAlreadyExsists;
import Exceptions.AuthExceptions.UserDoesNotExist;
import Products.Product;
import Stores.Restaurant;
import Users.User;
import database.DatabaseHandler;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.UUID;

public class AppContext {
    private final Auth auth;
    private User editingUser;
    private Restaurant editingRestaurant;
    private final DatabaseHandler db;

    public AppContext() throws UserAlreadyExsists, NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        this.db = DatabaseHandler.getInstance();
        auth = new Auth(db);

        try {
            db.updateUserRoles(
                    this.auth.registerUser("Admin", "Admin", "Admin", "admin")
                            .addRole(User.Roles.ADMIN)
                            .setRegComplete(true)
            );

            db.updateRegistrationComplete(auth.getLoggedUser(), true);
        } catch (UserAlreadyExsists ignored) {}

        this.auth.logout();
        editingUser = null;
    }

    public Auth getAuth() {
        return auth;
    }
    public void setEditingUser(String email) throws SQLException, UserDoesNotExist {
        User fetchedUser = auth.getUserByEmail(email);

        this.editingUser = fetchedUser;
    }
    public User getEditingUser() { return editingUser; }
    public Restaurant getEditingRestaurant() { return editingRestaurant; }
    public DatabaseHandler getDb() { return db; }

    public void setEditingRestaurant(Restaurant restaurant) { this.editingRestaurant = restaurant; }
}
