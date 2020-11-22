package ru.sfedu.mavenproject.bean;

/**
 * Class Meeting
 */
public class Meeting {

  //
  // Fields
  //

  private long id;
  private String meetDate;
  private boolean authorAgreement;
  private boolean editorAgreement;
  
  //
  // Constructors
  //
  public Meeting () { };
  
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
   * Set the value of meetDate
   * @param newVar the new value of meetDate
   */
  public void setMeetDate (String newVar) {
    meetDate = newVar;
  }

  /**
   * Get the value of meetDate
   * @return the value of meetDate
   */
  public String getMeetDate () {
    return meetDate;
  }

  /**
   * Set the value of authorAgreement
   * @param newVar the new value of authorAgreement
   */
  public void setAuthorAgreement (boolean newVar) {
    authorAgreement = newVar;
  }

  /**
   * Get the value of authorAgreement
   * @return the value of authorAgreement
   */
  public boolean getAuthorAgreement () {
    return authorAgreement;
  }

  /**
   * Set the value of editorAgreement
   * @param newVar the new value of editorAgreement
   */
  public void setEditorAgreement (boolean newVar) {
    editorAgreement = newVar;
  }

  /**
   * Get the value of editorAgreement
   * @return the value of editorAgreement
   */
  public boolean getEditorAgreement () {
    return editorAgreement;
  }

  //
  // Other methods
  //

}
