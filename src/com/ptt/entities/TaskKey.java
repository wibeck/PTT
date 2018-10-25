package com.ptt.entities;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


public class TaskKey implements Serializable {
  private int taskId;
  private int testId;
  
  public TaskKey() {}
  
  public TaskKey(int taskId, int testId) {
    this.taskId = taskId;
    this.testId = testId;
  }
  
 public int hashCode() {
   String help = taskId +""+ testId;
   
   return Integer.parseInt(help);
 }
 
 public boolean equals(Object tk) {
   boolean help = true;
   if(tk == this) {
     return true;
     
   } else {
     if(!(tk instanceof TaskKey)) {
       return false;
     } else {
       TaskKey t = (TaskKey) tk;
       if(t.taskId == this.taskId && this.testId == t.testId) {
         return true;
       }
     }
   }
   return false;
   
 }

  public int getTaskId() {
    return taskId;
  }
  
  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }
  
  public int getTestId() {
    return testId;
  }
  
  public void setTestId(int testId) {
    this.testId = testId;
  }


 
 
}
