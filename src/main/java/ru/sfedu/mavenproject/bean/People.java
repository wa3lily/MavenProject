package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import ru.sfedu.mavenproject.ClassId;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class People
 */
public class People extends ClassId {

  @Element
  @CsvBindByName
  private String firstName;
  @Element
  @CsvBindByName
  private String secondName;
  @Element
  @CsvBindByName
  private String lastName;
  @Element
  @CsvBindByName
  private String phone;

  public People () { };

  public void setFirstName (String newVar) {
    firstName = newVar;
  }

  public String getFirstName () {
    return firstName;
  }

  public void setSecondName (String newVar) {
    secondName = newVar;
  }

  public String getSecondName () {
    return secondName;
  }

  public void setLastName (String newVar) {
    lastName = newVar;
  }

  public String getLastName () {
    return lastName;
  }

  public void setPhone (String newVar) {
    phone = newVar;
  }

  public String getPhone () {
    return phone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    People people = (People) o;
    return Objects.equals(firstName, people.firstName) &&
            Objects.equals(secondName, people.secondName) &&
            Objects.equals(lastName, people.lastName) &&
            Objects.equals(phone, people.phone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), firstName, secondName, lastName, phone);
  }

  @Override
  public String toString() {
    return "People{" +
            "id=" + super.getId() +
            ", firstName='" + firstName + '\'' +
            ", secondName='" + secondName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", phone='" + phone + '\'' +
            '}';
  }
}
