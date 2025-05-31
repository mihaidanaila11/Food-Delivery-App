package Menu.Options.Admin.EditUsers;

import Auth.AppContext;
import Menu.MenuOption;

import java.sql.SQLException;
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

        try{
            ctx.getAuth().changeFirstName(ctx.getEditingUser().getId(), newLastName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
