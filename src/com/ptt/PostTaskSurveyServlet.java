package com.ptt;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

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

import com.markupGenerator.MarkupGeneratorRemote;
import com.ptt.entities.QuestionaireItem;
import com.ptt.entities.Test;
import com.ptt.utils.MarkupGeneratorBean;

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
        q.setParameter("taskCounter", Integer.parseInt((String) session.getAttribute("taskCounter")));
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
          renderContent = "http://localhost:8330/html-files/completedTemplate.html";
          
        } else {
          if(status.equals("cancelled")) {
            renderContent = "http://localhost:8330/html-files/cancelledTemplate.html";
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
    Document doc = null;
    
    InitialContext context2;
    try {
      context2 = new InitialContext();
      MarkupGeneratorRemote mG = (MarkupGeneratorRemote) 
          context2.lookup("ejb:/Markup2//MarkupGeneratorBean!com.markupGenerator.MarkupGeneratorRemote");
      String render = mG.generateDocumentFromUrl(renderContent);
      
      doc = Jsoup.parse(render);
    } catch (NamingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
      
      doc.getElementById("postTaskQuestions").html(qItem.getHtml() 
          + "<input type=\"submit\" value=\"let's go!\">");

   
    
    return doc;
  }
}
