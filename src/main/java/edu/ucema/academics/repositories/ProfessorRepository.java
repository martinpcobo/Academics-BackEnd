package edu.ucema.academics.repositories;

import edu.ucema.academics.models.courses.Class;
import edu.ucema.academics.models.users.Professor;
import edu.ucema.academics.models.users.User;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<Professor, String> {

}