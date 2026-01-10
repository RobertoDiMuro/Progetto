package it.uniroma2.dicii.ezgym.dao.csvdao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

public abstract class AbstractCsvDao {
    
    protected final File file;

    protected AbstractCsvDao(String path){
        this.file = new File(path);
        createFileIfNotExist();
    }

    protected void createFileIfNotExist(){
        try{
            File parent = file.getParentFile();
            if(parent != null && !parent.exists()){
                if(parent.mkdirs()){
                    throw new PersistenceException("Impossibile creare la directory" + parent);
                }
            }if(!file.exists()){
                boolean created = file.createNewFile();
                if(!created){
                    throw new PersistenceException("Impossibile creare il file" + created);
                }
            }
        }catch(IOException _){
            throw new PersistenceException("Impossibile creare il file" + file);
        }
    }

    protected List<String> readAllLines(){
        List<String> out = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();
            while(line != null){
                if(!line.isBlank()){
                    out.add(line);
                }
            }
        }catch(IOException _){
            throw new PersistenceException("Errore leggendo il file" + file);
        }
        return out;
    }

    protected boolean writeLine(String line){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))){
            writer.write(line);
            writer.newLine();
            return true;
        }catch(IOException _){
            throw new PersistenceException("Errore scrivendo il file" + file);
        }
    }

    protected void overwriteAllLines(List<String> lines){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))){
            for(String line : lines){
                writer.write(line);
                writer.newLine();
            }
        }catch(IOException _){
            throw new PersistenceException("Errore riscrivendo il file" + file);
        }
    }
}
