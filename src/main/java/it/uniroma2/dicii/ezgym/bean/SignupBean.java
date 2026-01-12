package it.uniroma2.dicii.ezgym.bean;

public class SignupBean extends AbstractUserDataBean {

    private String confirmPw;

    public SignupBean() {
        //
    }

    public String getConfirmPw() {
        return confirmPw;
    }

    public void setConfirmPw(String confirmPw, String password) {
        requireNotBlank(confirmPw, "La conferma della password non può essere vuota.");
        if (password == null) {
            throw new IllegalArgumentException("La password non può essere vuota.");
        }
        if (!confirmPw.equals(password)) {
            throw new IllegalArgumentException("La conferma della pasword deve essere uguale alla password inserita.");
        }
        this.confirmPw = confirmPw;
    }
}
