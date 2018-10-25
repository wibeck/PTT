package com.ptt.entities;

import java.io.Serializable;

public class QuestionaireAnswerKey implements Serializable{

    private int userId;
    private int itemId;
    
    
    public QuestionaireAnswerKey() {}
    
    public QuestionaireAnswerKey(int userId, int itemId) {
      super();
      this.userId = userId;
      this.itemId = itemId;
    }
    
    public int hashCode() {
      String help = userId +""+ itemId;
      
      return Integer.parseInt(help);
    }
    
    public boolean equals(Object tk) {
      boolean help = true;
      if(tk == this) {
        return true;
        
      } else {
        if(!(tk instanceof QuestionaireAnswerKey)) {
          return false;
        } else {
          QuestionaireAnswerKey t = (QuestionaireAnswerKey) tk;
          if(t.userId == this.userId && this.itemId == t.itemId) {
            return true;
          }
        }
      }
      return false;
      
    }

    public int getUserId() {
      return userId;
    }

    public void setUserId(int userId) {
      this.userId = userId;
    }

    public int getItemId() {
      return itemId;
    }

    public void setItemId(int itemId) {
      this.itemId = itemId;
    }
    
    
    
    
}
