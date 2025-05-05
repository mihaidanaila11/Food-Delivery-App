package Auth;

import Exceptions.AuthExceptions.UserAlreadyExsists;
import Users.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class AppContext {
    private Auth auth;

    public AppContext() throws UserAlreadyExsists, NoSuchAlgorithmException, InvalidKeySpecException {
        auth = new Auth();

        this.auth.registerClient("Admin", "Admin", "Admin", "admin").addRole(User.Roles.ADMIN);
        this.auth.logout();
    }

    public Auth getAuth() {
        return auth;
    }
}
