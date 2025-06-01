package Location;

public class Country {
    private final String name;
    private final int CountryID;

    public Country(String name, int countryID) {
        this.name = name;
        CountryID = countryID;
    }


    public int getID() { return CountryID; }
}
