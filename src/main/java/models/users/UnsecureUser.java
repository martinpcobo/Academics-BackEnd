package models.users;

import jakarta.persistence.Entity;

@Entity
public final class UnsecureUser extends User {
    private String password;

    public UnsecureUser() {}
    public UnsecureUser(Long user_id, String user_name, String user_last_name, String email, String password) {
        super(user_id, user_name, user_last_name, email);
        this.password = password;
    }
    public UnsecureUser(UnsecureUser user_instance) {
        super(user_instance);
        this.password = user_instance.password;
    }

    public String getPassword() {
        return this.password;
    }
}
