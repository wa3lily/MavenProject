package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.mavenproject.enums.EmployeeType;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class Employee
 */
public class Employee extends People implements Serializable {

  @CsvBindByName
  private long inn;
  @CsvBindByName
  private long workRecordBook;
  @CsvBindByName
  private EmployeeType emplpyeeType;

  public Employee () { };

  public void setInn (long newVar) {
    inn = newVar;
  }

  public long getInn () {
    return inn;
  }

  public void setWorkRecordBook (long newVar) {
    workRecordBook = newVar;
  }

  public long getWorkRecordBook () {
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
    return inn == employee.inn &&
            workRecordBook == employee.workRecordBook &&
            emplpyeeType == employee.emplpyeeType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), inn, workRecordBook, emplpyeeType);
  }

  @Override
  public String toString() {
    return "Employee{" +
            "inn=" + inn +
            ", workRecordBook=" + workRecordBook +
            ", emplpyeeType=" + emplpyeeType +
            '}';
  }
}
