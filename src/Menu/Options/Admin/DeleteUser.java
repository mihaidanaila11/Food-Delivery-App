package Menu.Options.Admin;

import Auth.AppContext;
import Exceptions.AuthExceptions.UserDoesNotExist;
import Menu.MenuOption;

import java.sql.SQLException;
import java.util.Scanner;

public class DeleteUser extends MenuOption {
    public DeleteUser() {
        this.setLabel("Delete User");
    }

    @Override
    public void action(AppContext ctx) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter User email to be deleted");
        String email = scanner.nextLine();

        try{
            ctx.getAuth().deleteUser(email);
        } catch (UserDoesNotExist | SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("User " + " has been deleted");
    }
}
