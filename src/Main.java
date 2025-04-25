import Auth.Auth;
import Auth.AuthClient;
import Exceptions.AuthExceptions.UserAlreadyExsists;
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
            System.out.println("1. Register");
            System.out.println("2. Exit");

            option = scanner.nextLine();

            if (option.equals("1")) {
                System.out.println("Enter your first name");
                String firstName = scanner.nextLine();
                System.out.println("Enter your last name");
                String lastName = scanner.nextLine();
                System.out.println("Enter your email");
                String email = scanner.nextLine();
                System.out.println("Enter your password");
                String password = scanner.nextLine();

                try{
                    auth.registerClient(firstName, lastName, email, password);
                }
                catch (UserAlreadyExsists e){
                    System.out.println(e.getMessage());
                }

            }
            else if (option.equals("2")) {
                return;
            }
        }
    }
}