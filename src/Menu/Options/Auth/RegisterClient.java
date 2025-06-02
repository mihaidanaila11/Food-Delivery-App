package Menu.Options.Auth;

import Auth.AppContext;
import Exceptions.MenuExceptions.InvalidInput;
import Location.City;
import Location.Country;
import Location.Location;
import Menu.MenuOption;
import  Location.State;
import Menu.Utils.LocationUtils;
import Users.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static Menu.Menu.selectFromList;

public class RegisterClient extends MenuOption {

    public RegisterClient() {
        this.setLabel("Client");
    }



    @Override
    public void action(AppContext ctx) {
        try{
            Location location = LocationUtils.askForLocation(ctx);

            Client client = new Client(
                    ctx.getAuth().getLoggedUser(),
                    location
            );
            ctx.getAuth().completeClientRegistration(client);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
