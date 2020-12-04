package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.mavenproject.ClassId;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class Meeting
 */
public class Meeting extends ClassId {

  @CsvBindByName
  private String meetDate;
  @CsvBindByName
  private boolean authorAgreement;
  @CsvBindByName
  private boolean editorAgreement;

  public Meeting () { };

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
    if (!super.equals(o)) return false;
    Meeting meeting = (Meeting) o;
    return authorAgreement == meeting.authorAgreement &&
            editorAgreement == meeting.editorAgreement &&
            Objects.equals(meetDate, meeting.meetDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), meetDate, authorAgreement, editorAgreement);
  }

  @Override
  public String toString() {
    return "Meeting{" +
            "id=" + super.getId() +
            ", meetDate='" + meetDate + '\'' +
            ", authorAgreement=" + authorAgreement +
            ", editorAgreement=" + editorAgreement +
            '}';
  }
}
