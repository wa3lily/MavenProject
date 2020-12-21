package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import ru.sfedu.mavenproject.bean.converters.ConverterCoverPrice;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Class PriceParameters
 */
public class PriceParameters implements Serializable {

  //
  // Fields
  //
  @Attribute
  @CsvBindByName
  long id;
  @Element
  @CsvBindByName
  private double pagePrice;
  @ElementList(required = false)
  @CsvCustomBindByName(converter = ConverterCoverPrice.class)
  private List<CoverPrice> coverPrice;
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


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public List<CoverPrice> getCoverPrice() {
    return coverPrice;
  }

  public void setCoverPrice(List<CoverPrice> coverPrice) {
    this.coverPrice = coverPrice;
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
    PriceParameters that = (PriceParameters) o;
    return id == that.id &&
            Double.compare(that.pagePrice, pagePrice) == 0 &&
            Double.compare(that.workPrice, workPrice) == 0 &&
            ((coverPrice == null && that.coverPrice == null) || coverPrice.stream().allMatch(e1 -> that.coverPrice.stream().anyMatch(e2 -> e2.getId() == e1.getId()))) &&
            Objects.equals(validFromDate, that.validFromDate) &&
            Objects.equals(validToDate, that.validToDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, pagePrice, coverPrice, workPrice, validFromDate, validToDate);
  }

  @Override
  public String toString() {
    String result = "PriceParameters{" +
            "id=" + id +
            ", pagePrice=" + pagePrice +
            ", coverPrice=" + coverPrice +
            ", workPrice=" + workPrice +
            ", validFromDate='" + validFromDate + '\'' +
            ", validToDate='" + validToDate + '\'' +
            '}';
    return result;
  }

  //
  // Other methods
  //

}
