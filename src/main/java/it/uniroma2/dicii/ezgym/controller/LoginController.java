package it.uniroma2.dicii.ezgym.controller;

import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.UserDao;
import it.uniroma2.dicii.ezgym.dao.abstractFactory.DaoFactory;
import it.uniroma2.dicii.ezgym.domain.model.User;
import it.uniroma2.dicii.ezgym.exceptions.InvalidCredentialsException;
import it.uniroma2.dicii.ezgym.utils.PasswordUtils;
import it.uniroma2.dicii.ezgym.domain.model.Role;

public class LoginController {

    private final UserDao userDao;

    public LoginController() {
        this.userDao = DaoFactory.getInstance().createUserDao();
    }

    public User login(UserBean userBean) throws InvalidCredentialsException {
        User user = userDao.findByEmail(userBean.getEmail());

        if (user == null) {
            throw new InvalidCredentialsException("Email non trovata.");
        }

        if (!PasswordUtils.checkPassword(userBean.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Password errata.");
        }

        Role role = user.getRole();
        switch (role) {
            case ATHLETE -> System.out.println("Accesso come atleta");
            case PERSONAL_TRAINER -> System.out.println("Accesso come personal trainer");
            case ADMIN -> System.out.println("Accesso come admin");
        }

        return user;
    }
}
