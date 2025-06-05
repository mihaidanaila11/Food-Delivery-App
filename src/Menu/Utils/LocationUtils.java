package Menu.Utils;

import Auth.AppContext;
import Exceptions.MenuExceptions.CancelInput;
import Exceptions.MenuExceptions.InvalidInput;
import Location.City;
import Location.Country;
import Location.Location;
import Location.State;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Menu.Menu.selectFromList;

public class LocationUtils {

    private static ArrayList<Country> fetchCountries(AppContext ctx) throws SQLException {
        ArrayList<Country> countries = new ArrayList<>();


        ResultSet countriesResultSet = ctx.getDb().selectAllOrderedBy("Countries", "CountryName");

        while(countriesResultSet.next()) {
            countries.add(new Country(
                    countriesResultSet.getString("CountryName"),
                    countriesResultSet.getInt("CountryID")
            ));
        }

        return countries;
    }

    private static ArrayList<State> fetchStates(AppContext ctx, Country country) throws SQLException {
        ArrayList<State> states = new ArrayList<>();

        ResultSet statesResultSet = ctx.getDb().selectAllWhereOrdered("States", "StateName", "CountryID", country.getID());

        while(statesResultSet.next()) {
            states.add(new State(
                    statesResultSet.getInt("StateID"),
                    statesResultSet.getString("StateName"),
                    country
            ));
        }

        return states;
    }

    private static ArrayList<City> fetchCities(AppContext ctx, State state) throws SQLException {
        ArrayList<City> cities = new ArrayList<>();

        ResultSet citiesResultSet = ctx.getDb().selectAllWhereOrdered("Cities", "CityName", "StateID", state.getStateID());

        while(citiesResultSet.next()) {
            cities.add(new City(
                    citiesResultSet.getInt("CityID"),
                    citiesResultSet.getString("CityName"),
                    state
            ));
        }

        return cities;
    }

    private static Country askForCountry(AppContext ctx) throws SQLException {
        Country selectedCountry;
        ArrayList<Country> countries;

        countries = fetchCountries(ctx);

        while (true) {
            try {
                selectedCountry = selectFromList(countries);
                break;
            } catch (InvalidInput e) {
                System.out.println(e.getMessage());
            }
        }

        return selectedCountry;
    }

    private static State askForState(AppContext ctx, Country country) throws SQLException {
        State selectedState;
        ArrayList<State> states;

        states = fetchStates(ctx, country);

        while (true) {
            try {
                selectedState = selectFromList(states);
                break;
            } catch (InvalidInput e) {
                System.out.println(e.getMessage());
            }
        }

        return selectedState;
    }

    private static City askForCity(AppContext ctx, State state) throws SQLException {
        City selectedCity;
        ArrayList<City> cities;

        cities = fetchCities(ctx, state);

        while (true) {
            try {
                selectedCity = selectFromList(cities);
                break;
            } catch (InvalidInput e) {
                System.out.println(e.getMessage());
            }
        }

        return selectedCity;
    }

    public static City askForCity(AppContext ctx) throws SQLException {
        System.out.println("What is your country?");
        Country selectedCountry = askForCountry(ctx);

        System.out.println("What is your state?");
        State selectedState = askForState(ctx, selectedCountry);

        System.out.println("What is your city?");
        return askForCity(ctx, selectedState);
    }

    public static Location askForLocation(AppContext ctx) throws SQLException {
        System.out.println("What is your country?");
        Country selectedCountry = askForCountry(ctx);

        System.out.println("What is your state?");
        State selectedState = askForState(ctx, selectedCountry);

        System.out.println("What is your city?");
        City selectedCity = askForCity(ctx, selectedState);

        System.out.println("Enter your postal code:");
        String postalCode = Menu.Menu.getStringInput();

        System.out.println("Enter your street number:");
        int streetNumber = Menu.Menu.getIntInput();

        return new Location(
                selectedCity,
                selectedState.getStateName(),
                postalCode,
                streetNumber
        );
    }
}
