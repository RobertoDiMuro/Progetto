package it.uniroma2.dicii.ezgym.controller;

import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.dao.abstractfactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.interfacedao.AthleteDao;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

public class WorkoutRequestController {

    private final AthleteDao athleteDao;

    public WorkoutRequestController(){
        this.athleteDao = DaoFactory.getInstance().createAthleteDao();
    }

    public void setCurrAthlete(AthleteBean bean){
        if(bean == null){
            throw new IllegalArgumentException("AthleteBean cannot be null");
        }

        String email = bean.getEmail();
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Email non valida.");
        }

        Integer age = bean.getAge();
        if(age == null || age < 1 || age > 120){
            throw new IllegalArgumentException("Età non valida. Inserisci un valore tra 1 e 120.");
        }

        Double weight = bean.getWeight();
        if(weight == null || weight < 20 || weight > 500){
            throw new IllegalArgumentException("Peso non valido. Inserisci un valore tra 20 e 500 kg.");
        }

        Double height = bean.getHeight();
        if(height == null || height < 50 || height > 300){
            throw new IllegalArgumentException("Altezza non valida. Inserisci un valore tra 50 e 300 cm.");
        }

        String gender = normalizeGender(bean.getGender());

        if(bean.getTarget() == null){
            throw new IllegalArgumentException("Target non valido.");
        }
        if(bean.getWorkoutDay() == null){
            throw new IllegalArgumentException("Giorni di allenamento non validi.");
        }
        if(bean.getActivityLevel() == null){
            throw new IllegalArgumentException("Activity level non valido.");
        }

        Athlete athlete;
        try{
            athlete = athleteDao.findBy(email);
        }catch(Exception e){
            e.printStackTrace();
            throw new PersistenceException("Errore durante il recupero dell'atleta.");
        }

        if(athlete == null){
            throw new PersistenceException("Atleta non trovato.");
        }

        try{
            athlete.setAge(age);
            athlete.setWeight(weight);
            athlete.setHeight(height);
            athlete.setGender(gender);
            athlete.setIsWorkoutRequested(true);

            athlete.setActivityLevel(bean.getActivityLevel());
            athlete.setTarget(bean.getTarget());
            athlete.setWorkoutDay(bean.getWorkoutDay());

            athleteDao.update(athlete.getId(), athlete);
        }catch(Exception e){
            e.printStackTrace();
            throw new PersistenceException("Errore durante il salvataggio della richiesta di scheda");
        }
    }

    private static String normalizeGender(String gender){
        if(gender == null || gender.isBlank()){
            throw new IllegalArgumentException("Genere non valido. Inserisci Maschio o Femmina.");
        }
        String g = gender.trim();
        if(g.equalsIgnoreCase("Maschio") || g.equalsIgnoreCase("M")){
            return "Maschio";
        }
        if(g.equalsIgnoreCase("Femmina") || g.equalsIgnoreCase("F")){
            return "Femmina";
        }
        throw new IllegalArgumentException("Genere non valido. Inserisci Maschio o Femmina.");
    }
}
