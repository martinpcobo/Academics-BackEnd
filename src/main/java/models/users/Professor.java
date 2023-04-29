package models.users;

public final class Professor extends User {
    public Professor(Long user_id, String user_name, String user_last_name, String email) {
        super(user_id, user_name, user_last_name, email);
    }
    public Professor(Professor professor_instance) {
        super(professor_instance);
    }
}
