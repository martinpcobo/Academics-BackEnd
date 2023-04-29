package models.users;

public abstract class User {
    // Attributes
    private Long id;
    private String first_name;
    private String last_name;
    private String name;
    private String email;
    // Constructors
    User(Long user_id, String user_name, String user_last_name, String email) {
        this.id = user_id;
        this.first_name = user_name;
        this.last_name = user_last_name;
        this.name = user_name + ' ' + user_last_name;
        this.email = email;
    }
    User(User user_instance) {
        this.id = user_instance.getIdentifier();
        this.first_name = user_instance.getFirstName();
        this.last_name = user_instance.getLastName();
        this.name = user_instance.getName();
        this.email = user_instance.getEmail();
    }

    // Getters
    public Long getIdentifier() {
        return this.id;
    }
    public String getFirstName() {
        return this.first_name;
    }
    public String getLastName() {
        return this.last_name;
    }
    public String getName() {
        return this.name;
    }
    public String getEmail() {
        return this.email;
    }
}
