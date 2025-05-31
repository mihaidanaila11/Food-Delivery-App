package Menu.Options.Admin.EditUsers;

import Auth.AppContext;
import Exceptions.AuthExceptions.UserDoesNotExist;
import Menu.Menu;
import Menu.MenuOption;
import Users.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class EditUser extends MenuOption {
    public EditUser() {
        this.setLabel("Edit User");
    }

    @Override
    public void action(AppContext ctx){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter User email");
        String email = scanner.nextLine();

        try{
            ctx.setEditingUser(email);
        }
        catch (UserDoesNotExist | SQLException e){
            System.out.println(e.getMessage());
        }

        ArrayList<MenuOption> options = new ArrayList<>();
        options.add(new EditUserFirstName().addNeededRole(User.Roles.ADMIN));
        options.add(new EditUserLastName().addNeededRole(User.Roles.ADMIN));
        Menu menu = new Menu(options);

        menu.show(ctx);
    }
}
