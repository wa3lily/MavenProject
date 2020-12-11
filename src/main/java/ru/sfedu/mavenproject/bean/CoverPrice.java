package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import ru.sfedu.mavenproject.enums.CoverType;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class CoverPrice
 */
public class CoverPrice implements Serializable {

  @Attribute
  @CsvBindByName
  long id;
  @Element
  @CsvBindByName
  private CoverType coverType;
  @Element
  @CsvBindByName
  private double price;

  public CoverPrice () { };

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setCoverType (CoverType newVar) {
    coverType = newVar;
  }

  public CoverType getCoverType () {
    return coverType;
  }

  public void setPrice (double newVar) {
    price = newVar;
  }

  public double getPrice () {
    return price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CoverPrice that = (CoverPrice) o;
    return id == that.id &&
            Double.compare(that.price, price) == 0 &&
            coverType == that.coverType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, coverType, price);
  }

  @Override
  public String toString() {
    return "CoverPrice{" +
            "id=" + id +
            ", coverType=" + coverType +
            ", price=" + price +
            '}';
  }
}
