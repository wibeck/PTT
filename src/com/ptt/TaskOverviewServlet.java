package com.ptt;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.ptt.entities.Task;
import com.ptt.entities.TaskKey;


@WebServlet("/overview")
/**
 * This class is the first point of the cyle of PTT2: It delivers an overview of . It sets the index of next task of this testing session, which 
 * is stored as an attribute within the corresponding HttpSession-Object. 
 * 
 * Generate PreTest-Survey;
 * Initialize TestSession; (TestSessionGeneratorServlet)
 * While(remaining Tasks > 0) {
 *  Persist Form-Data;
 *  Pick next task;
 *  Generate TaskOverview;
 *  Redirect to Monitor;
 *  While(!taskFinished || !taskCancelled){
 *    Wait
 *  }
 *  Generate Post-Task-Survey;
 *  }
 *  Generate Post-Test-Survey;
 *  Terminate;
 *  
 * @author Willi
 *
 */
public class TaskOverviewServlet extends HttpServlet{
  
  @PersistenceContext
  EntityManager em;
  @Resource
  UserTransaction tx;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession();
    List<String> list = java.util.Collections.list(session.getAttributeNames());
    try {
      
      if(!request.getParameterMap().isEmpty()) { //Check if the session has some form-inputs to persist
        //persist formdata here
      }
      
      if(!list.contains("taskCounter")) {
        session.setAttribute("taskCounter", "1"); 
       
      } else { //task counter to be updated right after finishing the task
        String tcHelp = (String) session.getAttribute("taskCounter");
        int tC = Integer.parseInt(tcHelp) + 1;
        tcHelp = "" + tC;
        session.setAttribute("taskCounter", tcHelp);
      }
      

      tx.begin();
      Query q = em.createQuery("SELECT p FROM Task p WHERE taskId= :taskCounter");
      q.setParameter("taskCounter", Integer.parseInt((String)session.getAttribute("taskCounter")));
      List<Task> l = q.getResultList();
      Task t = null;
      String redUrl = "";
      if(l.isEmpty()) { //if there are no tasks left, redirect to the last servlet of the cycle
        redUrl = "http://localhost:8330/html-files/finishedTest.html";
      } else {  //if there are tasks left, continue here
        redUrl = "http://localhost:8330/html-files/taskOverview.html";
        t = (Task) l.get(0);
      }
       
      tx.commit();
      
      ServletOutputStream out = response.getOutputStream();
      response.setContentType("text/html");
      
      URL url = new URL(redUrl);
      URLConnection conn1 = url.openConnection();
      conn1.connect();
      Scanner s = new Scanner(url.openStream());
     
      String render = "";
      
      while(s.hasNextLine()) {
        render += s.nextLine(); //predefinded survey to be inserted as innerhtml of #demographicForm
      }
      
      Document doc = Jsoup.parse(render);
      if(redUrl.equals("http://localhost:8330/html-files/taskOverview.html")) {
        doc.getElementById("taskDescription").append(t.getDescription()); //Content to be appended is stemming from Database later
      }
      
      //conn.close();
      out.write(doc.html().getBytes());
      
      
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }  /*catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NamingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }*/ catch (NotSupportedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SystemException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
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
    } 
  
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    doGet(request, response);
  }
  
}