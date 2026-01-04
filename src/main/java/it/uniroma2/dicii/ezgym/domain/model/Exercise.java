package it.uniroma2.dicii.ezgym.domain.model;

public class Exercise {

    private String name;
    private String focus;


    public Exercise(String name, String focus) {
        this.name = name;
        this.focus = focus;
    }

    // Getters
    public String getName() {
        return name;
    }
   
    public String getFocus(){
        return focus;
    }
   
    // Setters
    public void setName(String name){
        this.name = name;
    }

    public void setFocus(String focus){
        this.focus = focus;
    }
    
}
