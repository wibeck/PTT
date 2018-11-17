package com.ptt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.ptt.entities.Test;

/**
 * Servlet implementation class MonitorGeneratorServlet
 */
@WebServlet("/runTest")
public class MonitorGeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonitorGeneratorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
	  Test tst = (Test) request.getSession().getAttribute("testId");
	  try {
	    Context context = new InitialContext();
      DataSource ds = (DataSource) context.lookup("java:/MySqlDS");
      Connection conn = ds.getConnection();
  	  PreparedStatement pStmt = conn.prepareStatement("select entryPoint "
  	      + "from tasks where testId=? and seqOrder=?");
      pStmt.setInt(1, tst.getTestId());
      pStmt.setInt(2, Integer.parseInt(
          request.getSession().getAttribute("taskCounter").toString()));
  	  ResultSet res = pStmt.executeQuery();
      res.next();
      String entryPoint = res.getString("entryPoint");
      
      Cookie testId = new Cookie("testId",
          "" + tst.getTestId());
      testId.setPath("/");
      Cookie taskCounter = new Cookie("taskCounter",
          (String) request.getSession().getAttribute("taskCounter"));
      taskCounter.setPath("/");
      Cookie taskType = new Cookie("taskType",
          request.getSession().getAttribute("taskType").toString() );
      taskType.setPath("/");
      Cookie entryP= new Cookie("entryPoint", entryPoint);
      entryP.setPath("/");
      
      response.addCookie(testId);
      response.addCookie(taskCounter);
      response.addCookie(taskType);
      response.addCookie(entryP);
      response.sendRedirect("http://localhost:8330/html-files/Monitor.html");
      
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
