package edu.northeastern.cs5200;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import edu.northeastern.cs5200.daos.UniversityDao;
import edu.northeastern.cs5200.models.Course;
import edu.northeastern.cs5200.models.Faculty;
import edu.northeastern.cs5200.models.Section;
import edu.northeastern.cs5200.models.Student;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JpaApplicationTest {
  @Autowired
  UniversityDao universityDao;

  /**
   * Creating Model.
   */
  @Test
  public void generateModel() {
    //1. Empty the database
    universityDao.truncateDatabase();

    //2. Create Faculties
    Faculty alan = new Faculty("alan", "password", "Alan", "Turin", "123A", true);
    Faculty ada = new Faculty("ada", "password", "Ada", "Lovelace", "123B", true);
    universityDao.createFaculty(alan);
    universityDao.createFaculty(ada);

    // 3. Create Students
    Student alice = new Student("alice", "password", "Alice", "Wonderland", 2020, 12000);
    Student bob = new Student("bob", "password", "Bob", "Hope", 2021, 23000);
    Student charlie = new Student("charlie", "password", "Charlie", "Brown", 2019, 21000);
    Student dan = new Student("dan", "password", "Dan", "Craig", 2019, 0);
    Student edward = new Student("edward", "password", "Edward", "Scissorhands", 2022, 11000);
    Student frank = new Student("frank", "password", "Frank", "Herbert", 2018, 0);
    Student gregory = new Student("gregory", "password", "Gregory", "Peck", 2023, 10000);
    universityDao.createStudent(alice);
    universityDao.createStudent(bob);
    universityDao.createStudent(charlie);
    universityDao.createStudent(dan);
    universityDao.createStudent(edward);
    universityDao.createStudent(frank);
    universityDao.createStudent(gregory);

    //4. Create Courses
    Course cs1234 = new Course();
    cs1234.setLabel("CS1234");
    universityDao.setAuthorForCourse(universityDao.findFacultyByName("Alan"), cs1234);
    Course cs2345 = new Course();
    cs2345.setLabel("CS2345");
    universityDao.setAuthorForCourse(universityDao.findFacultyByName("Alan"), cs2345);
    Course cs3456 = new Course();
    cs3456.setLabel("CS3456");
    universityDao.setAuthorForCourse(universityDao.findFacultyByName("Ada"), cs3456);
    Course cs4567 = new Course();
    cs4567.setLabel("CS4567");
    universityDao.setAuthorForCourse(universityDao.findFacultyByName("Ada"), cs4567);

    //5. Create Sections
    Section sec4321 = new Section();
    sec4321.setTitle("SEC4321");
    sec4321.setSeats(50);
    universityDao.addSectionToCourse(sec4321, universityDao.findCourseByTitle("CS1234"));
    Section sec5432 = new Section();
    sec5432.setTitle("SEC5432");
    sec5432.setSeats(50);
    universityDao.addSectionToCourse(sec5432, universityDao.findCourseByTitle("CS1234"));
    Section sec6543 = new Section();
    sec6543.setTitle("SEC6543");
    sec6543.setSeats(50);
    universityDao.addSectionToCourse(sec6543, universityDao.findCourseByTitle("CS2345"));
    Section sec7654 = new Section();
    sec7654.setTitle("SEC7654");
    sec7654.setSeats(50);
    universityDao.addSectionToCourse(sec7654, universityDao.findCourseByTitle("CS3456"));

    // 6. Enroll Student in sections
    universityDao.enrollStudentsInSection(universityDao.findStudentByName("Alice"),
            universityDao.findSectionByTitle("SEC4321"));
    universityDao.enrollStudentsInSection(universityDao.findStudentByName("Alice"),
            universityDao.findSectionByTitle("SEC5432"));
    universityDao.enrollStudentsInSection(universityDao.findStudentByName("Bob"),
            universityDao.findSectionByTitle("SEC5432"));
    universityDao.enrollStudentsInSection(universityDao.findStudentByName("Charlie"),
            universityDao.findSectionByTitle("SEC6543"));
  }

  /**
   * Test for validating the total number of users.
   */
  @Test
  public void validatePersons() {
    assertEquals(9, universityDao.findAllUsers().size());
  }

  /**
   * Test for validating the total number of faculty.
   */
  @Test
  public void validateFaculty() {
    assertEquals(2, universityDao.findAllFaculty().size());
  }

  /**
   * Test for validating the total number of students.
   */
  @Test
  public void validateStudents() {
    assertEquals(7, universityDao.findAllStudents().size());
  }

  /**
   * Test for validating the total number of courses.
   */
  @Test
  public void validateCourses() {
    assertEquals(4, universityDao.findAllCourses().size());
  }

  /**
   * Test for validating the total number of sections.
   */
  @Test
  public void validateSections() {
    assertEquals(4, universityDao.findAllSections().size());
  }

  /**
   * Test for validating the total number of courses authored by each faculty.
   */
  @Test
  public void validateCourseAuthorship() {
    Faculty alan = universityDao.findFacultyByName("Alan");
    Faculty ada = universityDao.findFacultyByName("Ada");
    assertEquals(2, universityDao.findCoursesForAuthor(alan).size());
    assertEquals(2, universityDao.findCoursesForAuthor(ada).size());
  }

  /**
   * Test for validating the total number of sections per course.
   */
  @Test
  public void validateSectionPerCourse() {
    Course cs1234 = universityDao.findCourseByTitle("CS1234");
    Course cs2345 = universityDao.findCourseByTitle("CS2345");
    Course cs3456 = universityDao.findCourseByTitle("CS3456");
    Course cs4567 = universityDao.findCourseByTitle("CS4567");
    assertEquals(2, universityDao.findSectionForCourse(cs1234).size());
    assertEquals(1, universityDao.findSectionForCourse(cs2345).size());
    assertEquals(1, universityDao.findSectionForCourse(cs3456).size());
    assertEquals(0, universityDao.findSectionForCourse(cs4567).size());
  }

  /**
   * Test for validating the total number of students in each section.
   */
  @Test
  public void validateSectionEnrollments() {
    Section sec4321 = universityDao.findSectionByTitle("SEC4321");
    Section sec5432 = universityDao.findSectionByTitle("SEC5432");
    Section sec6543 = universityDao.findSectionByTitle("SEC6543");
    Section sec7654 = universityDao.findSectionByTitle("SEC7654");
    assertEquals(1, universityDao.findStudentsInSection(sec4321).size());
    assertEquals(2, universityDao.findStudentsInSection(sec5432).size());
    assertEquals(1, universityDao.findStudentsInSection(sec6543).size());
    assertEquals(0, universityDao.findStudentsInSection(sec7654).size());
  }

  /**
   * Test for validating the total number of sections for each student.
   */
  @Test
  public void validateStudentEnrollments() {
    Student alice = universityDao.findStudentByName("Alice");
    Student bob = universityDao.findStudentByName("Bob");
    Student charlie = universityDao.findStudentByName("Charlie");
    Student dan = universityDao.findStudentByName("Dan");
    Student edward = universityDao.findStudentByName("Edward");
    Student frank = universityDao.findStudentByName("Frank");
    Student gregory = universityDao.findStudentByName("Gregory");
    assertEquals(2, universityDao.findSectionsForStudent(alice).size());
    assertEquals(1, universityDao.findSectionsForStudent(bob).size());
    assertEquals(1, universityDao.findSectionsForStudent(charlie).size());
    assertEquals(0, universityDao.findSectionsForStudent(dan).size());
    assertEquals(0, universityDao.findSectionsForStudent(edward).size());
    assertEquals(0, universityDao.findSectionsForStudent(frank).size());
    assertEquals(0, universityDao.findSectionsForStudent(gregory).size());
  }

  /**
   * Test for validating the total number of section seats.
   */
  @Test
  public void validateSectionSeats() {
    assertEquals(49, universityDao.findSectionByTitle("SEC4321").getSeats());
    assertEquals(48, universityDao.findSectionByTitle("SEC5432").getSeats());
    assertEquals(49, universityDao.findSectionByTitle("SEC6543").getSeats());
    assertEquals(50, universityDao.findSectionByTitle("SEC7654").getSeats());
  }
}