package Menu.Options.Auth;

import Auth.AppContext;
import Exceptions.AuthExceptions.UserAlreadyExsists;
import Menu.MenuOption;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Scanner;

public class Register extends MenuOption {
    private String label;

    public Register() {
        this.setLabel("Register");
    }

    @Override
    public void action(AppContext ctx) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your first name");
        String firstName = scanner.nextLine();
        System.out.println("Enter your last name");
        String lastName = scanner.nextLine();
        System.out.println("Enter your email");
        String email = scanner.nextLine();
        System.out.println("Enter your password");
        String password = scanner.nextLine();

        try {
            ctx.getAuth().registerClient(firstName, lastName, email, password);
        } catch (UserAlreadyExsists e) {
            System.out.println(e.getMessage());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Userul nu a putut fi inregistrat in baza de date.");
        }
    }
}
