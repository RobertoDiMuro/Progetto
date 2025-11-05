package it.uniroma2.dicii.ezgym.domain.bean;

import java.util.ArrayList;
import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.Target;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutStatus;

public class WorkoutBean {
    
    private  String requestByAthleteName;
    private  String requestByAthleteSurname;
    private  String createdByTrainerName;
    private  String createdByTrainerSurname;

    private Target target;
    private String notes;
    private List<WorkoutBean> sessions;
    private WorkoutStatus status;

    public WorkoutBean(){
        this.sessions = new ArrayList<>();
        this.status = WorkoutStatus.ACTIVE;
    }

    public String getRequestByAthleteName(){
        return requestByAthleteName;
    }

    public void setRequestByAthleteName(String requestByAthleteName) {
        if (requestByAthleteName == null || requestByAthleteName.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome dell'atleta non può essere vuoto.");
        }
        this.requestByAthleteName = requestByAthleteName.trim();
    }

    public String getRequestByAthleteSurname(){
        return requestByAthleteSurname;
    }

    public void setRequestByAthleteSurname(String requestByAthleteSurname) {
        if (requestByAthleteSurname == null || requestByAthleteSurname.trim().isEmpty()) {
            throw new IllegalArgumentException("Il cognome dell'atleta non può essere vuoto.");
        }
        this.requestByAthleteSurname = requestByAthleteSurname.trim();
    }

    public String getCreatedByTrainerName(){
        return createdByTrainerName;
    }

    public void setCreatedByTrainerName(String createdByTrainerName) {
        if (createdByTrainerName == null || createdByTrainerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome del trainer non può essere vuoto.");
        }
        this.createdByTrainerName = createdByTrainerName.trim();
    }

    public String getCreatedByTrainerSurname(){
        return createdByTrainerSurname;
    }

    public void setCreatedByTrainerSurname(String createdByTrainerSurname) {
        if (createdByTrainerSurname == null || createdByTrainerSurname.trim().isEmpty()) {
            throw new IllegalArgumentException("Il cognome del trainer non può essere vuoto.");
        }
        this.createdByTrainerSurname = createdByTrainerSurname.trim();
    }

    public Target getTarget(){
        return target;
    }

    public void setTarget(Target target) {
        if (target == null) {
            throw new IllegalArgumentException("Il target non può essere nullo.");
        }
        this.target = target;
    }

    public String getNotes(){
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "Nessuna nota aggiuntiva";
    }
    public List<WorkoutBean> getsSession(){
        return sessions;
    }

    public void setSessions(List<WorkoutBean> sessions) {
        this.sessions = (sessions != null) ? new ArrayList<>(sessions) : new ArrayList<>();
    }

    public WorkoutStatus getStatus(){
        return status;
    }

    public void setStatus(WorkoutStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Lo stato non può essere nullo.");
        }
        this.status = status;
    }
    

}
