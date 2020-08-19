package edu.northeastern.cs5200.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Faculty extends Person {
  private String office;
  private Boolean tenured;

  @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
  private List<Course> authoredCourses;

  public void authoredCourse(Course course) {
    this.authoredCourses.add(course);
    if (course.getAuthor() != this) {
      course.setAuthor(this);
    }
  }

  public Faculty() {
  }

  public Faculty(String username, String password, String firstName, String lastName, String office, Boolean tenured) {
    super(username, password, firstName, lastName);
    this.office = office;
    this.tenured = tenured;
  }

  public String getOffice() {
    return office;
  }

  public void setOffice(String office) {
    this.office = office;
  }

  public Boolean getTenured() {
    return tenured;
  }

  public void setTenured(Boolean tenured) {
    this.tenured = tenured;
  }

  public List<Course> getAuthoredCourses() {
    return authoredCourses;
  }

  public void setAuthoredCourses(List<Course> authoredCourses) {
    this.authoredCourses = authoredCourses;
  }
}
