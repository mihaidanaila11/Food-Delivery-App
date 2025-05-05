package Auth;

import Exceptions.AuthExceptions.IncorrectPassword;
import Exceptions.AuthExceptions.UserAlreadyExsists;
import Exceptions.AuthExceptions.UserDoesNotExist;
import Users.Client;
import Users.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

public class Auth {
    private AuthClient authClient;

    private HashMap<String, User> users;

    private User loggedUser;

    public Auth() {
        authClient = new AuthClient();
        users = new HashMap<>();
        loggedUser = null;
    }

    public User registerClient(String firstName, String lastName, String email, String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExsists {
        if (users.containsKey(email)) {
            throw new UserAlreadyExsists();
        }

        Client newClient = authClient.register(firstName, lastName, email, password);
        users.put(newClient.getEmail(), newClient);
        setLoggedUser(newClient);

        return newClient;
    }

    public void loginClient(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (!users.containsKey(email)) {
            throw new UserDoesNotExist();
        }

        if(!authClient.checkPassword(password, users.get(email).getPasswordHash())){
            throw new IncorrectPassword();
        }

        loggedUser = users.get(email);
    }
    public void logout(){
        loggedUser = null;
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
