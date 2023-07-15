package edu.ucema.academics.repositories;

import edu.ucema.academics.models.courses.Grade;
import org.springframework.data.repository.CrudRepository;

public interface GradeRepository extends CrudRepository<Grade, String> {
    Iterable<Grade> findAllByCourseId(String course_id);
    Iterable<Grade> findAllByStudentId(String student_id);
}
