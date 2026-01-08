package it.uniroma2.dicii.ezgym.view.cliMode;

import java.io.BufferedReader;
import java.io.IOException;

import it.uniroma2.bootstrap.InputReader;
import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.controller.WorkoutRequestController;
import it.uniroma2.dicii.ezgym.domain.model.ActivityLevel;
import it.uniroma2.dicii.ezgym.domain.model.Target;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutDay;
import it.uniroma2.dicii.ezgym.exceptions.BackException;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;
import it.uniroma2.dicii.ezgym.utils.BaseCli;

public class CliWorkoutRequest extends BaseCli {

    private static BufferedReader reader = InputReader.getInstance();
    
    private CliWorkoutRequest() {
        //
    }

    public static void startWorkoutRequest(AthleteBean currAthlete) throws NumberFormatException, IOException {

        boolean requested = false;

        while(!requested){
            System.out.println("\n====================================");
            System.out.println("     RICHIESTA SCHEDA ALLENAMENTO    ");
            System.out.println("====================================");
            System.out.println("Inserisci 0 in qualunque momento per tornare alla Home.");

            

            try{

                ageRequest(currAthlete);

                weightRequest(currAthlete);

                heightRequest(currAthlete);

                genderRequest(currAthlete);

                targetRequest(currAthlete);

                activityLevelRequest(currAthlete);

                workoutDayRequest(currAthlete);
                                
                currAthlete.setIsWorkoutRequested(true);

                WorkoutRequestController workoutRequestController = new WorkoutRequestController();
                workoutRequestController.setCurrAthlete(currAthlete);

                System.out.println("\nRichiesta della scheda effettuata!");
                requested = true;
            }catch(BackException e){
                System.out.println("\nTornando alla Home...");
                return;
            }catch(IllegalArgumentException e){
                System.err.println(e.getMessage());
            }catch(PersistenceException e){
                System.err.println(e.getMessage());
                return;
            }
         }
    }

    private static void ageRequest(AthleteBean currAthlete) throws NumberFormatException, IOException {
        
        while(true){
            System.out.println("\nInserisci la tua età:");
            try{
                String ageStr = reader.readLine().trim();
                if(ageStr.isEmpty()) continue;

                if(ageStr.equals("0")){
                    checkBackToHome(ageStr);
                }
                int age = Integer.parseInt(ageStr);
                currAthlete.setAge(age);
                break;
            }catch(NumberFormatException e){
                System.err.println("Età non valida. Inserisci un numero intero.");
            }
        }
    }

    private static void weightRequest(AthleteBean currAthlete) throws NumberFormatException, IOException {
        
        while(true){
            System.out.println("\nInserisci il tuo peso:");
            try{
                String weightStr = reader.readLine().trim();
                if(weightStr.isEmpty()) continue;
                if(weightStr.equals("0")){
                    checkBackToHome(weightStr);
                }
                double weight = Double.parseDouble(weightStr);  
                currAthlete.setWeight(weight);
                break;
            }catch(NumberFormatException e){
                System.err.println("Peso non valido. Inserisci un numero intero.");
            }
        }
    }

    private static void heightRequest(AthleteBean currAthlete) throws NumberFormatException, IOException {
        
        while(true){
            System.out.println("\nInserisci la tua altezza:");            
            try{
                String heightStr = reader.readLine().trim();
                if(heightStr.isEmpty()) continue;
                if(heightStr.equals("0")){
                    checkBackToHome(heightStr);
                }
                double height = Double.parseDouble(heightStr);
                currAthlete.setHeight(height);
                break;
            }catch(NumberFormatException e){
                System.err.println("Altezza non valida. Inserisci un numero intero.");
            }
        }
    }

