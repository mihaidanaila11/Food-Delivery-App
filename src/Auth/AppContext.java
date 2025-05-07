package Auth;

import Exceptions.AuthExceptions.UserAlreadyExsists;
import Exceptions.AuthExceptions.UserDoesNotExist;
import Users.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class AppContext {
    private Auth auth;
    private User editingUser;

    public AppContext() throws UserAlreadyExsists, NoSuchAlgorithmException, InvalidKeySpecException {
        auth = new Auth();

        this.auth.registerClient("Admin", "Admin", "Admin", "admin").addRole(User.Roles.ADMIN);
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
