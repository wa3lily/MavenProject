package ru.sfedu.mavenproject.bean;

import ru.sfedu.mavenproject.enums.CorrectionsStatus;

/**
 * Class Corrections
 */
public class Corrections {

  //
  // Fields
  //

  private long id;
  private int page;
  private String textBefore;
  private String textAfter;
  private byte[] comment;
  private Order book;
  private Meeting meet;
  private CorrectionsStatus status;
  
  //
  // Constructors
  //
  public Corrections () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of id
   * @param newVar the new value of id
   */
  public void setId (long newVar) {
    id = newVar;
  }

  /**
   * Get the value of id
   * @return the value of id
   */
  public long getId () {
    return id;
  }

  /**
   * Set the value of page
   * @param newVar the new value of page
   */
  public void setPage (int newVar) {
    page = newVar;
  }

  /**
   * Get the value of page
   * @return the value of page
   */
  public int getPage () {
    return page;
  }

  /**
   * Set the value of textBefore
   * @param newVar the new value of textBefore
   */
  public void setTextBefore (String newVar) {
    textBefore = newVar;
  }

  /**
   * Get the value of textBefore
   * @return the value of textBefore
   */
  public String getTextBefore () {
    return textBefore;
  }

  /**
   * Set the value of textAfter
   * @param newVar the new value of textAfter
   */
  public void setTextAfter (String newVar) {
    textAfter = newVar;
  }

  /**
   * Get the value of textAfter
   * @return the value of textAfter
   */
  public String getTextAfter () {
    return textAfter;
  }

  /**
   * Set the value of comment
   * @param newVar the new value of comment
   */
  public void setComment (byte[] newVar) {
    comment = newVar;
  }

  /**
   * Get the value of comment
   * @return the value of comment
   */
  public byte[] getComment () {
    return comment;
  }

  /**
   * Set the value of book
   * @param newVar the new value of book
   */
  public void setBook (Order newVar) {
    book = newVar;
  }

  /**
   * Get the value of book
   * @return the value of book
   */
  public Order getBook () {
    return book;
  }

  /**
   * Set the value of meet
   * @param newVar the new value of meet
   */
  public void setMeet (Meeting newVar) {
    meet = newVar;
  }

  /**
   * Get the value of meet
   * @return the value of meet
   */
  public Meeting getMeet () {
    return meet;
  }

  /**
   * Set the value of status
   * @param newVar the new value of status
   */
  public void setStatus (CorrectionsStatus newVar) {
    status = newVar;
  }

  /**
   * Get the value of status
   * @return the value of status
   */
  public CorrectionsStatus getStatus () {
    return status;
  }

  //
  // Other methods
  //

}
