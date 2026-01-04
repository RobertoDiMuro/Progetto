package it.uniroma2.dicii.ezgym.controller;

import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.AthleteDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.UserDao;
import it.uniroma2.dicii.ezgym.dao.abstractFactory.DaoFactory;
import it.uniroma2.dicii.ezgym.domain.model.User;
import it.uniroma2.dicii.ezgym.exceptions.InvalidCredentialsException;
import it.uniroma2.dicii.ezgym.utils.PasswordUtils;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;

public class LoginController {

    private final UserDao userDao;
    private final AthleteDao athleteDao;
    private final PersonalTrainerDao personalTrainerDao;

    public LoginController() {
        DaoFactory factory = DaoFactory.getInstance();
        this.userDao = factory.createUserDao();
        this.athleteDao = factory.createAthleteDao();
        this.personalTrainerDao = factory.createPersonalTrainerDao();
    }

    public User login(UserBean userBean) throws InvalidCredentialsException {
        User user = userDao.findByEmail(userBean.getEmail());

        if (user == null) {
            throw new InvalidCredentialsException("Email non trovata.");
        }

        if (!PasswordUtils.checkPassword(userBean.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Password errata.");
        }

        switch(user.getRole()){

            case ATHLETE:
                Athlete athleteFromDb = athleteDao.findBy(user.getEmail());

                if (athleteFromDb != null) {
                        athleteFromDb.setId(user.getId());
                        athleteFromDb.setName(user.getName());
                        athleteFromDb.setSurname(user.getSurname());
                        athleteFromDb.setEmail(user.getEmail());
                        athleteFromDb.setPassword(user.getPassword());
                        athleteFromDb.setRole(user.getRole());

                        return athleteFromDb;
                    }
                    return user;
            case PERSONAL_TRAINER:
                PersonalTrainer personalTrainerFromDb = personalTrainerDao.findBy(user.getEmail());

                if(personalTrainerFromDb != null){
                    personalTrainerFromDb.setId(user.getId());
                    personalTrainerFromDb.setName(user.getName());
                    personalTrainerFromDb.setSurname(user.getSurname());
                    personalTrainerFromDb.setEmail(user.getEmail());
                    personalTrainerFromDb.setPassword(user.getPassword());
                    personalTrainerFromDb.setRole(user.getRole());

                    return personalTrainerFromDb;
                }
                return user;
            default:
                return user;
        }
    }
}
