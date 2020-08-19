package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.Faculty;

public interface FacultyRepository extends CrudRepository<Faculty,Integer> {
  @Query("SELECT faculty FROM Faculty faculty where faculty.firstName =:firstName")
  public Faculty findFacultyByFirstName (@Param("firstName") String firstName);
}
