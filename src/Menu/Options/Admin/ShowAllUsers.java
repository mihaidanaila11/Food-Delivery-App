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
        System.out.println("First Name\tLast Name\t Email Address");
        for(User u : users.values()) {
            System.out.println(u.getFirstName() + "\t" + u.getLastName() + "\t" + u.getEmail());
        }
    }
}
