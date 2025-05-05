package Menu.Options.Auth;

import Auth.AppContext;
import Menu.MenuOption;

import java.util.Scanner;

public class Login extends MenuOption {

    public Login() {
        this.setLabel("Login");
    }

    @Override
    public void action(AppContext ctx) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your email");
        String email = scanner.nextLine();
        System.out.println("Enter your password");
        String password = scanner.nextLine();
        try{
            ctx.getAuth().loginClient(email, password);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
