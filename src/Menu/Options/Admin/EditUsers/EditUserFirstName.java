package Menu.Options.Admin.EditUsers;

import Auth.AppContext;
import Menu.MenuOption;

import java.util.Scanner;

public class EditUserFirstName extends MenuOption {
    public EditUserFirstName() {
        this.setLabel("Edit User First Name");
    }

    @Override
    public void action(AppContext ctx){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter First Name");
        String newLastName = scanner.nextLine();

        ctx.getAuth().changeFirstName(ctx.getEditingUser().getEmail(), newLastName);
    }
}
