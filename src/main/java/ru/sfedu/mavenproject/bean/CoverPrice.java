package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.mavenproject.enums.CoverType;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class CoverPrice
 */
public class CoverPrice implements Serializable {

  @CsvBindByName
  private long id;
  @CsvBindByName
  private CoverType coverType;
  @CsvBindByName
  private double price;

  public CoverPrice () { };

  public void setId (long newVar) {
    id = newVar;
  }

  public long getId () {
    return id;
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
