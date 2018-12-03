package com.ptt.entities;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity

@Table(name="userdata")
public class Tester {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int testerId;
 
  
  @OneToOne
  @JoinColumn(name="sessionID", referencedColumnName="sessionID")
  private TestSession sessionID;
  
  @ElementCollection(targetClass=QuestionaireAnswer.class)
  @OneToMany(cascade=CascadeType.ALL, mappedBy="userId")
  private List<QuestionaireAnswer> qAnswers  = new LinkedList<>();;
  
  public int getTesterId() {
    return testerId;
  }
  public void setTesterId(int testerId) {
    this.testerId = testerId;
  }
  public TestSession getSessionID() {
    return sessionID;
  }
  public void setSessionID(TestSession sessionID) {
    this.sessionID = sessionID;
  }
  
  

  
  
}
