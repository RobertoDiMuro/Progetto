package it.uniroma2.dicii.ezgym.controller;

import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.dao.abstractfactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.interfacedao.UserDao;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.domain.model.User;
import it.uniroma2.dicii.ezgym.exceptions.InvalidCredentialsException;
import it.uniroma2.dicii.ezgym.utils.PasswordUtils;

public class LoginController {

    private final UserDao userDao;

    public LoginController() {
        DaoFactory factory = DaoFactory.getInstance();
        this.userDao = factory.createUserDao();
    }

    public UserBean login(UserBean userBean) throws InvalidCredentialsException {
        if (userBean == null) {
            throw new InvalidCredentialsException("Credenziali non valide.");
        }

        String email = normalizeEmail(userBean.getEmail());
        String password = userBean.getPassword();

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidCredentialsException("Email e password sono obbligatori.");
        }

        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new InvalidCredentialsException("Email non trovata.");
        }

        boolean ok;
        try {
            ok = PasswordUtils.checkPassword(password, user.getPassword());
        } catch (IllegalArgumentException _) {
            throw new InvalidCredentialsException("Password errata.");
        }

        if (!ok) {
            throw new InvalidCredentialsException("Password errata.");
        }

        Role role = user.getRole();
        if (role == null) {
            throw new InvalidCredentialsException("Ruolo utente non valido.");
        }

        UserBean logged = new UserBean();
        logged.setId(user.getId());
        logged.setName(user.getName());
        logged.setSurname(user.getSurname());
        logged.setEmail(email);
        logged.setRole(role);

        return logged;
    }

    private String normalizeEmail(String email) {
        if (email == null) return null;
        return email.trim().toLowerCase();
    }
}
