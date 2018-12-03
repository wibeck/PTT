package com.ptt.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="questionaire_items")
public class QuestionaireItem {
  
  @Id
  private int itemId;
  
  @ManyToOne(cascade=CascadeType.ALL)  
  @JoinColumn(name="testId", referencedColumnName="testId")
  private Test testId;
  private int location;
  private String name;
  private String html;
  private String style;
  private String script;
  
  
  public int getItemId() {
    return itemId;
  }
  public void setItemId(int itemId) {
    this.itemId = itemId;
  }
  public Test getTestId() {
    return testId;
  }
  public void setTestId(Test testId) {
    this.testId = testId;
  }
  public int getLocation() {
    return location;
  }
  public void setLocation(int location) {
    this.location = location;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getHtml() {
    return html;
  }
  public void setHtml(String html) {
    this.html = html;
  }
  public String getStyle() {
    return style;
  }
  public void setStyle(String style) {
    this.style = style;
  }
  public String getScript() {
    return script;
  }
  public void setScript(String script) {
    this.script = script;
  }
  
  
  
  
}
