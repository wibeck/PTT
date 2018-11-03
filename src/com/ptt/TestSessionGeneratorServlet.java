package com.ptt;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Scanner;

import javax.annotation.Resource;
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

import com.own.AnotherBeanRemote;
import com.ptt.entities.Test;
import com.ptt.entities.TestSession;
import com.ptt.entities.Tester;

@WebServlet("/test")
public class TestSessionGeneratorServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  @PersistenceContext
  private EntityManager em;
  @Resource
  private UserTransaction tx;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    
    if(!request.getSession().isNew()) {
      request.getSession().invalidate();
    }
    String test = request.getParameter("testId");
    
    
    String renderContent = "";
    try {
      tx.begin();
      setIndizes(request); //Generates tester- and testsession-Ids and sets Attributes for the HttpSession
    response.setContentType("text/html");
    AnotherBeanRemote aB = (AnotherBeanRemote) getContext().lookup("ejb:/OwnEJB3//AnotherBean!"
        + "com.own.AnotherBeanRemote?stateful");
    aB.setProperties(1, 1, Integer.parseInt(test));
    
      ServletOutputStream out = response.getOutputStream();
      
      URL url = new URL("http://localhost:8330/html-files/intro.html");
      URLConnection conn = url.openConnection();
      conn.connect();
      
      Scanner s = new Scanner(url.openStream());
      String render = "";
      while(s.hasNextLine()) {
        render += s.nextLine(); //predefinded survey to be inserted as innerhtml of #demographicForm
      }
      render += ""+ request.getSession().getAttribute("testId");
      Document doc = Jsoup.parse(render);
      doc.getElementById("demographicForm").append("<input type=\"radio\""
          + " name=\"fuck\" value=\"bitch\"> Wut?<br>"
          + "<input type=\"submit\" value=\"let's go!\">"); 
      //Content to be appended is stemming from Database later
      s.close();
      out.write(doc.html().getBytes());
      
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NamingException e) {
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
    } 
    
    
    
  }
  
  private static Context getContext() {
    InitialContext context = null;
    Properties jndiProperties = new Properties();
    jndiProperties.put("java.naming.factory.initial","org.jboss"
        + ".naming.remote.client.InitialContextFactory");
    jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080/");
    jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
    try {
      context = new InitialContext(jndiProperties);
    } catch (NamingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return context;
  }
  
  private void setIndizes(HttpServletRequest request) {
   
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
      session.setAttribute("sessionID", testsession.getSessionID());
      session.setAttribute("testId", Integer.parseInt(test)); //set test Id to this session
      session.setAttribute("testerId",tester.getSessionID()); //set a unique Identifier for each tester by counting the participants that have already taken part
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
}
