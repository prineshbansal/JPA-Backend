package edu.northeastern.cs5200.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String label;

  @ManyToOne
  private Faculty author;

  @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
  private List<Section> enrolledSections;

  public void enrolledSection(Section section) {
    this.getEnrolledSections().add(section);
    if(section.getCourse() != this) {
      section.setCourse(this);
    }
  }

  public Course() {}

  public Course(int id, String label, Faculty author) {
    this.id = id;
    this.label = label;
    this.author = author;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Faculty getAuthor() {
    return author;
  }

  public void setAuthor(Faculty author) {
    this.author = author;
    if (!author.getAuthoredCourses().contains(this)) {
      author.getAuthoredCourses().add(this);
    }
  }

  public List<Section> getEnrolledSections() {
    return enrolledSections;
  }

  public void setEnrolledSections(List<Section> enrolledSections) {
    this.enrolledSections = enrolledSections;
  }
}
