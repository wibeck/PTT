package tests.unit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class RESTTests extends TestCase {
  
  @Test
  void hintServiceFunctionTest() {
    URL myUrl;
    try {
      myUrl = new URL("http://localhost:8080/Hintservice4/hints/hint/1/1");
      
      HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
      con.setRequestMethod("GET");
      con.connect();
     BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      //BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String result = in.readLine();
      in.close();
      System.out.println(result);
      String expected = "click HTML 0 http://localhost:8080/html-files/hi.html;"
          + "click BUTTON 1 http://localhost:8080/html-files/hi.html";
      assertEquals( expected,result );
      
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  @Test
  void hintServiceRangeTest() {
    String url = "http://localhost:8080/Hintservice4/hints/hint/1/4";
    URL myUrl;
    try {
      myUrl = new URL(url);
      
      HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
      con.setRequestMethod("GET");
      con.connect();
     BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      //BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String result = in.readLine();
      in.close();
      System.out.println(result);
      String expected = "Either no task " + url.charAt(url.length() - 1) 
      + " or no test " + url.charAt(url.length() - 3) + " found!";;
      assertEquals( expected,result );
      
    } catch(FileNotFoundException e) {
      
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  @Test
  void questionServiceFunctionTest() {
    URL myUrl;
    try {
      myUrl = new URL("http://localhost:8080/QuestionService/service/get/questions/1/3");
      
      HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
      con.setRequestMethod("GET");
      con.connect();
     BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      //BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String result = in.readLine();
      in.close();
      System.out.println(result);
      String expected = "<p>Copy the last word from the first paragraph: "
          + "<input class=\"quizElements\" type=\"text\" id=\"q0\">"
          + "</p><p>Copy the last word from the first paragraph here, too:"
          + " <input class=\"quizElements\" type=\"text\" id=\"q1\"></p>";
      assertEquals( expected,result );
      
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  void questionServiceRangeTest() {
    String url = "http://localhost:8080/QuestionService/service/get/questions/1/4";
    URL myUrl;
    try {
      myUrl = new URL(url);
      
      HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
      con.setRequestMethod("GET");
      con.connect();
     BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      //BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String result = in.readLine();
      in.close();
      System.out.println(result);
      String expected = "Either no task " + url.charAt(url.length() - 1) 
      + " or no test " + url.charAt(url.length() - 3) + " found!";;
      assertEquals( expected,result );
      
    } catch(FileNotFoundException e) {
      
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  @Test 
  void answerServiceFunctionTest() {
    URL myUrl;
    try {
      myUrl = new URL("http://localhost:8080/QuestionService/service/get/answers/1/3");
      
      HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
      con.setRequestMethod("GET");
      con.connect();
     BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      //BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String result = in.readLine();
      in.close();
      System.out.println(result);
      String expected = "q0:hi;q1:hi";
      assertEquals( expected,result );
      
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  @Test
  void answerServiceRangeTest() {
    String url = "http://localhost:8080/QuestionService/service/get/answers/1/4";
    URL myUrl;
    try {
      myUrl = new URL(url);
      
      HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
      con.setRequestMethod("GET");
      con.connect();
     BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      //BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String result = in.readLine();
      in.close();
      System.out.println(result);
      String expected = "Either no task " + url.charAt(url.length() - 1) 
      + " or no test " + url.charAt(url.length() - 3) + " found!";;
      assertEquals( expected,result );
      
    } catch(FileNotFoundException e) {
      
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  @Test
  void preEventServiceFunctionTest() {
    URL myUrl;
    try {
      myUrl = new URL("http://localhost:8080/QuestionService/service/get/preEvent/1/3");
      
      HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
      con.setRequestMethod("GET");
      con.connect();
     BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      //BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String result = in.readLine();
      in.close();
      System.out.println(result);
      String expected = "click HTML 0 http://localhost:8080/html-files/hi.html";
      assertEquals( expected,result );
      
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  @Test
  void preEventServiceRangeTest() {
    String url = "http://localhost:8080/QuestionService/service/get/preEvent/1/4";
    URL myUrl;
    try {
      myUrl = new URL(url);
      
      HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
      con.setRequestMethod("GET");
      con.connect();
     BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      //BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String result = in.readLine();
      in.close();
      System.out.println(result);
      String expected = "Either no task " + url.charAt(url.length() - 1) 
      + " or no test " + url.charAt(url.length() - 3) + " found!";
      assertEquals( expected,result );
      
    } catch(FileNotFoundException e) {
      
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
}
