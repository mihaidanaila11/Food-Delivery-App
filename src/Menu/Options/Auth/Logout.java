package Menu.Options.Auth;

import Auth.AppContext;
import Menu.MenuOption;

public class Logout extends MenuOption {

    public Logout() {
        this.setLabel("Logout");
    }

    @Override
    public void action(AppContext ctx) {
        ctx.getAuth().logout();
    }
}
