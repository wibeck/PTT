package com.markupGenerator;


import javax.ejb.Remote;



public interface MarkupGeneratorRemote {

  public String generateDocumentFromUrl(String url);
}
