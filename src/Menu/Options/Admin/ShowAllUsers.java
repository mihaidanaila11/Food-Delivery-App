package Menu.Options.Admin;

import Auth.AppContext;
import Menu.MenuOption;
import Users.User;

import java.util.HashMap;

public class ShowAllUsers extends MenuOption {
    public ShowAllUsers() {
        this.setLabel("Show All Users");
    }

    @Override
    public void action(AppContext ctx) {
        HashMap<String, User> users = ctx.getAuth().getUsers();

        int userCount = 0;
        System.out.println("ID\t\tFirst Name\t\tLast Name\t\t Email Address\t\t Roles");
        for(User u : users.values()) {
            System.out.println(u.getId() + "\t\t" + u.getFirstName() + "\t\t" + u.getLastName() + "\t\t" + u.getEmail() + "\t\t" + u.getRoles());
        }
    }
}
