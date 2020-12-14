package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Element;
import ru.sfedu.mavenproject.bean.converters.ConverterEmployee;
import ru.sfedu.mavenproject.bean.converters.ConverterPriceParameters;
import ru.sfedu.mavenproject.bean.enums.BookStatus;
import ru.sfedu.mavenproject.bean.enums.CoverType;

import java.util.Objects;

/**
 * Class Order
 */
public class Order extends Book {

  @Element
  @CsvBindByName
  private String orderDate;
  @Element
  @CsvBindByName
  private CoverType coverType;
  @Element
  @CsvCustomBindByName(converter = ConverterEmployee.class)
  private Employee bookMaker;
  @Element
  @CsvCustomBindByName(converter = ConverterEmployee.class)
  private Employee bookEditor;
  @Element
  @CsvCustomBindByName(converter = ConverterPriceParameters.class)
  private PriceParameters bookPriceParameters;
  @Element
  @CsvBindByName
  private int finalNumberOfPages;
  @Element
  @CsvBindByName
  private int numberOfCopies;
  @Element
  @CsvBindByName
  private double price = -1;
  @Element
  @CsvBindByName
  private BookStatus bookStatus;
  
  public Order () { };

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
    return finalNumberOfPages == order.finalNumberOfPages &&
            numberOfCopies == order.numberOfCopies &&
            Double.compare(order.price, price) == 0 &&
            Objects.equals(orderDate, order.orderDate) &&
            coverType == order.coverType &&
            bookMaker.getId() == order.bookMaker.getId() &&
            bookEditor.getId() == order.bookEditor.getId() &&
            bookPriceParameters.getId() == order.bookPriceParameters.getId() &&
            bookStatus == order.bookStatus;
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(super.hashCode(), orderDate, coverType, bookMaker, bookEditor, bookPriceParameters, finalNumberOfPages, numberOfCopies, price, bookStatus);
    return result;
  }

  @Override
  public String toString() {
    return "Order{" +
            "id=" + super.getId() +
            ", author=" + super.getAuthor() +
            ", title='" + super.getTitle() + '\'' +
            ", numberOfPages=" + super.getNumberOfPages() +
            ", orderDate='" + orderDate + '\'' +
            ", coverType=" + coverType +
            ", bookMaker=" + bookMaker.getId() +
            ", bookEditor=" + bookEditor.getId() +
            ", bookPriceParameters=" + bookPriceParameters.getId() +
            ", finalNumberOfPages=" + finalNumberOfPages +
            ", numberOfCopies=" + numberOfCopies +
            ", price=" + price +
            ", bookStatus=" + bookStatus +
            '}';
  }
}
