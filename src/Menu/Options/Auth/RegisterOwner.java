package Menu.Options.Auth;

import Auth.AppContext;
import Menu.MenuOption;
import Users.Owner;

import java.sql.SQLException;

public class RegisterOwner extends MenuOption {
    public RegisterOwner() {
        this.setLabel("Owner");
    }

    @Override
    public void action(AppContext ctx) {
        Owner newOwner = new Owner(ctx.getAuth().getLoggedUser());
        try{
            ctx.getAuth().completeOwnerRegistration(newOwner);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
