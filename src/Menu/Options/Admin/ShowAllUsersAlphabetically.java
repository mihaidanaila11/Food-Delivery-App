package Menu.Options.Admin;

import Auth.AppContext;
import Menu.MenuOption;
import Users.User;

import java.util.ArrayList;
import java.util.Comparator;

public class ShowAllUsersAlphabetically extends MenuOption {
    public ShowAllUsersAlphabetically() {
        this.setLabel("Show All Users Alphabetically");
    }

    @Override
    public void action(AppContext ctx) {
        ArrayList<User> users = new ArrayList<>(ctx.getAuth().getUsers().values());

        users.sort(Comparator.comparing(User::getFirstName));

        System.out.println("ID\t\tFirst Name\t\tLast Name\t\t Email Address\t\t Roles");
        for (User u : users) {
            System.out.println(u.getId() + "\t\t" + u.getFirstName() + "\t\t" + u.getLastName() + "\t\t" + u.getEmail() + "\t\t" + u.getRoles());
        }
    }
}