package Menu.Options.Auth;

import Auth.AppContext;
import Menu.Menu;
import Menu.MenuOption;

import java.util.ArrayList;

public class ResumeRegister extends MenuOption {
    public ResumeRegister() {
        this.setLabel("Resume Registration");
    }

    @Override
    public void action(AppContext ctx) {
        ArrayList<MenuOption> options = new ArrayList<>();
        System.out.println("What are you registering as?");

        options.add(new RegisterClient());
        options.add(new RegisterOwner());
        options.add(new RegisterCourier());

        Menu menu = new Menu(options);
        menu.show(ctx);
    }
}
