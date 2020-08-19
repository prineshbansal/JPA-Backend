package edu.northeastern.cs5200.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5200.models.Course;
import edu.northeastern.cs5200.models.Enrollment;
import edu.northeastern.cs5200.models.Faculty;
import edu.northeastern.cs5200.models.Person;
import edu.northeastern.cs5200.models.Section;
import edu.northeastern.cs5200.models.Student;
import edu.northeastern.cs5200.repositories.CourseRepository;
import edu.northeastern.cs5200.repositories.EnrollmentRepository;
import edu.northeastern.cs5200.repositories.FacultyRepository;
import edu.northeastern.cs5200.repositories.PersonRepository;
import edu.northeastern.cs5200.repositories.SectionRepository;
import edu.northeastern.cs5200.repositories.StudentRepository;

@Service
public class UniversityDao {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private FacultyRepository facultyRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private EnrollmentRepository enrollmentRepository;

  @Autowired
  private SectionRepository sectionRepository;

  /**
   * Update Methods.
   */

  public void truncateDatabase() {
    enrollmentRepository.deleteAll();
    sectionRepository.deleteAll();
    courseRepository.deleteAll();
    facultyRepository.deleteAll();
    studentRepository.deleteAll();
    personRepository.deleteAll();
  }

  public Faculty createFaculty(Faculty faculty) {
    return facultyRepository.save(faculty);
  }

  public Student createStudent(Student student) {
    return studentRepository.save(student);
  }

  public Course createCourse(Course course) {
    return courseRepository.save(course);
  }

  public Section createSection(Section section) {
    return sectionRepository.save(section);
  }

  public Course addSectionToCourse(Section section, Course course) {
    section.setCourse(course);
    course.getEnrolledSections().add(section);
    sectionRepository.save(section);
    return courseRepository.save(course);
  }

  public Course setAuthorForCourse(Faculty faculty, Course course) {
    course.setAuthor(faculty);
    faculty.getAuthoredCourses().add(course);
    facultyRepository.save(faculty);
    return courseRepository.save(course);
  }

  public Boolean enrollStudentsInSection(Student student, Section section) {
    int availableSeats = section.getSeats();

    if (availableSeats == 0) {
      return false;
    }

    Enrollment enrollment = new Enrollment();
    enrollment.setSection(section);
    enrollment.setStudent(student);
    enrollmentRepository.save(enrollment);

    section.setSeats(availableSeats - 1);
    sectionRepository.save(section);
    return true;
  }

  /**
   * Find Methods.
   */
  public List<Person> findAllUsers() {
    List<Person> result = (List<Person>) personRepository.findAll();
    return result;
  }

  public List<Faculty> findAllFaculty() {
    List<Faculty> result = (List<Faculty>) facultyRepository.findAll();
    return result;
  }

  public List<Student> findAllStudents() {
    List<Student> result = (List<Student>) studentRepository.findAll();
    return result;
  }

  public List<Course> findAllCourses() {
    List<Course> result = (List<Course>) courseRepository.findAll();
    return result;
  }

  public List<Section> findAllSections() {
    List<Section> result = (List<Section>) sectionRepository.findAll();
    return result;
  }

  public List<Course> findCoursesForAuthor(Faculty author) {
    return author.getAuthoredCourses();
  }

  public List<Section> findSectionForCourse(Course course) {
    return course.getEnrolledSections();
  }

  public List<Student> findStudentsInSection(Section section) {
    List<Enrollment> enrollments = (List<Enrollment>) enrollmentRepository.findAll();
    List<Student> result = new ArrayList<>();

    for (Enrollment enrollment : enrollments) {
      if (enrollment.getSection().getId() == section.getId()) {
        result.add(enrollment.getStudent());
      }
    }

    return result;
  }

  public List<Section> findSectionsForStudent(Student student) {
    List<Enrollment> enrollments = (List<Enrollment>) enrollmentRepository.findAll();
    List<Section> result = new ArrayList<Section>();

    for (Enrollment enrollment : enrollments) {
      if (enrollment.getStudent().getId() == student.getId()) {
        result.add(enrollment.getSection());
      }
    }
    return result;
  }

  /**
   * Extra Find Methods.
   */
  public Faculty findFacultyByName(String input) {
    return facultyRepository.findFacultyByFirstName(input);
  }

  public Course findCourseByTitle(String input) {
    return courseRepository.findCourseByTitle(input);
  }

  public Student findStudentByName(String input) {
    return studentRepository.findStudentByFirstName(input);
  }

  public Section findSectionByTitle(String input) {
    return sectionRepository.findSectionByTitle(input);
  }
}
