package Auth;

import Users.Client;
import Users.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class AuthClient implements AuthenticatorInterface {
    @Override
    public Client register(String firstName, String lastName, String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PasswordHash passwordHash = passwordHash(password, null);
        Client client = new Client(firstName, lastName, email, passwordHash);
        client.addRole(User.Roles.USER);


        return client;
    }
}
