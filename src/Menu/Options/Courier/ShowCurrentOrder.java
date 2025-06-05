package Menu.Options.Courier;

import Auth.AppContext;
import Menu.Menu;
import Menu.MenuOption;

import java.util.ArrayList;

public class ShowCurrentOrder extends MenuOption {
    public ShowCurrentOrder() {
        this.setLabel("Show Current Order");
    }

    @Override
    public void action(AppContext ctx) {
        if(ctx.getAuth().getLoggedCourier().getActiveOrder() == null) {
            System.out.println("You do not have an active order.");
            return;
        }

        ctx.getAuth().getLoggedCourier().getActiveOrder().show();

        ArrayList<MenuOption> options = new ArrayList<>();

        options.add(new FinishOrder());

        Menu menu = new Menu(options);
        menu.show(ctx, true);
    }
}
