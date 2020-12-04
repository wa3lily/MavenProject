package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.mavenproject.ClassId;
import ru.sfedu.mavenproject.converters.ConverterMeeting;
import ru.sfedu.mavenproject.converters.ConverterOrder;
import ru.sfedu.mavenproject.enums.CorrectionsStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class Corrections
 */
public class Corrections extends ClassId {

  @CsvBindByName
  private int page;
  @CsvBindByName
  private String textBefore;
  @CsvBindByName
  private String textAfter;
  @CsvBindByName
  private String comment;
  @CsvCustomBindByName(converter = ConverterOrder.class)
  private Order order;
  @CsvCustomBindByName(converter = ConverterMeeting.class)
  private Meeting meet;
  @CsvBindByName
  private CorrectionsStatus status;

  public Corrections () { };

  public void setPage (int newVar) {
    page = newVar;
  }

  public int getPage () {
    return page;
  }

  public void setTextBefore (String newVar) {
    textBefore = newVar;
  }

  public String getTextBefore () {
    return textBefore;
  }

  public void setTextAfter (String newVar) {
    textAfter = newVar;
  }

  public String getTextAfter () {
    return textAfter;
  }

  public void setComment (String newVar) {
    comment = newVar;
  }

  public String getComment () {
    return comment;
  }

  public void setOrder (Order newVar) {
    order = newVar;
  }

  public Order getOrder () {
    return order;
  }

  public void setMeet (Meeting newVar) {
    meet = newVar;
  }

  public Meeting getMeet () {
    return meet;
  }

  public void setStatus (CorrectionsStatus newVar) {
    status = newVar;
  }

  public CorrectionsStatus getStatus () {
    return status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Corrections that = (Corrections) o;
    return page == that.page &&
            Objects.equals(textBefore, that.textBefore) &&
            Objects.equals(textAfter, that.textAfter) &&
            Objects.equals(comment, that.comment) &&
            order.getId() == that.order.getId() &&
            (meet == null && that.meet == null || meet.getId() == that.meet.getId()) &&
            status == that.status;
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(super.hashCode(), page, textBefore, textAfter, comment, order, meet, status);
    return result;
  }

  @Override
  public String toString() {
    String result = "Corrections{" +
            "id=" + super.getId() +
            ", page=" + page +
            ", textBefore='" + textBefore + '\'' +
            ", textAfter='" + textAfter + '\'' +
            ", comment='" + comment + '\'' +
            ", order=" + order.getId();
    try{
      result += ", meet=" + meet.getId();
    }catch (NullPointerException e){
      result += ", meet=null";
    }
    result += ", status=" + status +
            '}';
    return result;
  }
}
