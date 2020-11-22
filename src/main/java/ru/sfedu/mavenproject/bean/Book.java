package ru.sfedu.mavenproject.bean;

/**
 * Class Book
 */
public class Book {

  //
  // Fields
  //

  private long id;
  private Author author;
  private String title;
  private byte[] fileOriginal;
  private int numberOfPages;
  
  //
  // Constructors
  //
  public Book () { };
  
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
   * Set the value of author
   * @param newVar the new value of author
   */
  public void setAuthor (Author newVar) {
    author = newVar;
  }

  /**
   * Get the value of author
   * @return the value of author
   */
  public Author getAuthor () {
    return author;
  }

  /**
   * Set the value of title
   * @param newVar the new value of title
   */
  public void setTitle (String newVar) {
    title = newVar;
  }

  /**
   * Get the value of title
   * @return the value of title
   */
  public String getTitle () {
    return title;
  }

  /**
   * Set the value of fileOriginal
   * @param newVar the new value of fileOriginal
   */
  public void setFileOriginal (byte[] newVar) {
    fileOriginal = newVar;
  }

  /**
   * Get the value of fileOriginal
   * @return the value of fileOriginal
   */
  public byte[] getFileOriginal () {
    return fileOriginal;
  }

  /**
   * Set the value of numberOfPages
   * @param newVar the new value of numberOfPages
   */
  public void setNumberOfPages (int newVar) {
    numberOfPages = newVar;
  }

  /**
   * Get the value of numberOfPages
   * @return the value of numberOfPages
   */
  public int getNumberOfPages () {
    return numberOfPages;
  }

  //
  // Other methods
  //

}
