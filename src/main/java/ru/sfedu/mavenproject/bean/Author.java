package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;

/**
 * Class Author
 */
public class Author extends People implements Serializable {

  //
  // Fields
  //

  @CsvBindByName
  private String email;
  @CsvBindByName
  private String degree;
  @CsvBindByName
  private String organization;
  
  //
  // Constructors
  //
  public Author () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of email
   * @param newVar the new value of email
   */
  public void setEmail (String newVar) {
    email = newVar;
  }

  /**
   * Get the value of email
   * @return the value of email
   */
  public String getEmail () {
    return email;
  }

  /**
   * Set the value of degree
   * @param newVar the new value of degree
   */
  public void setDegree (String newVar) {
    degree = newVar;
  }

  /**
   * Get the value of degree
   * @return the value of degree
   */
  public String getDegree () {
    return degree;
  }

  /**
   * Set the value of organization
   * @param newVar the new value of organization
   */
  public void setOrganization (String newVar) {
    organization = newVar;
  }

  /**
   * Get the value of organization
   * @return the value of organization
   */
  public String getOrganization () {
    return organization;
  }

  //
  // Other methods
  //

}
