package com.ptt;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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


import com.ptt.entities.QuestionaireItem;
import com.ptt.entities.Test;

@WebServlet("/finish")
public class PostTaskSurveyServlet extends HttpServlet{
  @PersistenceContext
  private EntityManager em;
  @Resource
  private UserTransaction tx;
  
  QuestionaireItem qItem;
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response ) {
    Test tst = (Test) request.getSession().getAttribute("testId");
    HttpSession session = request.getSession();
    
    Cookie[] cookies = request.getCookies();
    
    try{
        tx.begin();
        Query q = em.createQuery("SELECT q FROM QuestionaireItem q "
            + "WHERE testId = :tId AND location = :taskCounter");
        q.setParameter("tId", tst);
        q.setParameter("taskCounter", Integer.parseInt((String)session.getAttribute("taskCounter")));
        qItem = (QuestionaireItem) q.getSingleResult();
        tx.commit();
        
        response.setContentType("text/html");
        ServletOutputStream out = response.getOutputStream();
        response.encodeURL(request.getRequestURL().toString());
   
        String status = "";
        
        for(Cookie c: cookies) {
          if(c.getName().equals("finishStatus")) {
            status = c.getValue();
          }
        }
        String renderContent = "";
        if(status.equals("completed")) {
          renderContent = "completedTemplate";
          
        } else {
          if(status.equals("cancelled")) {
            renderContent = "cancelledTemplate";
          }   
        }

        Document doc = getRenderDocument(renderContent);
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
  
  private Document getRenderDocument(String renderContent) {
    URL url1;
    Document doc = null;
    try {
      url1 = new URL("http://localhost:8080/html-files/" 
      + renderContent + ".html");
      URLConnection conn1 = url1.openConnection();
      conn1.connect();
      Scanner s = new Scanner(url1.openStream());

      String render = "";
      
      
      while(s.hasNextLine()) {
        render += s.nextLine();
      }
      
      doc = Jsoup.parse(render);
      doc.getElementById("postTaskQuestions").html(qItem.getHtml() 
          + "<input type=\"submit\" value=\"let's go!\">");
      s.close();
      
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return doc;
  }
}
