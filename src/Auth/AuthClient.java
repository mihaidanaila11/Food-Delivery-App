package Auth;

import Users.Client;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class AuthClient implements AuthenticatorInterface {
    @Override
    public Client register(String firstName, String lastName, String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PasswordHash passwordHash = passwordHash(password, null);

        return new Client(firstName, lastName, email, passwordHash);
    }
}
