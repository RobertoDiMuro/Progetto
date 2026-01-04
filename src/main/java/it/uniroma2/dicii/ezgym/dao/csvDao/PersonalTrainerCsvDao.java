// package it.uniroma2.dicii.ezgym.dao.csvDao;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.UUID;

// import it.uniroma2.dicii.ezgym.dao.InterfaceDao.PersonalTrainerDao;
// import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;
// import it.uniroma2.dicii.ezgym.domain.model.Role;
// import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

// public class PersonalTrainerCsvDao extends AbstractCsvDao implements PersonalTrainerDao{
    
//     private static final String separator = ";";

//     private static PersonalTrainerCsvDao instance;

//      private PersonalTrainerCsvDao(){
//         super(CsvPaths.PersonalTrainers);
//     }

//     public static synchronized PersonalTrainerCsvDao getInstance(){
//         if(instance == null){
//             instance = new PersonalTrainerCsvDao();
//         }
//         return instance;
//     }

//     @Override
//     public boolean insert(PersonalTrainer personalTrainer, UUID id){
//         String line = toCsv(personalTrainer);
//         if(writeLine(line)){
//             return true;
//         }
//         return false;
//     }

//     @Override
//     public PersonalTrainer findBy(String email){
//         return findAll().stream().filter(personalTrainer -> personalTrainer.getEmail().equals(email)).findFirst().orElse(null);
//     }

//     @Override
//     public List<PersonalTrainer> findAll(){
//         List<String> lines = readAllLines();
//         List<PersonalTrainer> athletes = new ArrayList<>();

//         for(String line : lines){
//             athletes.add(fromCsv(line));
//         }
//         return athletes;
//     }

//     @Override
//    public void delete(UUID id){
//         List<PersonalTrainer> all = findAll();
//         boolean removed = all.removeIf(personalTrainer -> personalTrainer.getId().equals(id));

//         if(!removed){
//             return;
//         }

//         List<String> lines = new ArrayList<>();
//         for(PersonalTrainer p : all){
//             lines.add(toCsv(p));
//         }

//         overwriteAllLines(lines);
//    }

//    private String toCsv(PersonalTrainer personalTrainer){
//         return personalTrainer.getId().toString() + separator + personalTrainer.getName() + separator + personalTrainer.getSurname() + separator +
//                personalTrainer.getEmail() + separator + personalTrainer.getPassword() + separator + personalTrainer.getRole() + separator +
//                personalTrainer.getActiveUsers();
//    }

//    private PersonalTrainer fromCsv(String line){
//     String token[] = line.split(separator);
//     if(token.length != 7){
//             throw new PersistenceException("Riga utente malformata: " + line);
     
//     }

//     UUID id = UUID.fromString(token[0]);
//     String name = token[1];
//     String surname = token[2];
//     String email = token[3];
//     String password = token[4];
//     Role role = Role.valueOf(token[5]);
//     double activeUsers = Double.parseDouble(token[6]);

//     PersonalTrainer personalTrainer = new PersonalTrainer();
//     personalTrainer.setId(id);
//     personalTrainer.setName(name);
//     personalTrainer.setSurname(surname);
//     personalTrainer.setEmail(email);
//     personalTrainer.setPassword(password);
//     personalTrainer.setRole(role);
//     personalTrainer.setActiveUsers(activeUsers);
    
//     return personalTrainer;

//    }
// }
