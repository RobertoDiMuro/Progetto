package it.uniroma2.dicii.ezgym.domain.bean;

import java.util.ArrayList;
import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.MealType;

public class MealBean {
    
    private MealType type;
    private List<FoodBean> portions;
    private String notes;

    public MealBean(){
        this.portions = new ArrayList<>();
    }

    public MealType getType(){
        return type;
    }

    public void setType(MealType type){
        if(type == null){
            throw new IllegalArgumentException("Il tipo non può essere nullo");
        }
        this.type = type;
    }

    public List<FoodBean> getPortions(){
        return portions;
    }

    public void setPortions(List<FoodBean> portions) {
        this.portions = portions != null ? new ArrayList<>(portions) : new ArrayList<>();
    }

    public String getNotes(){
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "Nessuna nota aggiuntiva";
    }
}
