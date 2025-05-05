import Exceptions.AuthExceptions.UserAlreadyExsists;
import Menu.Menu;
import Menu.MenuOption;
import Menu.Options.Admin.AdminPanel;
import Menu.Options.Auth.Login;
import Menu.Options.Auth.Logout;
import Menu.Options.Auth.Register;
import Menu.Options.Exit;
import Users.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Scanner;

import Auth.AppContext;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExsists {
        Scanner scanner = new Scanner(System.in);
        String option = null;

        AppContext context = new AppContext();

        while(true){
            if(!context.getAuth().isLoggedUser()){
                ArrayList<MenuOption> options = new ArrayList<>();
                options.add(new Register());
                options.add(new Login());
                options.add(new Exit());
                Menu welcomeMenu = new Menu(options);

                welcomeMenu.show(context);
            }
            else{
                User loggedUser = context.getAuth().getLoggedUser();
                System.out.println("Role: " + loggedUser.getRoles());
                ArrayList<MenuOption> options = new ArrayList<>();
                options.add(new AdminPanel().addNeededRole(User.Roles.ADMIN));
                options.add(new Logout());
                options.add(new Exit());
                Menu menu = new Menu(options);

                menu.setBanner("Welcome back, " + loggedUser.getFirstName() + "\n");
                menu.show(context);

            }
        }
    }
}