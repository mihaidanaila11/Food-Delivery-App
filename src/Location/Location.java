package Location;

public class Location {
    private final Integer locationID = null;
    private String street;
    private City city;
    private String postalCode;
    private int locationNumber;

    public Location(City city, String street, String postalCode, int number) {
        this.street = street;
        this.postalCode = postalCode;
        locationNumber = number;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }


    public int getLocationNumber() {
        return locationNumber;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setLocationNumber(int locationNumber) {
        this.locationNumber = locationNumber;
    }

    public City getCity() { return city; }
}
