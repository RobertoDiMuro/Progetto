package it.uniroma2.dicii.ezgym.bean;


public class ExerciseBean {

    private String name;
    private String focus;

    public ExerciseBean() {
        // Costruttore vuoto
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Il nome dell'esercizio non può essere vuoto o nullo.");
        }
        this.name = name;
    }
    
    public String getFocus(){
        return focus;
    }

    public void setFocus(String focus){
        if(focus == null || focus.trim().isEmpty()){
            throw new IllegalArgumentException("Il focus non può essere vuoto o nullo");
        }
        this.focus = focus;
    }

}
