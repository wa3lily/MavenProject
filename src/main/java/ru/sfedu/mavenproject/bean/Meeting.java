package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class Meeting
 */
public class Meeting implements Serializable {

  @Attribute
  @CsvBindByName
  long id;
  @Element
  @CsvBindByName
  private String meetDate;
  @Element
  @CsvBindByName
  private boolean authorAgreement;
  @Element
  @CsvBindByName
  private boolean editorAgreement;

  public Meeting () { };

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setMeetDate (String newVar) {
    meetDate = newVar;
  }

  public String getMeetDate () {
    return meetDate;
  }

  public void setAuthorAgreement (boolean newVar) {
    authorAgreement = newVar;
  }

  public boolean getAuthorAgreement () {
    return authorAgreement;
  }

  public void setEditorAgreement (boolean newVar) {
    editorAgreement = newVar;
  }

  public boolean getEditorAgreement () {
    return editorAgreement;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Meeting meeting = (Meeting) o;
    return id == meeting.id &&
            authorAgreement == meeting.authorAgreement &&
            editorAgreement == meeting.editorAgreement &&
            Objects.equals(meetDate, meeting.meetDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, meetDate, authorAgreement, editorAgreement);
  }

  @Override
  public String toString() {
    return "Meeting{" +
            "id=" + id +
            ", meetDate='" + meetDate + '\'' +
            ", authorAgreement=" + authorAgreement +
            ", editorAgreement=" + editorAgreement +
            '}';
  }
}
