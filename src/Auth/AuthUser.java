package Auth;

import Users.Client;
import Users.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class AuthUser implements AuthenticatorInterface {
    @Override
    public User register(String firstName, String lastName, String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PasswordHash passwordHash = passwordHash(password, null);
        User user = new User(firstName, lastName, email, passwordHash, false);
        user.addRole(User.Roles.USER);


        return user;
    }
}
