package com.ptt;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.own.AnotherBeanRemote;

@WebServlet("/finish")
public class PostTaskSurveyServlet extends HttpServlet{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response ) {
    
    HttpSession session = request.getSession();
    
    Cookie[] cookies = request.getCookies();
    
   
    
    response.setContentType("text/html");
     try{
         ServletOutputStream out = response.getOutputStream();
         BufferedWriter o = new BufferedWriter(new OutputStreamWriter(out));
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
        String dbConfigFile = "";
        URL url1 = new URL("http://localhost:8330/html-files/" + renderContent + ".html");
        URLConnection conn1 = url1.openConnection();
        conn1.connect();
        Scanner s = new Scanner(url1.openStream());
        URL url2 = new URL("http://localhost:8330/html-files/sourceConfig.txt");
        URLConnection conn2 = url2.openConnection();
        conn2.connect();
        Scanner s2 = new Scanner(url2.openStream());
        
        String render = "";
        
        String table = s2.next("[a-zA-z0-9]*");
        String id = s2.next("[a-zA-z0-9]*");
        String pw = s2.next("[a-zA-z0-9]*");
        Context context = new InitialContext();
        DataSource ds = (DataSource) context.lookup("java:/MySqlDS");
        Connection conn = ds.getConnection();
        
        
        
        while(s.hasNextLine()) {
          render += s.nextLine();
        }
        
      conn.close();
     out.write(render.getBytes());
     this.getServletContext().getContextPath();
     
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }  catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NamingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    
    
  }
  
  private static Context getContext() {
    InitialContext context = null;
    Properties jndiProperties = new Properties();
    jndiProperties.put("java.naming.factory.initial","org.jboss.naming.remote.client.InitialContextFactory");
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
}
