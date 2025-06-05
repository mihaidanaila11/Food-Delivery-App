package Menu.Options.Auth;

import Auth.AppContext;
import Location.City;
import Menu.MenuOption;
import Menu.Utils.LocationUtils;
import Users.Client;
import Users.Courier;

import java.sql.SQLException;

public class RegisterCourier extends MenuOption {
    public RegisterCourier() {
        this.setLabel("Register Courier");
    }
    @Override
    public void action(AppContext ctx) {
        City workingCity;
        try{
            workingCity = LocationUtils.askForCity(ctx);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Enter your company name: ");
        String companyName = Menu.Menu.getStringInput();

        System.out.println("Enter your license plate: ");
        String licensePlate = Menu.Menu.getStringInput();

        System.out.println("Enter your vehicle type (CAR, BIKE, SCOOTER): ");
        String vehicleType = Menu.Menu.getStringInput().toUpperCase();
        while(!vehicleType.equals("CAR") &&
              !vehicleType.equals("BIKE") &&
              !vehicleType.equals("SCOOTER")) {
            System.out.println("Invalid vehicle type. Please enter CAR, BIKE, or SCOOTER: ");
            vehicleType = Menu.Menu.getStringInput().toUpperCase();
        }


        try{

            Courier courier = new Courier(
                    ctx.getAuth().getLoggedUser(),
                    companyName,
                    licensePlate,
                    Courier.Vehicle.valueOf(vehicleType),
                    workingCity
            );
            ctx.getAuth().completeCourierRegistration(courier);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
