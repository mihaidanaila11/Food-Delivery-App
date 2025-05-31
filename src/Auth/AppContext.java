package Auth;

import Exceptions.AuthExceptions.UserAlreadyExsists;
import Exceptions.AuthExceptions.UserDoesNotExist;
import Users.User;
import database.DatabaseHandler;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.UUID;

public class AppContext {
    private final Auth auth;
    private User editingUser;
    private final DatabaseHandler db;

    public AppContext() throws UserAlreadyExsists, NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        this.db = new DatabaseHandler();
        auth = new Auth(db);

//        db.updateUserRoles(
//                this.auth.registerClient("Admin", "Admin", "Admin", "admin")
//                        .addRole(User.Roles.ADMIN)
//        );

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
}
