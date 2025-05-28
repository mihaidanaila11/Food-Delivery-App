package Location;

public class Location {
    private String Street;
    private String City;
    private String State;
    private String PostalCode;
    private String Country;
    private int Number;

    public Location(String street, String city, String state, String postalCode, String country, int number) {
        Street = street;
        City = city;
        State = state;
        PostalCode = postalCode;
        Country = country;
        Number = number;
    }

    public String getStreet() {
        return Street;
    }

    public String getCity() {
        return City;
    }

    public String getState() {
        return State;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public String getCountry() {
        return Country;
    }

    public int getNumber() {
        return Number;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setState(String state) {
        State = state;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setNumber(int number) {
        Number = number;
    }
}
