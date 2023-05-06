package edu.ucema.academics.repositories;

import edu.ucema.academics.models.courses.Grade;
import org.springframework.data.repository.CrudRepository;

public interface GradeRepository extends CrudRepository<Grade, String> {
    //@Query("FROM grade WHERE course_id = :course_id AND student_id = :student_id LIMIT 1")
    //Optional<Grade> getGradeByStudentCourse(String course_id, String student_id);
}
