package com.ptt.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@IdClass(MilestoneKey.class)
@Entity
@Table(name="milestones")
public class Milestone {
  
  @Id
  private int milestoneId;
  
  @Id
  @ManyToOne
  @JoinColumn(name="taskId")
  private Task taskId;
  
  private String url;
  private String element;
  private String elementIndex;
  private String event;
  public int getMilestoneId() {
    return milestoneId;
  }
  public void setMilestoneId(int milestoneId) {
    this.milestoneId = milestoneId;
  }
  public Task getTaskId() {
    return taskId;
  }
  public void setTaskId(Task taskId) {
    this.taskId = taskId;
  }
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getElement() {
    return element;
  }
  public void setElement(String element) {
    this.element = element;
  }
  public String getElementIndex() {
    return elementIndex;
  }
  public void setElementIndex(String elementIndex) {
    this.elementIndex = elementIndex;
  }
  public String getEvent() {
    return event;
  }
  public void setEvent(String event) {
    this.event = event;
  }
  
  
  
}
