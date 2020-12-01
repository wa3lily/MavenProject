package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.mavenproject.enums.CorrectionsStatus;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class Corrections
 */
public class Corrections implements Serializable {

  @CsvBindByName
  private long id;
  @CsvBindByName
  private int page;
  @CsvBindByName
  private String textBefore;
  @CsvBindByName
  private String textAfter;
  @CsvBindByName
  private byte[] comment;
  @CsvBindByName
  private Order book;
  @CsvBindByName
  private Meeting meet;
  @CsvBindByName
  private CorrectionsStatus status;

  public Corrections () { };

  public void setId (long newVar) {
    id = newVar;
  }

  public long getId () {
    return id;
  }

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

  public void setComment (byte[] newVar) {
    comment = newVar;
  }

  public byte[] getComment () {
    return comment;
  }

  public void setBook (Order newVar) {
    book = newVar;
  }

  public Order getBook () {
    return book;
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
    Corrections that = (Corrections) o;
    return id == that.id &&
            page == that.page &&
            Objects.equals(textBefore, that.textBefore) &&
            Objects.equals(textAfter, that.textAfter) &&
            Arrays.equals(comment, that.comment) &&
            Objects.equals(book, that.book) &&
            Objects.equals(meet, that.meet) &&
            status == that.status;
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(id, page, textBefore, textAfter, book, meet, status);
    result = 31 * result + Arrays.hashCode(comment);
    return result;
  }

  @Override
  public String toString() {
    return "Corrections{" +
            "id=" + id +
            ", page=" + page +
            ", textBefore='" + textBefore + '\'' +
            ", textAfter='" + textAfter + '\'' +
            ", comment=" + Arrays.toString(comment) +
            ", book=" + book +
            ", meet=" + meet +
            ", status=" + status +
            '}';
  }
}
