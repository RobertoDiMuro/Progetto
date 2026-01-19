# EzGym

Applicazione JavaFX eseguibile tramite Maven, con supporto a tre modalità di persistenza (**Database**, **FileSystem**, **Demo/in-memory**).
In modalità **FileSystem** sono implementate **solo** le DAO di **Athlete**, **PersonalTrainer** e **User**.
La registrazione è consentita **solo per Atleti**: i **Personal Trainer** vengono inseriti dall’**Admin**.

---

## Requisiti
- Java (versione compatibile con il progetto)
- Maven
- Importare il dump del database

---

## Come eseguire l’applicazione

Da terminale, nella cartella del progetto:

```bash
mvn clean compile
mvn javafx:run
