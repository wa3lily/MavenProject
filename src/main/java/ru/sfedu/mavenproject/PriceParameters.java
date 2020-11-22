package ru.sfedu.mavenproject;

import ru.sfedu.mavenproject.bean.CoverPrice;

/**
 * Class PriceParameters
 */
public class PriceParameters {

  //
  // Fields
  //

  private long id;
  private double pagePrice;
  private CoverPrice coverPrice;
  private double workPrice;
  private String validFromDate;
  private String validToDate;
  
  //
  // Constructors
  //
  public PriceParameters () { };
  
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
   * Set the value of pagePrice
   * @param newVar the new value of pagePrice
   */
  public void setPagePrice (double newVar) {
    pagePrice = newVar;
  }

  /**
   * Get the value of pagePrice
   * @return the value of pagePrice
   */
  public double getPagePrice () {
    return pagePrice;
  }

  /**
   * Set the value of coverPrice
   * @param newVar the new value of coverPrice
   */
  public void setCoverPrice (CoverPrice newVar) {
    coverPrice = newVar;
  }

  /**
   * Get the value of coverPrice
   * @return the value of coverPrice
   */
  public CoverPrice getCoverPrice () {
    return coverPrice;
  }

  /**
   * Set the value of workPrice
   * @param newVar the new value of workPrice
   */
  public void setWorkPrice (double newVar) {
    workPrice = newVar;
  }

  /**
   * Get the value of workPrice
   * @return the value of workPrice
   */
  public double getWorkPrice () {
    return workPrice;
  }

  /**
   * Set the value of validFromDate
   * @param newVar the new value of validFromDate
   */
  public void setValidFromDate (String newVar) {
    validFromDate = newVar;
  }

  /**
   * Get the value of validFromDate
   * @return the value of validFromDate
   */
  public String getValidFromDate () {
    return validFromDate;
  }

  /**
   * Set the value of validToDate
   * @param newVar the new value of validToDate
   */
  public void setValidToDate (String newVar) {
    validToDate = newVar;
  }

  /**
   * Get the value of validToDate
   * @return the value of validToDate
   */
  public String getValidToDate () {
    return validToDate;
  }

  //
  // Other methods
  //

}
