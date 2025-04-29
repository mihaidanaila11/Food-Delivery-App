import Auth.Auth;
import Auth.AuthClient;
import Exceptions.AuthExceptions.UserAlreadyExsists;
import Exceptions.AuthExceptions.UserDoesNotExist;
import Users.Client;
import Users.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Scanner scanner = new Scanner(System.in);
        String option = null;

        Auth auth = new Auth();

        while(true){
            if(!auth.isLoggedUser()){
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");

                option = scanner.nextLine();

                switch (option) {
                    case "1" -> {
                        System.out.println("Enter your first name");
                        String firstName = scanner.nextLine();
                        System.out.println("Enter your last name");
                        String lastName = scanner.nextLine();
                        System.out.println("Enter your email");
                        String email = scanner.nextLine();
                        System.out.println("Enter your password");
                        String password = scanner.nextLine();

                        try {
                            auth.registerClient(firstName, lastName, email, password);
                        } catch (UserAlreadyExsists e) {
                            System.out.println(e.getMessage());
                        }

                    }
                    case "2" -> {
                        System.out.println("Enter your email");
                        String email = scanner.nextLine();
                        System.out.println("Enter your password");
                        String password = scanner.nextLine();
                        try{
                            auth.loginClient(email, password);
                        } catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                    case "3" -> {
                        return;
                    }
                }
            }
            else{
                User loggedUser = auth.getLoggedUser();
                System.out.println("Welcome back, " + loggedUser.getFirstName() + "\n");

                System.out.println("1. Logout");

                option = scanner.nextLine();
                switch (option) {
                    case "1" -> {
                        auth.logout();
                    }
                }
            }

        }
    }
}