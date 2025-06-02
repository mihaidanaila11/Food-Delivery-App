import Menu.Menu;
import Menu.MenuOption;
import Menu.Options.Admin.AdminPanel;
import Menu.Options.Auth.Login;
import Menu.Options.Auth.Logout;
import Menu.Options.Auth.Register;
import Menu.Options.Auth.ResumeRegister;
import Menu.Options.Exit;
import Menu.Options.Owner.CreateRestaurant;
import Menu.Options.Owner.OwnedRestaurants;
import Users.User;
import java.util.ArrayList;

import Auth.AppContext;

public class Main {

    public static void main(String[] args) {

        AppContext context;

        try{
            context = new AppContext();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }


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

                if(!loggedUser.isRegComplete()){
                    MenuOption completeRegistration = new ResumeRegister();
                    completeRegistration.action(context);
                }

                ArrayList<MenuOption> options = new ArrayList<>();
                options.add(new AdminPanel().addNeededRole(User.Roles.ADMIN));
                options.add(new OwnedRestaurants().addNeededRole(User.Roles.OWNER));
                options.add(new CreateRestaurant().addNeededRole(User.Roles.OWNER));
                options.add(new Logout());
                options.add(new Exit());
                Menu menu = new Menu(options);

                menu.setBanner("Welcome back, " + loggedUser.getFirstName() + "\n");
                menu.show(context);

            }
        }
    }
}