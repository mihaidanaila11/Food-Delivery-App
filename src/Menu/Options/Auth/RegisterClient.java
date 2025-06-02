package Menu.Options.Auth;

import Auth.AppContext;
import Exceptions.MenuExceptions.InvalidInput;
import Location.City;
import Location.Country;
import Location.Location;
import Menu.MenuOption;
import  Location.State;
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

    private ArrayList<Country> fetchCountries(AppContext ctx) throws SQLException {
        ArrayList<Country> countries = new ArrayList<>();


        ResultSet countriesResultSet = ctx.getDb().selectAll("Countries");

        while(countriesResultSet.next()) {
            countries.add(new Country(
                    countriesResultSet.getString("CountryName"),
                    countriesResultSet.getInt("CountryID")
            ));
        }

        return countries;
    }

    private ArrayList<State> fetchStates(AppContext ctx, Country country) throws SQLException {
        ArrayList<State> states = new ArrayList<>();

        ResultSet statesResultSet = ctx.getDb().selectAllWhere("States", "CountryID", country.getID());

        while(statesResultSet.next()) {
            states.add(new State(
                    statesResultSet.getInt("StateID"),
                    statesResultSet.getString("StateName"),
                    country
            ));
        }

        return states;
    }

    private ArrayList<City> fetchCities(AppContext ctx, State state) throws SQLException {
        ArrayList<City> cities = new ArrayList<>();

        ResultSet citiesResultSet = ctx.getDb().selectAllWhere("Cities", "StateID", state.getStateID());

        while(citiesResultSet.next()) {
            cities.add(new City(
                    citiesResultSet.getInt("CityID"),
                    citiesResultSet.getString("CityName"),
                    state
            ));
        }

        return cities;
    }

    @Override
    public void action(AppContext ctx) {


        System.out.println("What is your country?");
        Country selectedCountry;
        ArrayList<Country> countries;

        try{
            countries = fetchCountries(ctx);
        } catch (SQLException e) {
            System.out.println("Error fetching countries from the database.");
            return;
        }

        while (true) {
            try {
                selectedCountry = selectFromList(countries);
                break;
            } catch (InvalidInput e) {
                System.out.println(e.getMessage());
            }
        }

        State selectedState;
        ArrayList<State> states;

        try{
            states = fetchStates(ctx, selectedCountry);
        } catch (SQLException e) {
            System.out.println("Error fetching countries from the database.");
            return;
        }

        while (true){
            try {
                System.out.println("What is your state?");
                selectedState = selectFromList(states);
                break;
            } catch (InvalidInput e) {
                System.out.println(e.getMessage());
            }
        }

        City selectedCity;
        ArrayList<City> cities;

        try{
            cities = fetchCities(ctx, selectedState);
        } catch (SQLException e) {
            System.out.println("Error fetching countries from the database.");
            return;
        }

        while (true){
            try {
                System.out.println("What is your city?");
                selectedCity = selectFromList(cities);
                break;
            } catch (InvalidInput e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Enter your postal code:");
        String postalCode = Menu.Menu.getStringInput();

        System.out.println("Enter your street number:");
        int streetNumber = Menu.Menu.getIntInput();

        Location location  = new Location(
                selectedCity,
                selectedState.getStateName(),
                postalCode,
                streetNumber
        );

        try{
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
