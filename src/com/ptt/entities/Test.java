package com.ptt.entities;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tests")
public class Test {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private int testId;
  private String testName;
  
  @ElementCollection(targetClass=Task.class)
  @OneToMany(mappedBy="testId")
  private List<Task> tasks = new LinkedList<>();
  
  @ElementCollection(targetClass=QuestionaireItem.class)
  @OneToMany(cascade=CascadeType.ALL, mappedBy="testId")
  private List<QuestionaireItem> qItems  = new LinkedList<>();;

  public int getTestId() {
    return testId;
  }
  
  public void setTestId(int testId) {
    this.testId = testId;
  }
  public String getTestName() {
    return testName;
  }
  public void setTestName(String testName) {
    this.testName = testName;
  }
  public List<Task> getTasks() {
    return tasks;
  }
  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }
  public List<QuestionaireItem> getqItems() {
    return qItems;
  }
  public void setqItems(List<QuestionaireItem> qItems) {
    this.qItems = qItems;
  }
  
  
  
}
