package it.uniroma2.bootstrap;

import java.io.IOException;

import it.uniroma2.dicii.ezgym.dao.abstractFactory.DaoFactory;

public final class Main {

    public static void main(String[] args) throws IOException {

        Mode mode = Setup.askMode();

        DaoFactory.init(mode);

        InterfaceMode interfaceMode = InterfaceSetup.askInterfaceMode();

        AppLauncher.launch(interfaceMode, mode);

    }
}
