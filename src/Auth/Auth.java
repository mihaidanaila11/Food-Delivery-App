package Auth;

import Exceptions.AuthExceptions.UserAlreadyExsists;
import Users.Client;
import Users.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

public class Auth {
    private AuthClient authClient;

    private HashMap<String, User> users;

    public Auth() {
        authClient = new AuthClient();
        users = new HashMap<>();
    }

    public void registerClient(String firstName, String lastName, String email, String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExsists {
        if (users.containsKey(email)) {
            throw new UserAlreadyExsists();
        }

        Client newClient = authClient.register(firstName, lastName, email, password);
        users.put(newClient.getEmail(), newClient);
    }

    public HashMap<String, User> getUsers() {
        return users;
    }
}
