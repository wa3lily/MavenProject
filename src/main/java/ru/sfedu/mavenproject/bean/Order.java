package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.mavenproject.enums.BookStatus;
import ru.sfedu.mavenproject.enums.CoverType;
import ru.sfedu.mavenproject.PriceParameters;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class Order
 */
public class Order extends Book implements Serializable {

  @CsvBindByName
  private long id;
  @CsvBindByName
  private String orderDate;
  @CsvBindByName
  private CoverType coverType;
  @CsvBindByName
  private Employee bookMaker;
  @CsvBindByName
  private Employee bookEditor;
  @CsvBindByName
  private PriceParameters bookPriceParameters;
  @CsvBindByName
  private int finalNumberOfPages;
  @CsvBindByName
  private int numberOfCopies;
  @CsvBindByName
  private double price;
  @CsvBindByName
  private byte[] fileEdited;
  @CsvBindByName
  private byte[] fileForPrinting;
  @CsvBindByName
  private BookStatus bookStatus;
  
  public Order () { };

  public void setId (long newVar) {
    id = newVar;
  }

  public long getId () {
    return id;
  }

  public void setOrderDate (String newVar) {
    orderDate = newVar;
  }

  public String getOrderDate () {
    return orderDate;
  }

  public void setCoverType (CoverType newVar) {
    coverType = newVar;
  }

  public CoverType getCoverType () {
    return coverType;
  }

  public void setBookMaker (Employee newVar) {
    bookMaker = newVar;
  }

  public Employee getBookMaker () {
    return bookMaker;
  }

  public void setBookEditor (Employee newVar) {
    bookEditor = newVar;
  }

  public Employee getBookEditor () {
    return bookEditor;
  }

  public void setBookPriceParameters (PriceParameters newVar) {
    bookPriceParameters = newVar;
  }

  public PriceParameters getBookPriceParameters () {
    return bookPriceParameters;
  }

  public void setFinalNumberOfPages (int newVar) {
    finalNumberOfPages = newVar;
  }

  public int getFinalNumberOfPages () {
    return finalNumberOfPages;
  }

  public void setNumberOfCopies (int newVar) {
    numberOfCopies = newVar;
  }

  public int getNumberOfCopies () {
    return numberOfCopies;
  }

  public void setPrice (double newVar) {
    price = newVar;
  }

  public double getPrice () {
    return price;
  }

  public void setFileEdited (byte[] newVar) {
    fileEdited = newVar;
  }

  public byte[] getFileEdited () {
    return fileEdited;
  }

  public void setFileForPrinting (byte[] newVar) {
    fileForPrinting = newVar;
  }

  public byte[] getFileForPrinting () {
    return fileForPrinting;
  }

  public void setBookStatus (BookStatus newVar) {
    bookStatus = newVar;
  }

  public BookStatus getBookStatus () {
    return bookStatus;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Order order = (Order) o;
    return id == order.id &&
            finalNumberOfPages == order.finalNumberOfPages &&
            numberOfCopies == order.numberOfCopies &&
            Double.compare(order.price, price) == 0 &&
            Objects.equals(orderDate, order.orderDate) &&
            coverType == order.coverType &&
            Objects.equals(bookMaker, order.bookMaker) &&
            Objects.equals(bookEditor, order.bookEditor) &&
            Objects.equals(bookPriceParameters, order.bookPriceParameters) &&
            Arrays.equals(fileEdited, order.fileEdited) &&
            Arrays.equals(fileForPrinting, order.fileForPrinting) &&
            bookStatus == order.bookStatus;
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(super.hashCode(), id, orderDate, coverType, bookMaker, bookEditor, bookPriceParameters, finalNumberOfPages, numberOfCopies, price, bookStatus);
    result = 31 * result + Arrays.hashCode(fileEdited);
    result = 31 * result + Arrays.hashCode(fileForPrinting);
    return result;
  }

  @Override
  public String toString() {
    return "Order{" +
            "id=" + id +
            ", orderDate='" + orderDate + '\'' +
            ", coverType=" + coverType +
            ", bookMaker=" + bookMaker +
            ", bookEditor=" + bookEditor +
            ", bookPriceParameters=" + bookPriceParameters +
            ", finalNumberOfPages=" + finalNumberOfPages +
            ", numberOfCopies=" + numberOfCopies +
            ", price=" + price +
            ", fileEdited=" + Arrays.toString(fileEdited) +
            ", fileForPrinting=" + Arrays.toString(fileForPrinting) +
            ", bookStatus=" + bookStatus +
            '}';
  }
}
