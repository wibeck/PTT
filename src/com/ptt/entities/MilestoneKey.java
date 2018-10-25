package com.ptt.entities;

import java.io.Serializable;


public class MilestoneKey  implements Serializable{
  
  private int milestoneId;
  private int taskId;
  
  public MilestoneKey() {}

  public MilestoneKey(int milestoneId, int taskId) {
    this.milestoneId = milestoneId;
    this.taskId = taskId;
  }

  public int getMilestoneId() {
    return milestoneId;
  }

  public void setMilestoneId(int milestoneId) {
    this.milestoneId = milestoneId;
  }

  public int getTaskId() {
    return taskId;
  }

  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }
  
  
}
