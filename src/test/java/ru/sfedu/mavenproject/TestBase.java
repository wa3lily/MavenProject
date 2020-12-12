package ru.sfedu.mavenproject;

import ru.sfedu.mavenproject.bean.*;
import ru.sfedu.mavenproject.enums.BookStatus;
import ru.sfedu.mavenproject.enums.CorrectionsStatus;
import ru.sfedu.mavenproject.enums.CoverType;
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

    public Order createOrder (long id, Author author, String title, int numberOfPages, String orderDate,
                              CoverType coverType, Employee bookMaker, Employee bookEditor, PriceParameters bookPriceParameters,
                              int finalNumberOfPages, int numberOfCopies, double price, BookStatus bookStatus){
        Order order = new Order();
        order.setId(id);
        order.setAuthor(author);
        order.setTitle(title);
        order.setNumberOfPages(numberOfPages);
        order.setOrderDate(orderDate);
        order.setCoverType(coverType);
        order.setBookMaker(bookMaker);
        order.setBookEditor(bookEditor);
        order.setBookPriceParameters(bookPriceParameters);
        order.setFinalNumberOfPages(finalNumberOfPages);
        order.setNumberOfCopies(numberOfCopies);
        order.setPrice(price);
        order.setBookStatus(bookStatus);
        return order;
    }

    public Order createOrderFromBook (Book book, String orderDate,
                              CoverType coverType, Employee bookMaker, Employee bookEditor, PriceParameters bookPriceParameters,
                              int finalNumberOfPages, int numberOfCopies, double price, BookStatus bookStatus){
        Order order = new Order();
        order.setId(book.getId());
        order.setAuthor(book.getAuthor());
        order.setTitle(book.getTitle());
        order.setNumberOfPages(book.getNumberOfPages());
        order.setOrderDate(orderDate);
        order.setCoverType(coverType);
        order.setBookMaker(bookMaker);
        order.setBookEditor(bookEditor);
        order.setBookPriceParameters(bookPriceParameters);
        order.setFinalNumberOfPages(finalNumberOfPages);
        order.setNumberOfCopies(numberOfCopies);
        order.setPrice(price);
        order.setBookStatus(bookStatus);
        return order;
    }

    public Meeting createMeeting (long id, String meetDate, boolean authorAgreement, boolean editorAgreement){
        Meeting meeting = new Meeting();
        meeting.setId(id);
        meeting.setMeetDate(meetDate);
        meeting.setAuthorAgreement(authorAgreement);
        meeting.setEditorAgreement(editorAgreement);
        return meeting;
    }

    public Corrections createCorrections (long id, int page, String textBefore, String textAfter, String comment, Order order, Meeting meet, CorrectionsStatus status){
        Corrections correction = new Corrections();
        correction.setId(id);
        correction.setPage(page);
        correction.setTextBefore(textBefore);
        correction.setTextAfter(textAfter);
        correction.setComment(comment);
        correction.setOrder(order);
        correction.setMeet(meet);
        correction.setStatus(status);
        return correction;
    }

    public PriceParameters createPriceParameters(long id, double pagePrice, CoverPrice coverPrice, double workPrice, String validFromDate, String validToDate){
        PriceParameters priceParameters = new PriceParameters();
        priceParameters.setId(id);
        priceParameters.setPagePrice(pagePrice);
        priceParameters.setCoverPrice(coverPrice);
        priceParameters.setWorkPrice(workPrice);
        priceParameters.setValidFromDate(validFromDate);
        priceParameters.setValidToDate(validToDate);
        return priceParameters;
    }

    public CoverPrice createCoverPrice(long id, CoverType coverType, double price){
        CoverPrice coverPrice = new CoverPrice();
        coverPrice.setId(id);
        coverPrice.setCoverType(coverType);
        coverPrice.setPrice(price);
        return coverPrice;
    }


}
