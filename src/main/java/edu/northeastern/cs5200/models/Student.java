package edu.northeastern.cs5200.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Student extends Person {
  private int gradYear;
  private int scholarship;

  @OneToMany(mappedBy = "student")
  private List<Enrollment> enrolledStudents;

  public void enrolledStudent(Enrollment enrollment) {
    this.enrolledStudents.add(enrollment);
    if (enrollment.getStudent() != this) {
      enrollment.setStudent(this);
    }
  }

  public Student() {
  }

  public Student(String username, String password, String firstName, String lastName, int gradYear, int scholarship) {
    super(username, password, firstName, lastName);
    this.gradYear = gradYear;
    this.scholarship = scholarship;
  }

  public int getGradYear() {
    return gradYear;
  }

  public void setGradYear(int gradYear) {
    this.gradYear = gradYear;
  }

  public int getScholarship() {
    return scholarship;
  }

  public void setScholarship(int scholarship) {
    this.scholarship = scholarship;
  }

  public List<Enrollment> getEnrolledStudents() {
    return enrolledStudents;
  }

  public void setEnrolledStudents(List<Enrollment> enrolledStudents) {
    this.enrolledStudents = enrolledStudents;
  }
}
