package com.ptt;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;


import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.ptt.entities.EventLog;
import com.ptt.entities.Task;
import com.ptt.entities.Test;
import com.ptt.entities.TestSession;

import edu.emory.mathcs.backport.java.util.Arrays;

@WebServlet("/save")
public class EventPersistorServlet extends HttpServlet {
  @PersistenceContext
  private EntityManager em;
  @Resource
  private UserTransaction tx;
  
  //Constants necessary for parsing
  private HttpSession session;
  private Test  test;
  private TestSession tSession;
  private Task task;
  private int numOfEvents;
  private int seqOrder = 1;
  
  public void doPost(HttpServletRequest request, HttpServletResponse response ) {
    session = request.getSession();
    //obtain request-Body
    String reqBody = readRequestBody(request);
    
    try {
      
      response.setHeader("resp", "whatup");
      FileOutputStream out1 = new FileOutputStream("C://Users//Willi//Desktop/test1.txt");
      response.setContentType("text/html");
      out1.write(reqBody.getBytes());
      out1.close();
      
      tx.begin();
      String[] events = reqBody.split("<br>");
      
      
      //initialize all necessary constants
      test = getTest((int)session.getAttribute("testId"));
      tSession = getTestSession((int)session.getAttribute("sessionID"));
      task = getTask(Integer.parseInt((String)session.getAttribute("taskCounter")), test);
      numOfEvents = em.createQuery("SELECT t FROM EventLog t").getResultList().size() + 1;
      int seqOrder = 1;
      
      
      
      //parse and persist event-entries
     
      for(String event: events) {
        if(event.equals("")) {
          break;
        } else {
          
          em.persist(parseToEventLog(event));
        }
        
        
      }
      
     
      tx.commit();
      
    
     
       
      
     
    } catch (NotSupportedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    } catch (SystemException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalStateException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (RollbackException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (HeuristicMixedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (HeuristicRollbackException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    
   
  }
  
  //helper-Methods 
  
  //to be parallelized
  private EventLog parseToEventLog(String event) {
    String[] details = event.split(" ");
    EventLog res = new EventLog();
    res.setEvent(details[0]);
    res.setElement(details[1]);
    res.setElementIndex(Integer.parseInt(details[2]));
    res.setUrl(details[3]);
    res.setTime(Integer.parseInt(details[4]));
    res.setEventId(numOfEvents);
    res.setSeqOrder(seqOrder);
    res.setTaskId(task);
    res.setSessionID(tSession);
    
    seqOrder++;
    numOfEvents++;
    return res;
  }
  
  private String readRequestBody(HttpServletRequest request) {
    String requestBody = "";
    try {
      BufferedReader in = request.getReader();
      
      String readLine = in.readLine();
      requestBody = readLine;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return requestBody;
  }
  
  private Test getTest(int testId) {
    Query q = em.createQuery("SELECT t FROM Test t WHERE testId = :tId");
    q.setParameter("tId", testId);
    if(q.getResultList().isEmpty()) {
      throw new NullPointerException("no result from query; " + testId);
    }
    return (Test) q.getResultList().get(0);
  }
  
  private TestSession getTestSession(int sessionId) {
    Query q = em.createQuery("SELECT t FROM TestSession t WHERE sessionID = :sId");
    q.setParameter("sId", sessionId);
    return (TestSession) q.getResultList().get(0);
  }
  
  private Task getTask(int taskId, Test testId) {
    Query q = em.createQuery("SELECT t FROM Task t WHERE taskId = :taId AND testId = :tId");
    q.setParameter("taId", taskId);
    q.setParameter("tId", testId);
    return (Task) q.getResultList().get(0);
  }
}
