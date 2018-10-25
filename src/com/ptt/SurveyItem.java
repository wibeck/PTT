package com.ptt;

import java.util.HashMap;

import com.ptt.SurveyItem.Position;

public class SurveyItem {
  public enum Position {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT, DEFAULT
  }
  
  String location;
  String type;
  String name;
  String restrictions;
  String description;
  Position descriptionPosition;
  String html= "";

  public SurveyItem(String location, String type, String name, String restrictions, String description,
      Position descriptionPosition) {
    super();
    this.location = location;
    this.type = type;
    this.name = name;
    this.restrictions = restrictions;
    this.description = description;
    this.descriptionPosition = descriptionPosition;
  }



  public String getHTML() {
    String html = "";
    switch(this.getDescriptionPosition()) {
    case TOP: html= "<p>" + this.description + "<br><input type=\"radio\" name=\"radio\" " + this.restrictions + "></p>"; break;
    case BOTTOM: html ="<p><input type=\"radio\" name=\"radio\" " + this.restrictions + "><br>" + this.description + "</p>";break;
    case LEFT: html= "<p>" + this.description + "<input type=\"radio\" name=\"radio\" " + this.restrictions + "></p>"; break;
    case RIGHT: html ="<p><input type=\"radio\" name=\"radio\" " + this.restrictions + ">" + this.description + "</p>";break;
    case DEFAULT: html ="<input type=\"radio\" name=\"radio\" " + this.restrictions + ">";break;
    }
    return html;
  }



  public Position getDescriptionPosition() {
    // TODO Auto-generated method stub
    return null;
  }


}
