package Users;

import Auth.PasswordHash;
import Location.City;
import Orders.Order;

public class Courier extends User {
    private String companyName;
    private String licensePlate;

    public enum Vehicle{
        CAR, BIKE, SCOOTER
    }

    private Vehicle vehicle;
    private City workingCity;

    private Order activeOrder;

    public Courier(User user,
                   String companyName, String licensePlate, Vehicle vehicle, City workingCity) {
        super(user);
        this.companyName = companyName;
        this.licensePlate = licensePlate;
        this.vehicle = vehicle;
        this.workingCity = workingCity;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public City getWorkingCity() {
        return workingCity;
    }

    public void setActiveOrder(Order activeOrder) {
        this.activeOrder = activeOrder;
    }

    public Order getActiveOrder() {
        return activeOrder;
    }
}
