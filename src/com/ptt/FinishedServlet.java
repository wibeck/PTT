package com.ptt;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
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
import com.ptt.entities.Test;
import com.ptt.entities.Tester;

@WebServlet("/finished")
public class FinishedServlet extends HttpServlet {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @PersistenceContext
  private EntityManager em;
  @Resource
  private UserTransaction tx;
  private Test tst;
  private QuestionaireItem qItem;
  private Tester tester;
  private HttpSession session;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    
    tst = (Test) request.getSession().getAttribute("testId");
    tester = (Tester) request.getSession().getAttribute("testerId");
    session = request.getSession();
      
    try {
        tx.begin();
        persistFormData(request);
        
        session.removeAttribute("sessionID");
        session.removeAttribute("testId"); 
        session.removeAttribute("testerId");
        session.removeAttribute("taskCounter");

        tx.commit();
        response.sendRedirect("http://localhost:8330/html-files/theEnd.html");
        
      } catch (MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (SecurityException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IllegalStateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (NotSupportedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (SystemException e) {
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
      } catch(PersistenceException e) {
        try {
          response.sendRedirect("http://localhost:8330/html-files/TestSystemNotRunning.html");
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
      
      
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
      doGet(request, response);
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
}
