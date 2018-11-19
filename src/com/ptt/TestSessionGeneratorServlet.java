package com.ptt;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.time.LocalDateTime;


import javax.annotation.Resource;
import javax.inject.Inject;
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

import com.markupGenerator.MarkupGeneratorRemote;
import com.ptt.entities.QuestionaireItem;
import com.ptt.entities.Test;
import com.ptt.entities.TestSession;
import com.ptt.entities.Tester;
import com.ptt.utils.Logged;


@WebServlet("/test")
public class TestSessionGeneratorServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  @PersistenceContext
  private EntityManager em;
  @Resource
  private UserTransaction tx;
  QuestionaireItem qItem ;
  
  
  @Logged
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    
    if(!request.getSession().isNew()) {
      request.getSession().invalidate();
    }
    String test = request.getParameter("testId");
    
    try {
      tx.begin();
      Query q1 = em.createQuery("SELECT t FROM Test t WHERE testId= :tId");
      q1.setParameter("tId", Integer.parseInt(test));
      Test tst = (Test) q1.getResultList().get(0);
      setAttributes(request); 
      response.setContentType("text/html");
      Query q = em.createQuery("SELECT q FROM QuestionaireItem q WHERE testId =:tId AND location = 0");
      q.setParameter("tId", tst);
    
      qItem = (QuestionaireItem) q.getSingleResult();
      
      ServletOutputStream out = response.getOutputStream();
      InitialContext context2;
      Document doc = null ;
      try {
        context2 = new InitialContext();
        MarkupGeneratorRemote mG = (MarkupGeneratorRemote) 
            context2.lookup("ejb:/Markup2//MarkupGeneratorBean!com.markupGenerator.MarkupGeneratorRemote");
        String render = mG.generateDocumentFromUrl("http://localhost:8330/html-files/intro.html");
        doc = Jsoup.parse(render);
        
        doc.getElementById("pretestform").html(qItem.getHtml() + "<input type=\"submit\" value=\"let's go!\">");
      } catch (NamingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      
      
      out.write(doc.html().getBytes());
      
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }  catch (SecurityException e) {
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
    } 

  }
  /**
   * Generates tester- and testsession - Objects  and stores them as Attributes in the HttpSession
   * @param request
   */
  private void setAttributes(HttpServletRequest request) {
    try {
      String test = request.getParameter("testId");
      HttpSession session = request.getSession();
      
      TestSession testsession = new TestSession();
      Tester tester = new Tester();
      Query q = em.createQuery("SELECT t FROM Test t WHERE testId= :tId");
      q.setParameter("tId", Integer.parseInt(test));
      Test tst = (Test) q.getResultList().get(0);
      
      testsession.setStartDateTime(LocalDateTime.now().toString());
      testsession.setTestId(tst);

      em.persist(testsession);
      
      
      tester.setSessionID(testsession);
      testsession.setTesterId(tester);
      
      em.persist(tester);
      tx.commit();
      session.setAttribute("sessionID", testsession);
      session.setAttribute("testId", tst); 
      session.setAttribute("testerId",tester); 
      session.setAttribute("taskCounter", "0");
    
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalStateException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SystemException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }catch (RollbackException e) {
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
  
  private Document getRenderDocument() {
    Document doc = null;
      InitialContext context2;
      try {
        context2 = new InitialContext();
        MarkupGeneratorRemote mG = (MarkupGeneratorRemote) 
            context2.lookup("java:global//Markup2/MarkupGeneratorBean!com.markupGenerator.MarkupGeneratorRemote");

        doc = Jsoup.parse(mG.generateDocumentFromUrl("http://localhost:8330/html-files/intro.html"));
        
        doc.getElementById("pretestform").html(qItem.getHtml() + "<input type=\"submit\" value=\"let's go!\">");
      } catch (NamingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
      
    
    return doc;
  }
  
}
