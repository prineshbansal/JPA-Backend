package edu.northeastern.cs5200.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Section {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private int id;
  private String title;
  private int seats;

  @ManyToOne
  private Course course;

  @OneToMany(mappedBy="section")
  private List<Enrollment> hasEnrollments;

  public void hasEnrollment(Enrollment enrollment) {
    this.hasEnrollments.add(enrollment);
    if(enrollment.getSection()!=this) {
      enrollment.setSection(this);
    }
  }

  public Section() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getSeats() {
    return seats;
  }

  public void setSeats(int seats) {
    this.seats = seats;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
    if(!course.getEnrolledSections().contains(this)) {
      course.getEnrolledSections().add(this);
    }
  }

  public List<Enrollment> getHasEnrollments() {
    return hasEnrollments;
  }

  public void setHasEnrollments(List<Enrollment> hasEnrollments) {
    this.hasEnrollments = hasEnrollments;
  }
}
