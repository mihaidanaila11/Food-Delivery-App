package database;

import Location.City;
import Location.Country;
import Location.State;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PopulateLocations {
    private static class Oras {
        public double x;
        public double y;
        public String nume;
        public String judet;
        public String judetAuto;
        public int populatie;
        public String regiune;

        @Override
        public String toString() {
            return nume + " (" + judet + ")";
        }
    }

    public void populate(DatabaseHandler db) throws IOException, SQLException {
        ObjectMapper mapper = new ObjectMapper();

        File file = new File("src/database/orase-dupa-judet.json");

        Map<String, List<Oras>> judeteCuOrase = mapper.readValue(
                file,
                new TypeReference<>() {
                }
        );

        Country country = new Country("Romania", 1);

        for(String judet : judeteCuOrase.keySet()) {

            State state = new State(
                    judet,
                    country
            );

            db.insertState(state);

            ResultSet fetchedID = db.selectColumnWhere("States",
                    "StateID",
                    "StateName",
                    state.getStateName());
            if(!fetchedID.next()) {
                System.out.println("State " + state.getStateName() + " not found in database after insertion.");
                continue;
            }

            state.setID(fetchedID.getInt("StateID"));

            List<Oras> orase = judeteCuOrase.get(judet);
            for(Oras oras : orase) {
                db.insertCity(new City(oras.nume, state));
            }
        }
    }


}
