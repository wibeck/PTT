package com.ptt.entities;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tasks")
public class Task {
  
  public enum TaskType {
    INPUT, RETRIEVAL;
    public static String toString(TaskType t) {
      String res = "";
      switch(t){
        case INPUT: res = "INPUT"; break;
        case RETRIEVAL: res = "RETRIEVAL";break;
      }
      return res;
    }
  }
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="testId", referencedColumnName="testId")
  private Test testId;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int taskId;
  private String entryPoint;
  private String description;
  private int seqOrder;
  private String type;
  
  @ElementCollection(targetClass=Milestone.class)
  @OneToMany(cascade=CascadeType.ALL, mappedBy="taskId")
  private List<Milestone> milestones;
  
  public int getTaskId() {
    return taskId;
  }
  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }
  public Test getTestId() {
    return testId;
  }
  public void setTestId(Test testId) {
    this.testId = testId;
  }
  public String getEntryPoint() {
    return entryPoint;
  }
  public void setEntryPoint(String entryPoint) {
    this.entryPoint = entryPoint;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public int getSeqOrder() {
    return seqOrder;
  }
  public void setSeqOrder(int seqOrder) {
    this.seqOrder = seqOrder;
  }
  public String getType() {
    return type;
  }
  public void setType(TaskType type) {
    this.type = TaskType.toString(type);
  }
  
  
  
}
