package it.uniroma2.dicii.EzGym.domain.model;

public class Macronutrients {
    
    private final double proteins;
    private final double carbohydrates;
    private final double fats;
    private final double fibers;

    public Macronutrients(double proteins, double carbohydrates, double fats, double fibers) {
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.fibers = fibers;
    }

    public double getProteins() {
        return proteins;
    }
    public double getCarbohydrates() {
        return carbohydrates;
    }
    public double getFats() {
        return fats;
    }
    public double getFibers() {
        return fibers;
    }

    public double getTotalCalories(){
        return (proteins * MacronutrientType.PROTEINS.getCaloriesPerGram()) +
               (carbohydrates * MacronutrientType.CARBOHYDRATES.getCaloriesPerGram()) +
               (fats * MacronutrientType.FATS.getCaloriesPerGram()+ fibers * MacronutrientType.FIBERS.getCaloriesPerGram());
    }

   public Macronutrients multiply(double factor) {
        if (factor < 0) {
            throw new IllegalArgumentException("Il fattore moltiplicativo non può essere negativo");
        }
        return new Macronutrients(
            this.proteins * factor,
            this.carbohydrates * factor,
            this.fats * factor,
            this.fibers * factor
        );
    }

}
