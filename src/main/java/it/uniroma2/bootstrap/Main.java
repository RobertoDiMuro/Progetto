package it.uniroma2.bootstrap;

import java.io.IOException;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.UserDao;
import it.uniroma2.dicii.ezgym.dao.abstractFactory.DaoFactory;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.exceptions.EmailAlreadyExistsException;
import it.uniroma2.dicii.ezgym.utils.PasswordUtils;

public final class Main {


    public static void main(String[] args) throws IOException, EmailAlreadyExistsException {

        Mode mode = Setup.askMode();

        DaoFactory.init(mode);

        InterfaceMode interfaceMode = InterfaceSetup.askInterfaceMode();

        AppLauncher.launch(interfaceMode, mode);

        // String name  = "Mario";
        // String surname = "Rossi";
        // String email = "mario.rossi@gmail.com";
        // String password = "Password123!";

        // UUID id = UUID.randomUUID();
        // String hashedPassword = PasswordUtils.hashPassword(password);

        // DaoFactory factory = DaoFactory.getInstance();
        // UserDao userDao = factory.createUserDao();
        // PersonalTrainerDao personalTrainerDao = factory.createPersonalTrainerDao();

        // PersonalTrainer pt = new PersonalTrainer();
        
        // pt.setId(id);
        // pt.setName(name);
        // pt.setSurname(surname);
        // pt.setEmail(email);
        // pt.setPassword(hashedPassword);
        // pt.setRole(Role.PERSONAL_TRAINER);
        // pt.setActiveUsers(0);

        // userDao.insert(pt, id);
        // personalTrainerDao.insert(pt, id);


    }
}
