package ru.sfedu.mavenproject.bean;

import ru.sfedu.mavenproject.BookStatus;
import ru.sfedu.mavenproject.CoverType;
import ru.sfedu.mavenproject.PriceParameters;

/**
 * Class Order
 */
public class Order extends Book {

  //
  // Fields
  //

  private long id;
  private String orderDate;
  private CoverType coverType;
  private Employee bookMaker;
  private Employee bookEditor;
  private PriceParameters bookPriceParameters;
  private int finalNumberOfPages;
  private int numberOfCopies;
  private double price;
  private byte[] fileEdited;
  private byte[] fileForPrinting;
  private BookStatus bookStatus;
  
  //
  // Constructors
  //
  public Order () { };
  
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
   * Set the value of orderDate
   * @param newVar the new value of orderDate
   */
  public void setOrderDate (String newVar) {
    orderDate = newVar;
  }

  /**
   * Get the value of orderDate
   * @return the value of orderDate
   */
  public String getOrderDate () {
    return orderDate;
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
   * Set the value of bookMaker
   * @param newVar the new value of bookMaker
   */
  public void setBookMaker (Employee newVar) {
    bookMaker = newVar;
  }

  /**
   * Get the value of bookMaker
   * @return the value of bookMaker
   */
  public Employee getBookMaker () {
    return bookMaker;
  }

  /**
   * Set the value of bookEditor
   * @param newVar the new value of bookEditor
   */
  public void setBookEditor (Employee newVar) {
    bookEditor = newVar;
  }

  /**
   * Get the value of bookEditor
   * @return the value of bookEditor
   */
  public Employee getBookEditor () {
    return bookEditor;
  }

  /**
   * Set the value of bookPriceParameters
   * @param newVar the new value of bookPriceParameters
   */
  public void setBookPriceParameters (PriceParameters newVar) {
    bookPriceParameters = newVar;
  }

  /**
   * Get the value of bookPriceParameters
   * @return the value of bookPriceParameters
   */
  public PriceParameters getBookPriceParameters () {
    return bookPriceParameters;
  }

  /**
   * Set the value of finalNumberOfPages
   * @param newVar the new value of finalNumberOfPages
   */
  public void setFinalNumberOfPages (int newVar) {
    finalNumberOfPages = newVar;
  }

  /**
   * Get the value of finalNumberOfPages
   * @return the value of finalNumberOfPages
   */
  public int getFinalNumberOfPages () {
    return finalNumberOfPages;
  }

  /**
   * Set the value of numberOfCopies
   * @param newVar the new value of numberOfCopies
   */
  public void setNumberOfCopies (int newVar) {
    numberOfCopies = newVar;
  }

  /**
   * Get the value of numberOfCopies
   * @return the value of numberOfCopies
   */
  public int getNumberOfCopies () {
    return numberOfCopies;
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

  /**
   * Set the value of fileEdited
   * @param newVar the new value of fileEdited
   */
  public void setFileEdited (byte[] newVar) {
    fileEdited = newVar;
  }

  /**
   * Get the value of fileEdited
   * @return the value of fileEdited
   */
  public byte[] getFileEdited () {
    return fileEdited;
  }

  /**
   * Set the value of fileForPrinting
   * @param newVar the new value of fileForPrinting
   */
  public void setFileForPrinting (byte[] newVar) {
    fileForPrinting = newVar;
  }

  /**
   * Get the value of fileForPrinting
   * @return the value of fileForPrinting
   */
  public byte[] getFileForPrinting () {
    return fileForPrinting;
  }

  /**
   * Set the value of bookStatus
   * @param newVar the new value of bookStatus
   */
  public void setBookStatus (BookStatus newVar) {
    bookStatus = newVar;
  }

  /**
   * Get the value of bookStatus
   * @return the value of bookStatus
   */
  public BookStatus getBookStatus () {
    return bookStatus;
  }

  //
  // Other methods
  //

}
