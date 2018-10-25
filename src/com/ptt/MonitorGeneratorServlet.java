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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  try {
	    Context context = new InitialContext();
      DataSource ds = (DataSource) context.lookup("java:/MySqlDS");
      Connection conn = ds.getConnection();
  	  PreparedStatement pStmt = conn.prepareStatement("select entryPoint from tasks where testId=? and taskId=?");
      pStmt.setInt(1, Integer.parseInt(request.getSession().getAttribute("testId").toString()));
      pStmt.setInt(2, Integer.parseInt(request.getSession().getAttribute("taskCounter").toString()));
  	  ResultSet res = pStmt.executeQuery();
      res.next();
      String entryPoint = res.getString("entryPoint");
      ServletOutputStream out = response.getOutputStream();
      response.setContentType("text/html");
      
      URL url = new URL("http://localhost:8330/html-files/Monitor.html");
      URLConnection conn1 = url.openConnection();
      conn1.connect();
      Scanner s = new Scanner(url.openStream());
      String render = "";
      response.addCookie(new Cookie("testId",request.getSession().getAttribute("testId").toString()));
      response.addCookie(new Cookie("taskCounter",request.getSession().getAttribute("taskCounter").toString()));
      while(s.hasNextLine()) {
        render += s.nextLine(); //predefinded survey to be inserted as innerhtml of #demographicForm
      }
      conn.close();
      Document doc = Jsoup.parse(render);
      doc.getElementById("monitor").attr("src",entryPoint);
      out.write(doc.html().getBytes());
      
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}