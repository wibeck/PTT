package com.ptt;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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

import com.ptt.entities.QuestionaireAnswer;
import com.ptt.entities.QuestionaireItem;
import com.ptt.entities.Task;
import com.ptt.entities.TaskKey;
import com.ptt.entities.Test;
import com.ptt.entities.Tester;
import com.ptt.utils.MarkupGeneratorBean;


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
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @PersistenceContext
  private EntityManager em;
  @Resource
  private UserTransaction tx;
  private Task t = null;
  private QuestionaireItem qItem = null;
  private Test tst;
  private Tester tester;
  @Inject
  private MarkupGeneratorBean mG;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession();
    tst = (Test)session.getAttribute("testId");
    tester = (Tester) session.getAttribute("testerId");
    
    try {
      tx.begin();
      
      if(!request.getParameterMap().isEmpty()) { 
        //Check if there are form-inputs to persist from previous (post-task) survey
        persistFormData(request);
      }
      
      String tcHelp = (String) session.getAttribute("taskCounter");
      int tC = Integer.parseInt(tcHelp) + 1;
      tcHelp = "" + tC;
      session.setAttribute("taskCounter", tcHelp);
      
      String destination = getDestinationURL(request);
      
      ServletOutputStream out = response.getOutputStream();
      response.setContentType("text/html");
      
      Document doc = getRenderDocument(destination);
      tx.commit();
      //conn.close();
      out.write(doc.html().getBytes());

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NotSupportedException e) {
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
  
  private Document getRenderDocument(String destination) {
    
    Document doc = null;
    
      
      String render = mG.generateDocumentFromUrl(destination);
      
      doc = Jsoup.parse(render);
      if(destination.equals("http://localhost:8330/html-files/taskOverview.html")) {
        doc.getElementById("taskDescription").append(t.getDescription());
      } else {
        if(destination.equals("http://localhost:8330/html-files/finishedTest.html")) {
          doc.getElementById("postTestQuestions").append(qItem.getHtml() 
              + "<input type=\"submit\" value=\"finish this test!\">" );
        }
       
      }
      return doc;

    
  }
  
  private void persistFormData(HttpServletRequest request) {
    
    Query query = em.createQuery("SELECT qItem  FROM QuestionaireItem qItem "
        + "WHERE testId = :tId AND location = :taskCounter"  );
    query.setParameter("tId", tst);
    query.setParameter("taskCounter", Integer.parseInt((String) 
        request.getSession().getAttribute("taskCounter")));
    qItem = (QuestionaireItem) query.getResultList().get(0);
    
    String data ="";    
    BinaryOperator<String> combiner = (x,y) -> { return x + y;};
    BiFunction<String, ? super Entry<String, String[]>, String> accumulator = 
              (x,e) -> {return combiner.apply(x, e.getKey() + ":" + e.getValue()[0] + ";");};
    String ans = request.getParameterMap().entrySet().stream().reduce(data, accumulator, combiner);

    QuestionaireAnswer qAnswer = new QuestionaireAnswer();
    qAnswer.setUserId(tester);
    qAnswer.setValue(ans);
    qAnswer.setItemId(qItem);
    em.persist(qAnswer);
  }
  
  private String getDestinationURL(HttpServletRequest request) {
    HttpSession session = request.getSession();
    Query q = em.createQuery("SELECT p FROM Task p WHERE seqOrder= :taskCounter AND testId = :tId");
    q.setParameter("taskCounter", Integer.parseInt((String)session.getAttribute("taskCounter")));
    q.setParameter("tId", tst);
    List <Task> l = q.getResultList();
    String destination = "";
    
    //if there are no tasks left, redirect to the last servlet of the cycle
    if( q.getResultList().isEmpty()) { 
      destination = "http://localhost:8330/html-files/finishedTest.html";
      Query q3 = em.createQuery("SELECT u FROM QuestionaireItem u WHERE testId "
          + "= :tId AND location = :taskCounter");
      q3.setParameter("tId", tst);
      q3.setParameter("taskCounter",
         Integer.parseInt( (String) session.getAttribute("taskCounter") ));
      
      qItem = (QuestionaireItem) q3.getSingleResult();
    } else {  //if there are tasks left, continue here
      destination = "http://localhost:8330/html-files/taskOverview.html";
      t = l.get(0);
      session.setAttribute("taskType", t.getType());
    }
    return destination;
  }
  
}