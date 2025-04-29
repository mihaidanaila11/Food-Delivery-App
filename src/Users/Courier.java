package Users;

import Auth.PasswordHash;

import java.util.Vector;

public class Courier extends User {
    private String companyName;
    private String licensePlate;

    public enum Vehicle{
        CAR, BIKE, SCOOTER
    }

    private Vehicle vehicle;

    public Courier(String firstName, String lastName, String email, PasswordHash passwordHash,
                   String companyName, String licensePlate, Vehicle vehicle) {
        super(firstName, lastName, email, passwordHash);
        this.companyName = companyName;
        this.licensePlate = licensePlate;
        this.vehicle = vehicle;
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
}
