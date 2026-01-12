package it.uniroma2.dicii.ezgym.bean;

import java.util.UUID;

import it.uniroma2.dicii.ezgym.domain.model.Role;

public class UserBean extends AbstractUserDataBean {

    private UUID id;
    private String role;

    public UserBean() {
        //
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Role getRole() {
        return Role.valueOf(role);
    }

    public void setRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Il ruolo non può essere nullo");
        }
        this.role = role.name();
    }
}
