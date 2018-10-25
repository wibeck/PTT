package com.ptt;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.Scanner;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@WebServlet("/finished")
public class FinishedServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
      
      HttpSession session = request.getSession();
      URL url;
      try {
        session.removeAttribute("sessionID");
        session.removeAttribute("testId"); //set test Id to this session
        session.removeAttribute("testerId"); //set a unique Identifier for each tester by counting the participants that have already taken part
        session.removeAttribute("taskCounter"); //set a unique Identifier for each tester by counting the participants that have already taken part
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("text/html");
        url = new URL("http://localhost:8330/html-files/theEnd.html");
        URLConnection conn1 = url.openConnection();
        conn1.connect();
        Scanner s = new Scanner(url.openStream());
       
        String render = "";
        
        while(s.hasNextLine()) {
          render += s.nextLine(); //predefinded survey to be inserted as innerhtml of #demographicForm
        }
        
        Document doc = Jsoup.parse(render);
        
        //conn.close();
        out.write(doc.html().getBytes());
      } catch (MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
      
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
      doGet(request, response);
    }
}
