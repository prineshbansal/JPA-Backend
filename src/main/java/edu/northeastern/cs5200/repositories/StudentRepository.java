package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {
  @Query("SELECT student FROM Student student where student.firstName =:firstName")
  public Student findStudentByFirstName(@Param("firstName") String firstName);
}
