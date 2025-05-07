package Menu.Options.Admin;

import Auth.AppContext;
import Menu.Menu;
import Menu.MenuOption;
import Menu.Options.Admin.EditUsers.EditUser;
import Menu.Options.Exit;
import Users.User;

import java.util.ArrayList;

public class AdminPanel extends MenuOption {
    public AdminPanel() {
        this.setLabel("Admin Panel");
    }

    @Override
    public void action(AppContext ctx) {
        ArrayList<MenuOption> options = new ArrayList<MenuOption>();

        options.add(new ShowAllUsers().addNeededRole(User.Roles.ADMIN));
        options.add(new ShowAllUsersAlphabetically().addNeededRole(User.Roles.ADMIN));
        options.add(new EditUser().addNeededRole(User.Roles.ADMIN));
        options.add(new DeleteUser().addNeededRole(User.Roles.ADMIN));
        options.add(new Exit());

        Menu AdminMenu = new Menu(options);
        AdminMenu.show(ctx);
    }
}
