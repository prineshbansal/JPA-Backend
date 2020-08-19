package edu.northeastern.cs5200.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import edu.northeastern.cs5200.daos.UniversityDao;
import edu.northeastern.cs5200.models.Faculty;

/**
 * Created for testing purposes.
 */
@RestController
public class PersonController {
  @Autowired
  UniversityDao universityDao;

  @GetMapping("/drop")
  public void drop() {
    universityDao.truncateDatabase();
  }

  @GetMapping("/setFaculty")
  public void facultyTest() {
    Faculty alan = new Faculty();
    alan.setFirstName("Alan");
    alan.setLastName("Turin");
    alan.setOffice("123A");
    alan.setTenured(true);

    Faculty ada = new Faculty();
    ada.setFirstName("Ada");
    ada.setLastName("Lovelace");
    ada.setOffice("123B");
    ada.setTenured(true);

    universityDao.createFaculty(alan);
    universityDao.createFaculty(ada);
  }

  @GetMapping("/showFaculty")
  public List<Faculty> findAllFaculty() {
    return (List<Faculty>) universityDao.findAllFaculty();
  }

}
