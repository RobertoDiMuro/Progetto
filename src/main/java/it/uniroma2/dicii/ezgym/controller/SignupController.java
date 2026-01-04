package it.uniroma2.dicii.ezgym.controller;

import java.util.UUID;

import it.uniroma2.dicii.ezgym.bean.SignupBean;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.AthleteDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.UserDao;
import it.uniroma2.dicii.ezgym.dao.abstractFactory.DaoFactory;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.domain.model.User;
import it.uniroma2.dicii.ezgym.exceptions.EmailAlreadyExistsException;
import it.uniroma2.dicii.ezgym.utils.PasswordUtils;

public class SignupController {
    
    private final AthleteDao athleteDao;
    private final UserDao userDao;

    public SignupController(){
        DaoFactory factory = DaoFactory.getInstance();
        this.athleteDao =factory.createAthleteDao();
        this.userDao = factory.createUserDao();
    }

    public Athlete signup(SignupBean bean) throws EmailAlreadyExistsException{

        User exist = userDao.findByEmail(bean.getEmail());
        if(exist != null){
            throw new EmailAlreadyExistsException("L'email inserita esiste già.");
        }

        UUID id = UUID.randomUUID();
        String hashedPassword = PasswordUtils.hashPassword(bean.getPassword());

        Athlete athlete = new Athlete(
            null,
            0,
            id,
            bean.getName(),
            bean.getSurname(),
            bean.getEmail(),
            hashedPassword,
            Role.ATHLETE,
            0,
            0,
            null,
            null,
            null,
            false
        );

        userDao.insert(athlete, id);
        athleteDao.insert(athlete, id);
        return athlete;
    }
}
