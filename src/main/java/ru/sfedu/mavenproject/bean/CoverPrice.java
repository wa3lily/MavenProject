package ru.sfedu.mavenproject.bean;

import ru.sfedu.mavenproject.CoverType;

/**
 * Class CoverPrice
 */
public class CoverPrice {

  //
  // Fields
  //

  private long id;
  private CoverType coverType;
  private double price;
  
  //
  // Constructors
  //
  public CoverPrice () { };
  
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
   * Set the value of coverType
   * @param newVar the new value of coverType
   */
  public void setCoverType (CoverType newVar) {
    coverType = newVar;
  }

  /**
   * Get the value of coverType
   * @return the value of coverType
   */
  public CoverType getCoverType () {
    return coverType;
  }

  /**
   * Set the value of price
   * @param newVar the new value of price
   */
  public void setPrice (double newVar) {
    price = newVar;
  }

  /**
   * Get the value of price
   * @return the value of price
   */
  public double getPrice () {
    return price;
  }

  //
  // Other methods
  //

}
