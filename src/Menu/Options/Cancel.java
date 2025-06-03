package Menu.Options;

import Auth.AppContext;
import Menu.MenuOption;

public class Cancel extends MenuOption {
    public Cancel() {
        this.setLabel("Cancel");
    }

    @Override
    public void action(AppContext ctx) {
        // Nu face nimic, doar anuleaza actiunea curenta
    }
}
