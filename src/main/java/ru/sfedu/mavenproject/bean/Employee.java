package ru.sfedu.mavenproject.bean;

import ru.sfedu.mavenproject.EmployeeType;

/**
 * Class Employee
 */
public class Employee extends People {

  //
  // Fields
  //

  private long inn;
  private long workRecordBook;
  private EmployeeType emplpyeeType;
  
  //
  // Constructors
  //
  public Employee () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of inn
   * @param newVar the new value of inn
   */
  public void setInn (long newVar) {
    inn = newVar;
  }

  /**
   * Get the value of inn
   * @return the value of inn
   */
  public long getInn () {
    return inn;
  }

  /**
   * Set the value of workRecordBook
   * @param newVar the new value of workRecordBook
   */
  public void setWorkRecordBook (long newVar) {
    workRecordBook = newVar;
  }

  /**
   * Get the value of workRecordBook
   * @return the value of workRecordBook
   */
  public long getWorkRecordBook () {
    return workRecordBook;
  }

  /**
   * Set the value of emplpyeeType
   * @param newVar the new value of emplpyeeType
   */
  public void setEmplpyeeType (EmployeeType newVar) {
    emplpyeeType = newVar;
  }

  /**
   * Get the value of emplpyeeType
   * @return the value of emplpyeeType
   */
  public EmployeeType getEmplpyeeType () {
    return emplpyeeType;
  }

  //
  // Other methods
  //

}
