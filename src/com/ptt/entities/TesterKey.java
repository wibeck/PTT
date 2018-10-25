package com.ptt.entities;

import java.io.Serializable;

import javax.persistence.Id;

public class TesterKey implements Serializable {
  private int testerId;
  private int sessionID;
  
  public TesterKey() {}
  
  public TesterKey(int testerId, int sessionID) {
    this.testerId = testerId;
    this.sessionID = sessionID;
  }
  
 public int hashCode() {
   String help = testerId +""+ sessionID;
   
   return Integer.parseInt(help);
 }
 
 public boolean equals(Object tk) {
   boolean help = true;
   if(tk == this) {
     return true;
     
   } else {
     if(!(tk instanceof TesterKey)) {
       return false;
     } else {
       TesterKey t = (TesterKey) tk;
       if(t.testerId == this.testerId && this.sessionID == t.sessionID) {
         return true;
       }
     }
   }
   return false;
   
 }

public int getTesterId() {
  return testerId;
}

public void setTesterId(int testerId) {
  this.testerId = testerId;
}

public int getSessionID() {
  return sessionID;
}

public void setSessionID(int sessionID) {
  this.sessionID = sessionID;
}


 
 
}