    private static void genderRequest(AthleteBean currAthlete) throws IOException, BackException {

    while (true) {
        System.out.println("\nInserisci il tuo genere:");
        System.out.println(" 1) Maschio");
        System.out.println(" 2) Femmina");
        System.out.print("\n> ");

        String gender = reader.readLine();
        if (gender == null) continue;
        gender = gender.trim();

        if (gender.equals("0")) {
            checkBackToHome(gender); 
        }

        switch (gender) {
            case "1":
                currAthlete.setGender("Maschio");
                return;
            case "2":
                currAthlete.setGender("Femmina");
                return;
            default:
                System.err.println("Genere non valido. Inserisci 1 o 2 (oppure 0 per tornare indietro).");
        }
    }
}
    private static void targetRequest(AthleteBean currAthlete) throws IOException  {
        
        while(true){
            System.out.println("\nInserisci il tuo obiettivo:");
            System.out.println("  1) Perdere_peso");
            System.out.println("  2) Mantenere");
            System.out.println("  3) Massa_muscolare");
            System.out.println("  4) Tonificare");
            System.out.print("\n> ");

            
            String targetStr = reader.readLine().trim();

            if (targetStr.equals("0")) {
                checkBackToHome(targetStr); 
            }

            switch (targetStr) {
                case "1":
                        currAthlete.setTarget(Target.Perdere_peso);
                    break;
                case "2":
                        currAthlete.setTarget(Target.Mantenere);
                    break;
                case "3":
                        currAthlete.setTarget(Target.Massa_muscolare);
                    break;
                case "4":
                        currAthlete.setTarget(Target.Tonificare);
                    break;
                case "0":
                        checkBackToHome(targetStr);
                default:
                    System.err.println("Scelta non valida. Inserisci 1, 2, 3 o 4.");
                    continue;
            }
        }
    }
    

    private static void activityLevelRequest(AthleteBean currAthlete) throws IOException  {
        
        while(true){
            System.out.println("\nInserisci il tuo livello di attività:");
            System.out.println("  1) Sedentario");
            System.out.println("  2) Leggermente_attivo");
            System.out.println("  3) Moderatamente_attivo");
            System.out.println("  4) Intenso");
            System.out.print("\n> ");

            String activityLevelStr = reader.readLine().trim();

            if (activityLevelStr.equals("0")) {
                checkBackToHome(activityLevelStr); 
            }

            switch (activityLevelStr) {
                case "1":
                    currAthlete.setActivityLevel(ActivityLevel.Sedentario);
                    break;
                case "2":
                    currAthlete.setActivityLevel(ActivityLevel.Leggermente_attivo);
                    break;
                case "3":
                    currAthlete.setActivityLevel(ActivityLevel.Moderatamente_attivo);
                    break;
                case "4":
                    currAthlete.setActivityLevel(ActivityLevel.Intenso);
                    break;
                case "0":
                    checkBackToHome(activityLevelStr);
                default:
                    System.err.println("Scelta non valida. Inserisci 1, 2, 3 o 4.");
                    continue;
            }
            
        }
    }

    private static void workoutDayRequest(AthleteBean currAthlete) throws IOException  {
        
        while(true){
            System.out.println("\nQuanti giorni a settimana ti vuoi allenare?");
            System.out.println("  1) Due_volte");
            System.out.println("  2) Tre_volte");
            System.out.println("  3) Quattro_volte");
            System.out.println("  4) Cinque_volte");
            System.out.println("  5) Sei_volte");
            System.out.print("\n> ");

            
            String workoutDayStr = reader.readLine().trim();

            if (workoutDayStr.equals("0")) {
                checkBackToHome(workoutDayStr); 
            }

            switch (workoutDayStr) {
                case "1":
                    currAthlete.setWorkoutDay(WorkoutDay.Due_volte);
                    break;
                case "2":
                    currAthlete.setWorkoutDay(WorkoutDay.Tre_volte);
                    break;
                case "3":
                    currAthlete.setWorkoutDay(WorkoutDay.Quattro_volte);
                    break;
                case "4":
                    currAthlete.setWorkoutDay(WorkoutDay.Cinque_volte);
                    break;
                case "5":
                    currAthlete.setWorkoutDay(WorkoutDay.Sei_volte);
                    break;
                case "0":
                    checkBackToHome(workoutDayStr);
                default:
                    System.err.println("Scelta non valida. Inserisci 1, 2, 3, 4 o 5.");
                    continue; 
            }
        }
    }
}


