package it.uniroma2.dicii.ezgym.controller;

import java.util.ArrayList;

import it.uniroma2.dicii.ezgym.bean.WorkoutSessionBean;
import it.uniroma2.dicii.ezgym.dao.abstractfactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.interfacedao.WorkoutSessionDao;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

public class CreateSessionController {

    private final WorkoutSessionDao dao;

    public CreateSessionController() {
        DaoFactory factory = DaoFactory.getInstance();
        this.dao = factory.createWorkoutSessionDao();
    }

    public void createEmptySession(WorkoutSessionBean bean) {
        try {
            if (bean == null) {
                throw new IllegalArgumentException("Bean nullo");
            }
            String dayOfWeek = bean.getDayOfWeek();
            if (dayOfWeek == null || dayOfWeek.trim().isEmpty()) {
                throw new IllegalArgumentException("Giorno della settimana non valido");
            }

            WorkoutSession session = new WorkoutSession(0, dayOfWeek, new ArrayList<>());

            int newId = dao.insert(session, session.getSessionId());

            if (newId <= 0) {
                throw new IllegalStateException("Sessione non creata correttamente");
            }

            bean.setSessionId(newId);

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("Errore durante la creazione della sessione");
        }
    }
}
