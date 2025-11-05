package it.uniroma2.dicii.ezgym.domain.bean;

public class MacronutrientsBean {
    
    private  double proteins;
    private  double carbohydrates;
    private  double fats;
    private  double fibers;

    public MacronutrientsBean(){}

    public double getProteins(){
        return proteins;
    }

    public void setProteins(double proteins){
        if(proteins < 0){
            throw new IllegalArgumentException("Il numero di proteine deve essere maggiore o uguale a zero");
        }
        this.proteins = proteins;
    }

    public double getCarbohydrates(){
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates){
        if(carbohydrates < 0){
            throw new IllegalArgumentException("Il numero di carboidrati deve essere maggiore o uguale a zero");
        }
        this.carbohydrates = carbohydrates;
    }

    public double getFats(){
        return fats;
    }

    public void setFats(double fats){
        if(fats < 0){
            throw new IllegalArgumentException("Il numero di grassi deve essere positivo");
        }
        this.fats = fats;
    }

    public double getFibers(){
        return fibers;
    }

    public void setFibers(double fibers){
        if(fibers < 0){
            throw new IllegalArgumentException("Il numero di fibre deve essere positivo");
        }
        this.fibers = fibers;
    }
    
}
