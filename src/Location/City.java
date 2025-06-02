package Location;

public class City {
    private int cityID;
    private String cityName;
    private State state;

    public City(String cityName, State state) {
        this.cityName = cityName;
        this.state = state;
    }

    public City(int cityID, String cityName, State state) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.state = state;
    }

    public int getStateId() { return state.getStateID(); }

    public String getName() { return cityName; }

    public int getID() { return cityID;  }

    @Override
    public String toString() {
        return cityName;
    }
}
