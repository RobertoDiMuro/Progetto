package it.uniroma2.bootstrap;

import java.io.BufferedReader;
import java.io.IOException;
import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.controller.LoginController;
import it.uniroma2.dicii.ezgym.exceptions.InvalidCredentialsException;

public class AppLauncher {

    private AppLauncher(){
        //
    }

    public static void launch(InterfaceMode interfaceMode, Mode mode) throws IOException{
        switch(interfaceMode){
            case GUI -> startGuiLogin(mode);
            case CLI -> startCliLogin(mode);
        }
    }

    private static void startCliLogin(Mode mode) throws IOException{
        System.out.println("\n===Login avviato in modalità CLI===");

        BufferedReader reader = InputReader.getInstance();

        System.out.print("\nInserisci email: ");
        String email = reader.readLine().trim();

        System.out.print("\nInserisci password: ");
        String password = reader.readLine().trim();

        LoginController cliLoginController = new LoginController();

        UserBean userBean = new UserBean();
        userBean.setEmail(email);
        userBean.setPassword(password);

        try{
            cliLoginController.login(userBean);
            System.out.println("\nLogin effettuato con successo!");
        }catch (InvalidCredentialsException | IllegalArgumentException e){
            System.err.println("\nErrore: " + e.getMessage());
        }
    }

    private static void startGuiLogin(Mode mode){
        GuiLauncher.launchGui(mode);
    }
}
