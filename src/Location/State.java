package Location;

public class State {
    private int stateID;
    private String stateName;
    private Country country;

    public State(int stateID, String stateName, Country country) {
        this.stateID = stateID;
        this.stateName = stateName;
        this.country = country;
    }

    public State(String stateName, Country country) {
        this.stateName = stateName;
        this.country = country;
    }

    public int getStateID() { return stateID; }
    public String getStateName() { return stateName; }
    public Country getCountry() { return country; }

    public void setID(int stateID) {
        this.stateID = stateID;
    }
}
