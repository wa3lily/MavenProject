package ru.sfedu.mavenproject;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.mavenproject.bean.*;
import ru.sfedu.mavenproject.enums.EmployeeType;

public class TestBase {

    public People createPeople (long id, String firstName, String secondName, String lastName, String phone){
        People people = new People();
        people.setId(id);
        people.setFirstName(firstName);
        people.setSecondName(secondName);
        people.setLastName(lastName);
        people.setPhone(phone);
        return people;
    }

    public Author createAuthor (long id, String firstName, String secondName, String lastName, String phone, String email, String degree, String organization){
        //Author author = (Author) createPeople (id, firstName, secondName, lastName, phone);
        Author author = new Author();
        author.setId(id);
        author.setFirstName(firstName);
        author.setSecondName(secondName);
        author.setLastName(lastName);
        author.setPhone(phone);
        author.setEmail(email);
        author.setDegree(degree);
        author.setOrganization(organization);
        return author;
    }

    public Employee createEmployee (long id, String firstName, String secondName, String lastName, String phone, String inn, String workRecordBook, EmployeeType emplpyeeType){
        //Employee employee = (Employee) createPeople (id, firstName, secondName, lastName, phone);
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setSecondName(secondName);
        employee.setLastName(lastName);
        employee.setPhone(phone);
        employee.setInn(inn);
        employee.setWorkRecordBook(workRecordBook);
        employee.setEmplpyeeType(emplpyeeType);
        return employee;
    }

    public Book createBook (long id, Author author, String title, int numberOfPages){
        Book book = new Book();
        book.setId(id);
        book.setAuthor(author);
        book.setTitle(title);
        book.setNumberOfPages(numberOfPages);
        return book;
    }

    public Meeting createMeeting (long id, String meetDate, boolean authorAgreement, boolean editorAgreement){
        Meeting meeting = new Meeting();
        meeting.setId(id);
        meeting.setMeetDate(meetDate);
        meeting.setAuthorAgreement(authorAgreement);
        meeting.setEditorAgreement(editorAgreement);
        return meeting;
    }


}
