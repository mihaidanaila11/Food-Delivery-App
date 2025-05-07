package Menu.Options.Admin.EditUsers;

import Auth.AppContext;
import Menu.MenuOption;

import java.util.Scanner;

public class EditUserLastName extends MenuOption {
    public EditUserLastName(){
        this.setLabel("Edit User Name");
    }

    @Override
    public void action(AppContext ctx){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter new name");
        String newLastName = scanner.nextLine();

        ctx.getAuth().changeLastName(ctx.getEditingUser().getEmail(), newLastName);
    }
}
