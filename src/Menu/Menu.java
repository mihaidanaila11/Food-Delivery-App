package Menu;

import Auth.AppContext;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ArrayList<MenuOption> options;
    private String banner;
    private static final ArrayList<String> invalidInputs = new ArrayList<String>();

    public Menu(ArrayList<MenuOption> options) {

        this.options = options;
        invalidInputs.add("");
    }

    public String getBanner() { return banner; }
    public void setBanner(String banner) { this.banner = banner; }

    private boolean isInputValid(String input){
        if(input == null || input.isEmpty()){
            return false;
        }

        for(String invalidInput : invalidInputs){
            if(input.equals(invalidInput)){
                return false;
            }
        }

        try{
            Integer.parseInt(input);
        } catch (NumberFormatException e){
            return false;
        }

        return true;
    }

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
            }

        }

        for (int i=0; i<filteredOptions.size(); i++) {
            System.out.println(i+1 + ": " + filteredOptions.get(i).getLabel());
        }

        boolean validInput = false;
        String option = null;

        while(!validInput){
            Scanner scanner = new Scanner(System.in);
            option = scanner.nextLine().strip();

            if(isInputValid(option)){
                validInput = true;
            }
            else{
                System.out.println("Invalid input. Please try again.");
            }

            // De tratat exceptii - nu e numar, nu exista optiunea

        }
        filteredOptions.get(Integer.parseInt(option) - 1).action(ctx);

    }
}
