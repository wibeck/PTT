package com.ptt.entities;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity 
@Table(name="sessions")
public class TestSession {

 
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int sessionID;
  private String startDateTime;
  
  
  @OneToOne(cascade=CascadeType.ALL, mappedBy="sessionID")
  private Tester testerId;
  
  @JoinColumn(name="testId")
  @ManyToOne
  private Test testId;
  private int status;
  
  @ElementCollection(targetClass=EventLog.class)
  @OneToMany(mappedBy="sessionID")
  List<EventLog> logs = new LinkedList<>();
  
  
 
  public String getStartDateTime() {
    return startDateTime;
  }
  public void setStartDateTime(String startDateTime) {
    this.startDateTime = startDateTime;
  }

  public Tester getTesterId() {
    return testerId;
  }
  public void setTesterId(Tester testerId) {
    this.testerId = testerId;
  }
  public Test getTestId() {
    return testId;
  }
  public void setTestId(Test testId) {
    this.testId = testId;
  }
  public int getStatus() {
    return status;
  }
  public void setStatus(int status) {
    this.status = status;
  }
  
  public int getSessionID() {
    return sessionID;
  }
  public void setSessionID(int sessionID) {
    this.sessionID = sessionID;
  }
  public List<EventLog> getLogs() {
    return logs;
  }
  public void setLogs(List<EventLog> logs) {
    this.logs = logs;
  }
}
