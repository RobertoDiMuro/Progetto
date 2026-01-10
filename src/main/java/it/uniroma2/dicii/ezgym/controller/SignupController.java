package it.uniroma2.dicii.ezgym.controller;

import java.util.UUID;

import it.uniroma2.dicii.ezgym.bean.SignupBean;
import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.dao.abstractFactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.interfaceDao.AthleteDao;
import it.uniroma2.dicii.ezgym.dao.interfaceDao.UserDao;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.domain.model.User;
import it.uniroma2.dicii.ezgym.exceptions.EmailAlreadyExistsException;
import it.uniroma2.dicii.ezgym.utils.PasswordUtils;

public class SignupController {

    private final AthleteDao athleteDao;
    private final UserDao userDao;

    public SignupController() {
        DaoFactory factory = DaoFactory.getInstance();
        this.athleteDao = factory.createAthleteDao();
        this.userDao = factory.createUserDao();
    }

    public UserBean signup(SignupBean bean) throws EmailAlreadyExistsException {

        String email = bean.getEmail().trim().toLowerCase();

        User exist = userDao.findByEmail(email);
        if (exist != null) {
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
                email,
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

        UserBean created = new UserBean();
        created.setId(athlete.getId());
        created.setName(athlete.getName());
        created.setSurname(athlete.getSurname());
        created.setEmail(athlete.getEmail());
        created.setRole(Role.ATHLETE);

        return created;
    }
}
