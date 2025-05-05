package Menu;

import Auth.AppContext;
import Users.User;

import java.util.HashMap;
import java.util.HashSet;

public abstract class MenuOption {
    private String label;
    HashMap<User.Roles, Boolean> neededRoles;

    public MenuOption(){
        this.neededRoles = new HashMap<>();
    }

    public String getLabel() { return label; }

    public void setLabel(String label) { this.label = label; }

    public abstract void action(AppContext ctx);

    public MenuOption addNeededRole(User.Roles role) {
        this.neededRoles.put(role, true);
        return this;
    }
    public void removeNeededRole(User.Roles role) { this.neededRoles.remove(role); }
    public HashMap<User.Roles, Boolean> getNeededRoles() { return neededRoles; }
    public boolean hasAccess(User.Roles role) {
        if(neededRoles.isEmpty())
            return true;

        return this.neededRoles.containsKey(role);
    }
    public boolean hasAccess(HashSet<User.Roles> roles) {
        for (User.Roles role : roles) {
            if (this.hasAccess(role)) { return true; }
        }

        return false;
    }
}
