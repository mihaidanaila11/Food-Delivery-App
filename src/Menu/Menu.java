package Menu;

import Auth.AppContext;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ArrayList<MenuOption> options;
    private String banner;

    public Menu(ArrayList<MenuOption> options) {
        this.options = options;
    }

    public String getBanner() { return banner; }
    public void setBanner(String banner) { this.banner = banner; }

    public void show(AppContext ctx){
        if(banner != null && !banner.isEmpty()){
            System.out.println(banner);
        }

        ArrayList<MenuOption> filteredOptions = new ArrayList<MenuOption>();

        for(MenuOption option : options){
            if(!ctx.getAuth().isLoggedUser()){
                // Daca nu e logat si a ajuns in meniul asta inseamna ca nu are nevoie
                // de permisiuni
                filteredOptions = options;
                break;
            }

            if(option.hasAccess(ctx.getAuth().getLoggedUser().getRoles())){
                filteredOptions.add(option);
                continue;
            }

        }

        for (int i=0; i<filteredOptions.size(); i++) {
            System.out.println(i+1 + ": " + filteredOptions.get(i).getLabel());
        }

        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();

        // De tratat exceptii - nu e numar, nu exista optiunea
        filteredOptions.get(Integer.parseInt(option) - 1).action(ctx);
    }
}
