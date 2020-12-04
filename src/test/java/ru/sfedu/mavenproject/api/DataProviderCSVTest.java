package ru.sfedu.mavenproject.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import ru.sfedu.mavenproject.TestBase;
import ru.sfedu.mavenproject.bean.*;
import ru.sfedu.mavenproject.enums.BookStatus;
import ru.sfedu.mavenproject.enums.CorrectionsStatus;
import ru.sfedu.mavenproject.enums.CoverType;
import ru.sfedu.mavenproject.enums.EmployeeType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderCSVTest extends TestBase {

    private static Logger log = LogManager.getLogger(DataProviderCSVTest.class);

    //insert
    @Test
    public void testInsertPeopleSuccess() throws Exception{
        log.info("insertPeopleSuccess");
        List<People> listPeople = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        People people1 = createPeople(1,"Иван","Иванович","Иванов","81234567890");
        People people2 = createPeople(2,"Петр","Петрович","Петров","82345678901");
        People people3 = createPeople(3,"Виктор","Иванович","Ткач","83456789012");
        listPeople.add(people1);
        listPeople.add(people2);
        listPeople.add(people3);
        instance.insert(People.class, listPeople);
        assertEquals(people2, instance.getByID(People.class, 2));
    }

    @Test
    public void testInsertPeopleFail() throws Exception{
        log.info("insertPeopleFail");
        List<People> listPeople = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        People people1 = createPeople(1,"Иван","Иванович","Иванов","81234567890");
        People people2 = createPeople(2,"Петр","Петрович","Петров","82345678901");
        People people3 = createPeople(3,"Виктор","Иванович","Ткач","83456789012");
        listPeople.add(people1);
        listPeople.add(people2);
        listPeople.add(people3);
        instance.insert(People.class, listPeople);
        assertNull(instance.getByID(People.class, 4));
    }

    @Test
    public void testInsertAuthorSuccess() throws Exception{
        log.info("insertAuthorSuccess");
        List<Author> listAuthor = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(1,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        listAuthor.add(author);
        instance.insert(Author.class, listAuthor);
        assertEquals(author, instance.getByID(Author.class, 1));
    }

    @Test
    public void testInsertAuthorFail() throws Exception{
        log.info("insertAuthorFail");
        List<Author> listAuthor = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(1,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        listAuthor.add(author);
        instance.insert(Author.class, listAuthor);
        assertNull(instance.getByID(Author.class, 2));
    }

    @Test
    public void testInsertEmployeeSuccess() throws Exception{
        log.info("insertEmployeeSuccess");
        List<Employee> listEmployee = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee1 = createEmployee(1,"Иван","Иванович","Иванов","81234567890", "123456789012","1234567", EmployeeType.CHIEF);
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        listEmployee.add(employee1);
        listEmployee.add(employee2);
        listEmployee.add(employee3);
        instance.insert(Employee.class, listEmployee);
        assertEquals(employee2, instance.getByID(Employee.class, 2));
    }

    @Test
    public void testInsertEmployeeFail() throws Exception{
        log.info("insertEmployeeFail");
        List<Employee> listEmployee = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee1 = createEmployee(1,"Иван","Иванович","Иванов","81234567890", "123456789012","1234567", EmployeeType.CHIEF);
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        listEmployee.add(employee1);
        listEmployee.add(employee2);
        listEmployee.add(employee3);
        instance.insert(Employee.class, listEmployee);
        assertNull(instance.getByID(Employee.class, 4));
    }

    @Test
    public void testInsertMeetingSuccess() throws Exception{
        log.info("insertMeetingSuccess");
        List<Meeting> listMeeting = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Meeting meeting = createMeeting(1,"15.12.2020 11:55",true, false);
        listMeeting.add(meeting);
        instance.insert(Meeting.class, listMeeting);
        assertEquals(meeting, instance.getByID(Meeting.class, 1));
    }

    @Test
    public void testInsertMeetingFail() throws Exception{
        log.info("insertMeetingFail");
        List<Meeting> listMeeting = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Meeting meeting = createMeeting(1,"15.12.2020 11:55",true, false);
        listMeeting.add(meeting);
        instance.insert(Meeting.class, listMeeting);
        assertNull(instance.getByID(Meeting.class, 2));
    }

    @Test
    public void testInsertCoverPriceSuccess() throws Exception{
        log.info("insertCoverPriceSuccess");
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        listCoverPrice.add(coverPrice);
        instance.insert(CoverPrice.class, listCoverPrice);
        assertEquals(coverPrice, instance.getByID(CoverPrice.class, 1));
    }

    @Test
    public void testInsertCoverPriceFail() throws Exception{
        log.info("insertCoverPriceFail");
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        listCoverPrice.add(coverPrice);
        instance.insert(CoverPrice.class, listCoverPrice);
        assertNull(instance.getByID(CoverPrice.class, 2));
    }

    @Test
    public void testInsertPriceParametersSuccess() throws Exception{
        log.info("insertPriceParametersSuccess");
        List<PriceParameters> listPriceParameters = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        PriceParameters priceParameters = createPriceParameters(1, 13.4, coverPrice, 16.3, "01.01.2019", "01.01.2021");
        listPriceParameters.add(priceParameters);
        instance.insert(PriceParameters.class, listPriceParameters);
        assertEquals(priceParameters, instance.getByID(PriceParameters.class, 1));
    }

    @Test
    public void testInsertPriceParametersFail() throws Exception{
        log.info("insertPriceParametersFail");
        List<PriceParameters> listPriceParameters = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        PriceParameters priceParameters = createPriceParameters(1, 13.4, coverPrice, 16.3, "01.01.2019", "01.01.2021");
        listPriceParameters.add(priceParameters);
        instance.insert(PriceParameters.class, listPriceParameters);
        assertNull(instance.getByID(PriceParameters.class, 2));
    }

    @Test
    public void testInsertOrderSuccess() throws Exception{
        log.info("insertOrderSuccess");
        List<Order> listOrder = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        CoverPrice coverPrice = createCoverPrice(1, CoverType.RIGID_COVER, 123.5);
        PriceParameters priceParameters = createPriceParameters(1, 2.4, coverPrice, 1.3, "01.01.2019", "01.01.2021");
        Order order = createOrder(1,author,"Цифровая бухгалтерия",4,"03.09.2020", CoverType.RIGID_COVER, employee2, employee3, priceParameters, 229, 100, 9700.75 , BookStatus.EDITING);
        listOrder.add(order);
        instance.insert(Order.class, listOrder);
        assertEquals(order, instance.getByID(Order.class, 1));
    }

    @Test
    public void testInsertOrderFail() throws Exception{
        log.info("insertOrderFail");
        List<Order> listOrder = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        CoverPrice coverPrice = createCoverPrice(1, CoverType.RIGID_COVER, 123.5);
        PriceParameters priceParameters = createPriceParameters(1, 2.4, coverPrice, 1.3, "01.01.2019", "01.01.2021");
        Order order = createOrder(1,author,"Цифровая бухгалтерия",4,"03.09.2020", CoverType.RIGID_COVER, employee2, employee3, priceParameters, 229, 100, 9700.75 , BookStatus.EDITING);
        listOrder.add(order);
        instance.insert(Order.class, listOrder);
        assertNull(instance.getByID(Order.class, 2));
    }

    @Test
    public void testInsertCorrectionsSuccess() throws Exception{
        log.info("insertCorrectionsSuccess");
        List<Corrections> listCorrections = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        PriceParameters priceParameters = createPriceParameters(1, 2.4, coverPrice, 1.3, "01.01.2019", "01.01.2021");
        Order order = createOrder(1,author,"Цифровая бухгалтерия",4,"03.09.2020", CoverType.RIGID_COVER, employee2, employee3, priceParameters, 229, 100, 9700.75 , BookStatus.EDITING  );
        Corrections corrections = createCorrections(1,35, "Цифровой контроль - это компьютерные системы",
                "Цифровой контроль представляет собой компьютерные системы", "Повторяется конструкция", order, null, CorrectionsStatus.WAIT_AUTHOR_AGR );
        listCorrections.add(corrections);
        instance.insert(Corrections.class, listCorrections);
        assertEquals(corrections, instance.getByID(Corrections.class, 1));
    }

    @Test
    public void testInsertCorrectionsFail() throws Exception{
        log.info("insertCorrectionsFail");
        List<Corrections> listCorrections = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        PriceParameters priceParameters = createPriceParameters(1, 2.4, coverPrice, 1.3, "01.01.2019", "01.01.2021");
        Order order = createOrder(1,author,"Цифровая бухгалтерия",4,"03.09.2020", CoverType.RIGID_COVER, employee2, employee3, priceParameters, 229, 100, 9700.75 , BookStatus.EDITING  );
        Corrections corrections = createCorrections(1,35, "Цифровой контроль - это компьютерные системы",
                "Цифровой контроль представляет собой компьютерные системы", "Повторяется конструкция", order, null, CorrectionsStatus.WAIT_AUTHOR_AGR );
        listCorrections.add(corrections);
        instance.insert(Corrections.class, listCorrections);
        assertNull(instance.getByID(Corrections.class, 2));
    }

    @Test
    public void testInsertBookSuccess() throws Exception{
        log.info("insertBookSuccess");
        List<Book> listBook = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        Book book = createBook(1,author,"Цифровая бухгалтерия",4);
        listBook.add(book);
        instance.insert(Book.class, listBook);
        assertEquals(book, instance.getByID(Book.class,1));

    }

    @Test
    public void testInsertBookFail() throws Exception{
        log.info("insertBookFail");
        List<Book> listBook = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        Book book = createBook(1,author,"Цифровая бухгалтерия",4);
        listBook.add(book);
        instance.insert(Book.class, listBook);
        assertNull(instance.getByID(Book.class,2));

    }
}