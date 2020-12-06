package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Element;
import ru.sfedu.mavenproject.ClassId;
import ru.sfedu.mavenproject.converters.ConverterCoverPrice;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class PriceParameters
 */
public class PriceParameters extends ClassId {

  //
  // Fields
  //
  @Element
  @CsvBindByName
  private double pagePrice;
  @Element
  @CsvCustomBindByName(converter = ConverterCoverPrice.class)
  private CoverPrice coverPrice;
  @Element
  @CsvBindByName
  private double workPrice;
  @Element
  @CsvBindByName
  private String validFromDate;
  @Element
  @CsvBindByName
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    PriceParameters that = (PriceParameters) o;
    return Double.compare(that.pagePrice, pagePrice) == 0 &&
            Double.compare(that.workPrice, workPrice) == 0 &&
            ((coverPrice == null && that.coverPrice == null) || coverPrice.getId() == that.coverPrice.getId()) &&
            Objects.equals(validFromDate, that.validFromDate) &&
            Objects.equals(validToDate, that.validToDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), pagePrice, coverPrice, workPrice, validFromDate, validToDate);
  }

  @Override
  public String toString() {
    String result = "PriceParameters{" +
            "id=" + super.getId() +
            ", pagePrice=" + pagePrice;
    try{
      result += ", coverPrice=" + coverPrice.getId();
    }catch (NullPointerException e){
      result += ", coverPrice=null";
    }
    result += ", workPrice=" + workPrice +
            ", validFromDate='" + validFromDate + '\'' +
            ", validToDate='" + validToDate + '\'' +
            '}';
    return result;
  }

  //
  // Other methods
  //

}
