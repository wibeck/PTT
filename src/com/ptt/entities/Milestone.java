package com.ptt.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="milestones")
public class Milestone {
  
  @Id
  private int milestoneIndex;
  private int milestoneId;
  
  @ManyToOne
  @JoinColumn(name="testId")
  private Test testId;
  
  
  @ManyToOne
  @JoinColumn(name="taskId")
  private Task taskId;
  
  private int seqOrder;
  
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
  public int getMilestoneIndex() {
    return milestoneIndex;
  }
  public void setMilestoneIndex(int milestoneIndex) {
    this.milestoneIndex = milestoneIndex;
  }
  public Test getTestId() {
    return testId;
  }
  public void setTestId(Test testId) {
    this.testId = testId;
  }
  public Task getTaskId() {
    return taskId;
  }
  public void setTaskId(Task task) {
    this.taskId = task;
  }
  public int getSeqOrder() {
    return seqOrder;
  }
  public void setSeqOrder(int seqOrder) {
    this.seqOrder = seqOrder;
  }
  
  
  
}
