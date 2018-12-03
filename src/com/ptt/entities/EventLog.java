package com.ptt.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="userlogging")
public class EventLog {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int eventId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sessionID", referencedColumnName="sessionID")
    private TestSession sessionID;
    
    @JoinColumn(name="taskId", referencedColumnName="taskId")
    @ManyToOne
    private Task taskId;
    private int time; 
    private int seqOrder;
    private String event;
    private String element;
    private int elementIndex;
    private String url;
    
    
    public int getEventId() {
      return eventId;
    }
    public void setEventId(int eventId) {
      this.eventId = eventId;
    }
    public TestSession getSessionID() {
      return sessionID;
    }
    public void setSessionID(TestSession sessionID) {
      this.sessionID = sessionID;
    }
    public Task getTaskId() {
      return taskId;
    }
    public void setTaskId(Task taskId) {
      this.taskId = taskId;
    }
    public int getTime() {
      return time;
    }
    public void setTime(int time) {
      this.time = time;
    }
    public int getSeqOrder() {
      return seqOrder;
    }
    public void setSeqOrder(int seqOrder) {
      this.seqOrder = seqOrder;
    }
    public String getEvent() {
      return event;
    }
    public void setEvent(String event) {
      this.event = event;
    }
    public String getElement() {
      return element;
    }
    public void setElement(String element) {
      this.element = element;
    }
    
    public int getElementIndex() {
      return elementIndex;
    }
    public void setElementIndex(int elementIndex) {
      this.elementIndex = elementIndex;
    }
     
    public String getUrl() {
      return url;
    }
    public void setUrl(String url) {
      this.url = url;
    }
    
    
}
