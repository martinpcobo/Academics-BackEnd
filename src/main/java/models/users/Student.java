package models.users;

public final class Student extends User {
    public Student(Long student_id, String user_name, String user_last_name, String email) {
        super(student_id, user_name, user_last_name, email);
    }
    public Student(Student student_instance) {
        super(student_instance);
    }
}
