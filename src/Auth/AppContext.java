package Auth;

import Exceptions.AuthExceptions.UserAlreadyExsists;
import Exceptions.AuthExceptions.UserDoesNotExist;
import Users.User;
import database.DatabaseHandler;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class AppContext {
    private Auth auth;
    private User editingUser;
    private DatabaseHandler db;

    public AppContext() throws UserAlreadyExsists, NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        this.db = new DatabaseHandler();
        auth = new Auth(db);

        db.updateUserRoles(
                this.auth.registerClient("Admin", "Admin", "Admin", "admin")
                        .addRole(User.Roles.ADMIN)
        );

        this.auth.logout();
        editingUser = null;
    }

    public Auth getAuth() {
        return auth;
    }
    public void setEditingUser(String id) {
        User fetchedUser = auth.getUserById(id);

        if(fetchedUser == null) {
            throw new UserDoesNotExist();
        }
        this.editingUser = fetchedUser;
    }
    public User getEditingUser() { return editingUser; }
}
