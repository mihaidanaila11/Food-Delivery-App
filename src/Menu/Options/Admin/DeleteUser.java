package Menu.Options.Admin;

import Auth.AppContext;
import Exceptions.AuthExceptions.UserDoesNotExist;
import Menu.MenuOption;
import Users.User;

import java.util.HashMap;
import java.util.Scanner;

public class DeleteUser extends MenuOption {
    public DeleteUser() {
        this.setLabel("Delete User");
    }

    @Override
    public void action(AppContext ctx) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter User ID to be deleted");
        String id = scanner.nextLine();

        try{
            ctx.getAuth().deleteUser(id);
        } catch (UserDoesNotExist e) {
            System.out.println(e.getMessage());
        }

        System.out.println("User " + id + " has been deleted");
    }
}
