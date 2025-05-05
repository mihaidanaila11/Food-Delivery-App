package Menu.Options;

import Auth.AppContext;
import Menu.MenuOption;

public class Exit extends MenuOption {
    private String label;

    public Exit() {
        this.setLabel("Exit");
    }

    @Override
    public void action(AppContext ctx) {
        System.exit(0);
    }
}
