package com.markupGenerator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.Scanner;

import javax.ejb.Remote;
import javax.ejb.Stateless;




/**
 * Session Bean implementation class MarkupGenerator
 */
@Stateless
@Remote(MarkupGeneratorRemote.class)
public class MarkupGeneratorBean implements MarkupGeneratorRemote{

  @Override
  public String generateDocumentFromUrl(String source) {
    URL url;
    
    String render = "";
    try {
      url = new URL(source);
      URLConnection conn1 = url.openConnection();
      conn1.connect();
      Scanner s = new Scanner(url.openStream());
     
      
      
      while(s.hasNextLine()) {
        render += s.nextLine(); 
      }
      
      
      s.close();
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return render;
    }

}
