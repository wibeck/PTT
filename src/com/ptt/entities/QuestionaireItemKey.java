package com.ptt.entities;

import java.io.Serializable;

public class QuestionaireItemKey implements Serializable{
  private int itemId;
  private int testId;
  
  public QuestionaireItemKey() {}

  public QuestionaireItemKey(int itemId, int testId) {
    super();
    this.itemId = itemId;
    this.testId = testId;
  }

  public int getItemId() {
    return itemId;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }

  public int getTestId() {
    return testId;
  }

  public void setTestId(int testId) {
    this.testId = testId;
  }
  
  
}
