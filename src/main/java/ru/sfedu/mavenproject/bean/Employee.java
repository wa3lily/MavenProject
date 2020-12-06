package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import ru.sfedu.mavenproject.enums.EmployeeType;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class Employee
 */
public class Employee extends People {

  @Element
  @CsvBindByName
  private String inn;
  @Element
  @CsvBindByName
  private String workRecordBook;
  @Element
  @CsvBindByName
  private EmployeeType emplpyeeType;

  public Employee () { };

  public void setInn (String newVar) {
    inn = newVar;
  }

  public String getInn () {
    return inn;
  }

  public void setWorkRecordBook (String newVar) {
    workRecordBook = newVar;
  }

  public String getWorkRecordBook () {
    return workRecordBook;
  }

  public void setEmplpyeeType (EmployeeType newVar) {
    emplpyeeType = newVar;
  }

  public EmployeeType getEmplpyeeType () {
    return emplpyeeType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Employee employee = (Employee) o;
    return Objects.equals(inn, employee.inn) &&
            Objects.equals(workRecordBook, employee.workRecordBook) &&
            emplpyeeType == employee.emplpyeeType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), inn, workRecordBook, emplpyeeType);
  }

  @Override
  public String toString() {
    return "Employee{" +
            "id=" + super.getId() +
            ", firstName='" + super.getFirstName() + '\'' +
            ", secondName='" + super.getSecondName() + '\'' +
            ", lastName='" + super.getLastName() + '\'' +
            ", phone='" + super.getPhone() + '\'' +
            "inn='" + inn + '\'' +
            ", workRecordBook='" + workRecordBook + '\'' +
            ", emplpyeeType=" + emplpyeeType +
            '}';
  }
}
