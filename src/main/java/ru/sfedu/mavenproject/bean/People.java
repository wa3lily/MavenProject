package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;

/**
 * Class People
 */
public class People {

  //
  // Fields
  //

  @CsvBindByName
  private long id;
  @CsvBindByName
  private String firstName;
  @CsvBindByName
  private String secondName;
  @CsvBindByName
  private String lastName;
  @CsvBindByName
  private String phone;
  
  //
  // Constructors
  //
  public People () { };
  
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
   * Set the value of firstName
   * @param newVar the new value of firstName
   */
  public void setFirstName (String newVar) {
    firstName = newVar;
  }

  /**
   * Get the value of firstName
   * @return the value of firstName
   */
  public String getFirstName () {
    return firstName;
  }

  /**
   * Set the value of secondName
   * @param newVar the new value of secondName
   */
  public void setSecondName (String newVar) {
    secondName = newVar;
  }

  /**
   * Get the value of secondName
   * @return the value of secondName
   */
  public String getSecondName () {
    return secondName;
  }

  /**
   * Set the value of lastName
   * @param newVar the new value of lastName
   */
  public void setLastName (String newVar) {
    lastName = newVar;
  }

  /**
   * Get the value of lastName
   * @return the value of lastName
   */
  public String getLastName () {
    return lastName;
  }

  /**
   * Set the value of phone
   * @param newVar the new value of phone
   */
  public void setPhone (String newVar) {
    phone = newVar;
  }

  /**
   * Get the value of phone
   * @return the value of phone
   */
  public String getPhone () {
    return phone;
  }

  //
  // Other methods
  //

}
