package it.uniroma2.bootstrap;

import java.io.IOException;

import it.uniroma2.dicii.ezgym.exceptions.EmailAlreadyExistsException;
import it.uniroma2.dicii.ezgym.view.climode.CliStart;



public class AppLauncher {


    private AppLauncher(){
        //
    }

    public static void launch(InterfaceMode interfaceMode, Mode mode) throws IOException, EmailAlreadyExistsException{

        if(interfaceMode == InterfaceMode.GUI)
            GuiLauncher.launchGui(mode);
        else{
            CliStart.start();
        }
    }
    

   
}
