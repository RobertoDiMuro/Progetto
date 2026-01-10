package it.uniroma2.bootstrap;

import java.io.IOException;

import it.uniroma2.dicii.ezgym.dao.abstractFactory.DaoFactory;
import it.uniroma2.dicii.ezgym.exceptions.EmailAlreadyExistsException;

public final class Main {


    public static void main(String[] args) throws IOException, EmailAlreadyExistsException {

        Mode mode = Setup.askMode();

        DaoFactory.init(mode);

        InterfaceMode interfaceMode = InterfaceSetup.askInterfaceMode();

        AppLauncher.launch(interfaceMode, mode);

    }
}
