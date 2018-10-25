package com.own;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AnotherBeanRemote {
  void addEvent(String event);
  List<String> getEvents();
  /*
   * this Method shall ensure, that all properties are set in the correct
   *  order, including the sessionId of the Session Bean
   */
  void setProperties(int taskId, int testerId, int testId ); 

  
  int getTaskId();
  int getTesterId();
  int getTestId();
 
  
}
