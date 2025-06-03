package Menu;

import Auth.AppContext;
import Exceptions.MenuExceptions.InvalidInput;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private final ArrayList<MenuOption> options;
    private String banner;
    private static final ArrayList<String> invalidInputs = new ArrayList<>();

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



        if(!ctx.getAuth().isLoggedUser()){
            // Daca nu e logat si a ajuns in meniul asta inseamna ca nu are nevoie
            // de permisiuni
            filteredOptions = options;
        }
        else{
            for(MenuOption option : options){
                if(option.hasAccess(ctx.getAuth().getLoggedUser().getRoles())){
                    filteredOptions.add(option);
                }
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

    public static <T> T selectFromList(ArrayList<T> options){
        Scanner scanner = new Scanner(System.in);


        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        int countryIndex = -1;
        try {
            countryIndex = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidInput();
        }

        if (countryIndex < 0 || countryIndex >= options.size()) {
            throw new InvalidInput();
        }

        return options.get(countryIndex);
    }

    public static String getStringInput(){
        Scanner scanner = new Scanner(System.in);

        String input =  scanner.nextLine();

        if(input == null || input.isEmpty()){
            throw new InvalidInput();
        }

        return input;
    }

    public static int getIntInput(){
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        if(input == null || input.isEmpty()){
            throw new InvalidInput();
        }

        try{
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            throw new InvalidInput();
        }
    }
}
