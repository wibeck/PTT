package com.ptt.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="question_answers")
public class QuestionaireAnswer implements Serializable {
  
  @Id
  @JoinColumn(name="testerId")
  @ManyToOne
  private Tester userId;
  

  @JoinColumn(name="itemId")
  @ManyToOne
  private QuestionaireItem itemId;
  private String value;
  
  
  public Tester getUserId() {
    return userId;
  }
  
  public void setUserId(Tester userId) {
    this.userId = userId;
  }
  
  public QuestionaireItem getItemId() {
    return itemId;
  }
  public void setItemId(QuestionaireItem itemId) {
    this.itemId = itemId;
  }
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }
  
  
}
