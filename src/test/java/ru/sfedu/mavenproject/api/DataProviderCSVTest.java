package ru.sfedu.mavenproject.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.TestBase;
import ru.sfedu.mavenproject.bean.*;
import org.junit.jupiter.api.Test;
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
        instance.deleteFile(People.class);
        instance.insertPeople(People.class, listPeople);
        assertEquals(people2, instance.getPeopleByID(People.class, 2));
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
        instance.deleteFile(People.class);
        instance.insertPeople(People.class, listPeople);
        assertNull(instance.getPeopleByID(People.class, 4));
    }

    @Test
    public void testInsertAuthorSuccess() throws Exception{
        log.info("insertAuthorSuccess");
        List<Author> listAuthor = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(1,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        listAuthor.add(author);
        instance.deleteFile(Author.class);
        instance.insertPeople(Author.class, listAuthor);
        assertEquals(author, instance.getPeopleByID(Author.class, 1));
    }

    @Test
    public void testInsertAuthorFail() throws Exception{
        log.info("insertAuthorFail");
        List<Author> listAuthor = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(1,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        listAuthor.add(author);
        instance.deleteFile(Author.class);
        instance.insertPeople(Author.class, listAuthor);
        assertNull(instance.getPeopleByID(Author.class, 2));
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
        instance.deleteFile(Employee.class);
        instance.insertPeople(Employee.class, listEmployee);
        assertEquals(employee2, instance.getPeopleByID(Employee.class, 2));
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
        instance.deleteFile(Employee.class);
        instance.insertPeople(Employee.class, listEmployee);
        assertNull(instance.getPeopleByID(Employee.class, 4));
    }

    @Test
    public void testInsertMeetingSuccess() throws Exception{
        log.info("insertMeetingSuccess");
        List<Meeting> listMeeting = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Meeting meeting = createMeeting(1,"15.12.2020 11:55",true, false);
        listMeeting.add(meeting);
        instance.deleteFile(Meeting.class);
        instance.insertMeeting(listMeeting);
        assertEquals(meeting, instance.getMeetingByID(1));
    }

    @Test
    public void testInsertMeetingFail() throws Exception{
        log.info("insertMeetingFail");
        List<Meeting> listMeeting = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Meeting meeting = createMeeting(1,"15.12.2020 11:55",true, false);
        listMeeting.add(meeting);
        instance.deleteFile(Meeting.class);
        instance.insertMeeting(listMeeting);
        assertNull(instance.getMeetingByID(2));
    }

    @Test
    public void testInsertCoverPriceSuccess() throws Exception{
        log.info("insertCoverPriceSuccess");
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        listCoverPrice.add(coverPrice);
        instance.deleteFile(CoverPrice.class);
        instance.insertCoverPrice(listCoverPrice);
        assertEquals(coverPrice, instance.getCoverPriceByID(1));
    }

    @Test
    public void testInsertCoverPriceFail() throws Exception{
        log.info("insertCoverPriceFail");
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        listCoverPrice.add(coverPrice);
        instance.deleteFile(CoverPrice.class);
        instance.insertCoverPrice(listCoverPrice);
        assertNull(instance.getCoverPriceByID(2));
    }

    @Test
    public void testInsertPriceParametersSuccess() throws Exception{
        log.info("insertPriceParametersSuccess");
        List<PriceParameters> listPriceParameters = new ArrayList<>();
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        CoverPrice coverPrice2 = createCoverPrice(2,CoverType.PAPERBACK, 143.8);
        listCoverPrice.add(coverPrice);
        listCoverPrice.add(coverPrice2);
        PriceParameters priceParameters = createPriceParameters(1, 13.4, listCoverPrice, 16.3, "2019-01-01", "2021-01-01");
        listPriceParameters.add(priceParameters);
        instance.deleteFile(PriceParameters.class);
        instance.deleteFile(CoverPrice.class);
        instance.insertCoverPrice(listCoverPrice);
        instance.insertPriceParameters(listPriceParameters);
        assertEquals(1, instance.getPriceParametersByID(1).getId());
    }

    @Test
    public void testInsertPriceParametersFail() throws Exception{
        log.info("insertPriceParametersFail");
        List<PriceParameters> listPriceParameters = new ArrayList<>();
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        CoverPrice coverPrice2 = createCoverPrice(2,CoverType.PAPERBACK, 143.8);
        listCoverPrice.add(coverPrice);
        listCoverPrice.add(coverPrice2);
        PriceParameters priceParameters = createPriceParameters(1, 13.4, listCoverPrice, 16.3, "2019-01-01", "2021-01-01");
        listPriceParameters.add(priceParameters);
        instance.deleteFile(CoverPrice.class);
        instance.deleteFile(PriceParameters.class);
        instance.insertPriceParameters(listPriceParameters);
        assertNull(instance.getPriceParametersByID(1));
    }

    @Test
    public void testInsertOrderSuccess() throws Exception{
        log.info("insertOrderSuccess");
        List<Order> listOrder = new ArrayList<>();
        List<Employee> listEmployee = new ArrayList<>();
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        List<PriceParameters> listPriceParameters = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        CoverPrice coverPrice = createCoverPrice(1, CoverType.RIGID_COVER, 123.5);
        CoverPrice coverPrice2 = createCoverPrice(2,CoverType.PAPERBACK, 143.8);
        listCoverPrice.add(coverPrice);
        listCoverPrice.add(coverPrice2);
        PriceParameters priceParameters = createPriceParameters(1, 2.4, listCoverPrice, 1.3, "2019-01-01", "2021-01-01");
        Order order = createOrder(1,author,"Цифровая бухгалтерия",4,"2020-09-03", CoverType.RIGID_COVER, employee2, employee3, priceParameters, 229, 100, 9700.75 , BookStatus.EDITING);
        listOrder.add(order);
        listEmployee.add(employee2);
        listEmployee.add(employee3);
        listPriceParameters.add(priceParameters);
        instance.deleteFile(Order.class);
        instance.deleteFile(Employee.class);
        instance.deleteFile(CoverPrice.class);
        instance.deleteFile(PriceParameters.class);
        instance.insertPeople(Employee.class, listEmployee);
        instance.insertCoverPrice(listCoverPrice);
        instance.insertPriceParameters(listPriceParameters);
        instance.insertOrder(listOrder);
        assertEquals(order, instance.getBookByID(Order.class, 1));
    }

    @Test
    public void testInsertOrderFail() throws Exception{
        log.info("insertOrderFail");
        List<Order> listOrder = new ArrayList<>();
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        CoverPrice coverPrice = createCoverPrice(1, CoverType.RIGID_COVER, 123.5);
        CoverPrice coverPrice2 = createCoverPrice(2,CoverType.PAPERBACK, 143.8);
        listCoverPrice.add(coverPrice);
        listCoverPrice.add(coverPrice2);
        PriceParameters priceParameters = createPriceParameters(1, 2.4, listCoverPrice, 1.3, "2019-01-01", "2021-01-01");
        Order order = createOrder(1,author,"Цифровая бухгалтерия",4,"2020-09-03", CoverType.RIGID_COVER, employee2, employee3, priceParameters, 229, 100, 9700.75 , BookStatus.EDITING);
        listOrder.add(order);
        instance.deleteFile(Employee.class);
        instance.deleteFile(CoverPrice.class);
        instance.deleteFile(PriceParameters.class);
        instance.deleteFile(Order.class);
        instance.insertOrder(listOrder);
        assertNull(instance.getBookByID(Order.class, 1));
    }

    @Test
    public void testInsertCorrectionsSuccess() throws Exception{
        log.info("insertCorrectionsSuccess");
        List<Corrections> listCorrections = new ArrayList<>();
        List<Employee> listEmployee = new ArrayList<>();
        List<Author> listAuthor = new ArrayList<>();
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        List<PriceParameters> listPriceParameters = new ArrayList<>();
        List<Order> listOrder = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        CoverPrice coverPrice2 = createCoverPrice(2,CoverType.PAPERBACK, 143.8);
        listCoverPrice.add(coverPrice);
        listCoverPrice.add(coverPrice2);
        PriceParameters priceParameters = createPriceParameters(1, 2.4, listCoverPrice, 1.3, "2019-01-01", "2021-01-01");
        Order order = createOrder(1,author,"Цифровая бухгалтерия",4,"2020-09-03", CoverType.RIGID_COVER, employee2, employee3, priceParameters, 229, 100, 9700.75 , BookStatus.EDITING  );
        Corrections corrections = createCorrections(1,35, "Цифровой контроль - это компьютерные системы",
                "Цифровой контроль представляет собой компьютерные системы", "Повторяется конструкция", order, null, CorrectionsStatus.WAIT_AUTHOR_AGR );
        listEmployee.add(employee2);
        listEmployee.add(employee3);
        listAuthor.add(author);
        listPriceParameters.add(priceParameters);
        listOrder.add(order);
        listCorrections.add(corrections);
        instance.deleteFile(Employee.class);
        instance.deleteFile(Author.class);
        instance.deleteFile(PriceParameters.class);
        instance.deleteFile(Order.class);
        instance.deleteFile(Corrections.class);
        instance.insertPeople(Employee.class, listEmployee);
        instance.insertPeople(Author.class, listAuthor);
        instance.insertCoverPrice(listCoverPrice);
        instance.insertPriceParameters(listPriceParameters);
        instance.insertOrder(listOrder);
        instance.insertCorrections(listCorrections);
        assertEquals(corrections, instance.getCorrectionsByID(1));
    }

    @Test
    public void testInsertCorrectionsFail() throws Exception{
        log.info("insertCorrectionsFail");
        List<Corrections> listCorrections = new ArrayList<>();
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        CoverPrice coverPrice2 = createCoverPrice(2,CoverType.PAPERBACK, 143.8);
        listCoverPrice.add(coverPrice);
        listCoverPrice.add(coverPrice2);
        PriceParameters priceParameters = createPriceParameters(1, 2.4, listCoverPrice, 1.3, "2019-01-01", "2021-01-01");
        Order order = createOrder(1,author,"Цифровая бухгалтерия",4,"2020-09-03", CoverType.RIGID_COVER, employee2, employee3, priceParameters, 229, 100, 9700.75 , BookStatus.EDITING  );
        Corrections corrections = createCorrections(1,35, "Цифровой контроль - это компьютерные системы",
                "Цифровой контроль представляет собой компьютерные системы", "Повторяется конструкция", order, null, CorrectionsStatus.WAIT_AUTHOR_AGR );
        listCorrections.add(corrections);
        instance.deleteFile(Employee.class);
        instance.deleteFile(Author.class);
        instance.deleteFile(PriceParameters.class);
        instance.deleteFile(Order.class);
        instance.deleteFile(Corrections.class);
        instance.insertCorrections(listCorrections);
        assertNull(instance.getCorrectionsByID(1));
    }

    @Test
    public void testInsertBookSuccess() throws Exception{
        log.info("insertBookSuccess");
        List<Book> listBook = new ArrayList<>();
        List<Author> listAuthor = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        Book book = createBook(1,author,"Цифровая бухгалтерия",4);
        listBook.add(book);
        listAuthor.add(author);
        instance.deleteFile(Author.class);
        instance.deleteFile(Book.class);
        instance.insertPeople(Author.class, listAuthor);
        instance.insertBook(listBook);
        assertEquals(book, instance.getBookByID(Book.class,1));
    }

    @Test
    public void testInsertBookFail() throws Exception{
        log.info("insertBookFail");
        List<Book> listBook = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        Book book = createBook(1,author,"Цифровая бухгалтерия",4);
        listBook.add(book);
        instance.deleteFile(Author.class);
        instance.deleteFile(Book.class);
        instance.insertBook(listBook);
        assertNull(instance.getBookByID(Book.class,1));
    }

    @Test
    public void testInsertFewSuccess() throws Exception{
        log.info("testInsertFewSuccess");
        List<Employee> listEmployee = new ArrayList<>();
        List<Employee> expectListEmployee = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteFile(Employee.class);
        Employee employee1 = createEmployee(1,"Иван","Иванович","Иванов","81234567890", "123456789012","1234567", EmployeeType.CHIEF);
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        listEmployee.add(employee1);
        listEmployee.add(employee2);
        instance.insertPeople(Employee.class, listEmployee);
        listEmployee.clear();
        listEmployee.add(employee3);
        assertEquals(expectListEmployee, instance.insertPeople(Employee.class, listEmployee));
        expectListEmployee.add(employee1);
        expectListEmployee.add(employee2);
        expectListEmployee.add(employee3);
        assertEquals(expectListEmployee, instance.read(Employee.class));
    }

    @Test
    public void testInsertFewFail() throws Exception{
        log.info("testInsertFewFail");
        List<Employee> listEmployee = new ArrayList<>();
        List<Employee> expectListEmployee = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteFile(Employee.class);
        Employee employee1 = createEmployee(1,"Иван","Иванович","Иванов","81234567890", "123456789012","1234567", EmployeeType.CHIEF);
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(2,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        listEmployee.add(employee1);
        listEmployee.add(employee2);
        instance.insertPeople(Employee.class, listEmployee);
        listEmployee.clear();
        listEmployee.add(employee1);
        listEmployee.add(employee3);
        expectListEmployee.add(employee3);
        assertEquals(expectListEmployee, instance.insertPeople(Employee.class, listEmployee));
        expectListEmployee.clear();
        expectListEmployee.add(employee1);
        expectListEmployee.add(employee2);
        assertEquals(expectListEmployee, instance.read(Employee.class));
    }

    //read
    @Test
    public void testReadSuccess() throws Exception{
        log.info("testReadSuccess");
        List<Employee> listEmployee = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee1 = createEmployee(1,"Иван","Иванович","Иванов","81234567890", "123456789012","1234567", EmployeeType.CHIEF);
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        listEmployee.add(employee1);
        listEmployee.add(employee2);
        listEmployee.add(employee3);
        instance.deleteFile(Employee.class);
        instance.insertPeople(Employee.class, listEmployee);
        assertEquals(listEmployee, instance.read(Employee.class));
    }

    @Test
    public void testReadFail() throws Exception{
        log.info("testReadFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteFile(Employee.class);
        assertNotNull(instance.read(Employee.class));
    }

    //update
    @Test
    public void testUpdateEmployeeSuccess() throws Exception{
        log.info("testUpdateSuccess");
        List<Employee> listEmployee = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee1 = createEmployee (1,"Иван","Иванович","Иванов","81234567890", "123456789012","1234567", EmployeeType.CHIEF);
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(2,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        listEmployee.add(employee1);
        listEmployee.add(employee2);
        instance.deleteFile(Employee.class);
        instance.insertPeople(Employee.class, listEmployee);
        instance.updatePeople(Employee.class, employee3);
        listEmployee.clear();
        listEmployee.add(employee1);
        listEmployee.add(employee3);
        assertEquals(listEmployee, instance.read(Employee.class));
    }

    @Test
    public void testUpdateEmployeeFail() throws Exception{
        log.info("testUpdateFail");
        List<Employee> listEmployee = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee1 = createEmployee(1,"Иван","Иванович","Иванов","81234567890", "123456789012","1234567", EmployeeType.CHIEF);
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        listEmployee.add(employee1);
        listEmployee.add(employee2);
        instance.deleteFile(Employee.class);
        instance.insertPeople(Employee.class, listEmployee);
        instance.updatePeople(Employee.class, employee3);
        assertEquals(listEmployee, instance.read(Employee.class));
    }

    @Test
    public void testUpdateMeetingSuccess() throws Exception{
        log.info("UpdateMeetingSuccess");
        List<Meeting> listMeeting = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Meeting meeting = createMeeting(1,"2020-12-15 11:55",true, false);
        listMeeting.add(meeting);
        instance.deleteFile(Meeting.class);
        instance.insertMeeting(listMeeting);
        Meeting meeting2 = createMeeting(1,"2020-12-15 11:55",false, false);
        instance.updateMeeting(meeting2);
        assertEquals(meeting2, instance.getMeetingByID(1));
    }

    @Test
    public void testUpdateMeetingFail() throws Exception{
        log.info("UpdateMeetingSuccess");
        List<Meeting> listMeeting = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Meeting meeting = createMeeting(1,"2020-12-15 11:55",true, false);
        listMeeting.add(meeting);
        instance.deleteFile(Meeting.class);
        instance.insertMeeting(listMeeting);
        Meeting meeting2 = createMeeting(2,"2020-12-15 11:55",false, false);
        instance.updateMeeting(meeting2);
        assertEquals(listMeeting, instance.read(Meeting.class));
    }

    @Test
    public void testUpdateBookSuccess() throws Exception{
        log.info("testUpdateBookSuccess");
        List<Book> listBook = new ArrayList<>();
        List<Author> listAuthor = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        Book book = createBook(1,author,"Цифровая бухгалтерия",4);
        listBook.add(book);
        listAuthor.add(author);
        instance.deleteFile(Author.class);
        instance.deleteFile(Book.class);
        instance.insertPeople(Author.class, listAuthor);
        instance.insertBook(listBook);
        Author author2 = createAuthor(12,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        Book book2 = createBook(1,author2,"Цифровая бухгалтерия",4);
        listAuthor.clear();
        listAuthor.add(author2);
        instance.insertPeople(Author.class, listAuthor);
        instance.updateBook(book2);
        assertEquals(book2, instance.getBookByID(Book.class,1));
    }

    @Test
    public void testUpdateBookFail() throws Exception{
        log.info("testUpdateBookFail");
        List<Book> listBook = new ArrayList<>();
        List<Author> listAuthor = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        Book book = createBook(1,author,"Цифровая бухгалтерия",4);
        listBook.add(book);
        listAuthor.add(author);
        instance.deleteFile(Author.class);
        instance.deleteFile(Book.class);
        instance.insertPeople(Author.class, listAuthor);
        instance.insertBook(listBook);
        Author author2 = createAuthor(12,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        Book book2 = createBook(1,author2,"Цифровая бухгалтерия",4);
        instance.updateBook(book2);
        assertEquals(book, instance.getBookByID(Book.class,1));
    }

    //delete
    @Test
    public void testDeleteBookSuccess() throws Exception{
        log.info("testDeleteBookSuccess");
        List<Book> listBook = new ArrayList<>();
        List<Author> listAuthor = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        Book book = createBook(1,author,"Цифровая бухгалтерия",4);
        listBook.add(book);
        listAuthor.add(author);
        instance.deleteFile(Author.class);
        instance.deleteFile(Book.class);
        instance.insertPeople(Author.class, listAuthor);
        instance.insertBook(listBook);
        instance.deleteObj(Book.class, book);
        instance.deleteObj(Author.class, author);
        assertNull(instance.getBookByID(Book.class,1));
        assertNull(instance.getPeopleByID(Author.class,10));
    }

    @Test
    public void testDeleteBookFail() throws Exception{
        log.info("testDeleteBookFail");
        List<Book> listBook = new ArrayList<>();
        List<Author> listAuthor = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        Book book = createBook(1,author,"Цифровая бухгалтерия",4);
        listBook.add(book);
        listAuthor.add(author);
        instance.deleteFile(Author.class);
        instance.deleteFile(Book.class);
        instance.insertPeople(Author.class, listAuthor);
        instance.insertBook(listBook);
        instance.deleteObj(Author.class, author);
        instance.deleteObj(Book.class, book);
        assertEquals(author, instance.getPeopleByID(Author.class,10));
        assertNull(instance.getBookByID(Book.class,1));
    }

    @Test
    public void testGetMaxIdSuccess() throws Exception {
        log.info("testGetMaxIdSuccess");
        List<People> listPeople = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        People people1 = createPeople(1, "Иван", "Иванович", "Иванов", "81234567890");
        People people2 = createPeople(2, "Петр", "Петрович", "Петров", "82345678901");
        People people3 = createPeople(3, "Виктор", "Иванович", "Ткач", "83456789012");
        listPeople.add(people1);
        listPeople.add(people2);
        listPeople.add(people3);
        instance.deleteFile(People.class);
        instance.insertPeople(People.class, listPeople);
        assertEquals(3, instance.getMaxId(People.class));
    }

    @Test
    public void testGetMaxIdFail() throws Exception {
        log.info("testGetMaxIdFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteFile(People.class);
        assertEquals(-1, instance.getMaxId(People.class));
    }

    @Test
    public void alterBookInsertSuccess() throws Exception {
        log.info("alterBookInsertSuccess");
        List<Author> listAuthor = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        Book book = createBook(1,author,"Цифровая бухгалтерия",4);
        listAuthor.add(author);
        instance.deleteFile(Author.class);
        instance.deleteFile(Book.class);
        instance.insertPeople(Author.class, listAuthor);
        instance.alterBook(10, 1,"Цифровая бухгалтерия",4 );
        assertEquals(book, instance.getBookByID(Book.class,1));
    }

    @Test
    public void alterBookInsertFail() throws Exception {
        log.info("alterBookInsertFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteFile(Author.class);
        instance.deleteFile(Book.class);
        instance.alterBook(10, 1,"Цифровая бухгалтерия",4 );
        assertNull(instance.getBookByID(Book.class,1));
    }

    @Test
    public void makeOrderSuccess() throws Exception {
        log.info("alterBookInsertSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        List<Author> listAuthor = new ArrayList<>();
        List<Book> listBook = new ArrayList<>();
        instance.deleteFile(Book.class);
        instance.deleteFile(Author.class);
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        Book book = createBook(1,author,"Цифровая бухгалтерия",229);
        listAuthor.add(author);
        listBook.add(book);
        instance.insertPeople(Author.class, listAuthor);
        instance.insertBook(listBook);
        assertNotNull(instance.makeOrder(1, "2020-09-03", "RIGID_COVER", 100).orElse(null));
    }

    @Test
    public void makeOrderFail() throws Exception {
        log.info("makeOrderFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteFile(Book.class);
        assertNull(instance.makeOrder(1, "2020-09-03", "RIGID_COVER", 100).orElse(null));
    }

    @Test
    public void selectPriceParametersSuccess() throws Exception {
        log.info("selectPriceParametersSuccess");
        List<PriceParameters> listPriceParameters = new ArrayList<>();
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        CoverPrice coverPrice2 = createCoverPrice(2,CoverType.PAPERBACK, 143.8);
        listCoverPrice.add(coverPrice);
        listCoverPrice.add(coverPrice2);
        PriceParameters priceParameters = createPriceParameters(1, 3.4, listCoverPrice, 6.3, "2019-01-01", "2021-12-31");
        PriceParameters priceParameters2 = createPriceParameters(2, 5.1, listCoverPrice, 7.2, "2016-01-01", "2018-12-31");
        listPriceParameters.add(priceParameters);
        listPriceParameters.add(priceParameters2);
        instance.deleteFile(PriceParameters.class);
        instance.deleteFile(CoverPrice.class);
        instance.insertCoverPrice(listCoverPrice);
        instance.insertPriceParameters(listPriceParameters);
        assertEquals(priceParameters, instance.selectPriceParameters("2019-01-01").orElse(null));
    }

    @Test
    public void selectPriceParametersFail() throws Exception {
        log.info("selectPriceParametersFail");
        List<PriceParameters> listPriceParameters = new ArrayList<>();
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        CoverPrice coverPrice2 = createCoverPrice(2,CoverType.PAPERBACK, 143.8);
        listCoverPrice.add(coverPrice);
        listCoverPrice.add(coverPrice2);
        PriceParameters priceParameters = createPriceParameters(1, 3.4, listCoverPrice, 6.3, "2019-01-01", "2021-12-31");
        PriceParameters priceParameters2 = createPriceParameters(2, 5.1, listCoverPrice, 7.2, "2016-01-01", "2018-12-31");
        listPriceParameters.add(priceParameters);
        listPriceParameters.add(priceParameters2);
        instance.deleteFile(PriceParameters.class);
        instance.deleteFile(CoverPrice.class);
        instance.insertCoverPrice(listCoverPrice);
        instance.insertPriceParameters(listPriceParameters);
        assertNull(instance.selectPriceParameters("2007-01-01").orElse(null));
    }

    @Test
    public void takeAwayOrderSuccess() throws Exception{
        log.info("insertOrderSuccess");
        List<Order> listOrder = new ArrayList<>();
        List<Employee> listEmployee = new ArrayList<>();
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        List<PriceParameters> listPriceParameters = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        CoverPrice coverPrice = createCoverPrice(1, CoverType.RIGID_COVER, 123.5);
        CoverPrice coverPrice2 = createCoverPrice(2,CoverType.PAPERBACK, 143.8);
        listCoverPrice.add(coverPrice);
        listCoverPrice.add(coverPrice2);
        PriceParameters priceParameters = createPriceParameters(1, 2.4, listCoverPrice, 1.3, "2019-01-01", "2021-01-01");
        Order order = createOrder(1,author,"Цифровая бухгалтерия",4,"2020-09-03", CoverType.RIGID_COVER, employee2, employee3, priceParameters, 229, 100, 9700.75 , BookStatus.EDITING);
        listOrder.add(order);
        listEmployee.add(employee2);
        listEmployee.add(employee3);
        listPriceParameters.add(priceParameters);
        instance.deleteFile(Order.class);
        instance.deleteFile(Employee.class);
        instance.deleteFile(CoverPrice.class);
        instance.deleteFile(PriceParameters.class);
        instance.deleteFile(Corrections.class);
        instance.insertPeople(Employee.class, listEmployee);
        instance.insertCoverPrice(listCoverPrice);
        instance.insertPriceParameters(listPriceParameters);
        instance.insertOrder(listOrder);
        instance.takeAwayOrder(1);
        assertNull(instance.getBookByID(Order.class, 1));
    }

    @Test
    public void takeAwayOrderFail() throws Exception{
        log.info("takeAwayOrderFail");
        List<Corrections> listCorrections = new ArrayList<>();
        List<Employee> listEmployee = new ArrayList<>();
        List<Author> listAuthor = new ArrayList<>();
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        List<PriceParameters> listPriceParameters = new ArrayList<>();
        List<Order> listOrder = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        CoverPrice coverPrice2 = createCoverPrice(2,CoverType.PAPERBACK, 143.8);
        listCoverPrice.add(coverPrice);
        listCoverPrice.add(coverPrice2);
        PriceParameters priceParameters = createPriceParameters(1, 2.4, listCoverPrice, 1.3, "2019-01-01", "2021-01-01");
        Order order = createOrder(1,author,"Цифровая бухгалтерия",4,"2020-09-03", CoverType.RIGID_COVER, employee2, employee3, priceParameters, 229, 100, 9700.75 , BookStatus.EDITING  );
        Corrections corrections = createCorrections(1,35, "Цифровой контроль - это компьютерные системы",
                "Цифровой контроль представляет собой компьютерные системы", "Повторяется конструкция", order, null, CorrectionsStatus.WAIT_AUTHOR_AGR );
        listEmployee.add(employee2);
        listEmployee.add(employee3);
        listAuthor.add(author);
        listPriceParameters.add(priceParameters);
        listOrder.add(order);
        listCorrections.add(corrections);
        instance.deleteFile(Employee.class);
        instance.deleteFile(Author.class);
        instance.deleteFile(PriceParameters.class);
        instance.deleteFile(Order.class);
        instance.deleteFile(Corrections.class);
        instance.insertPeople(Employee.class, listEmployee);
        instance.insertPeople(Author.class, listAuthor);
        instance.insertCoverPrice(listCoverPrice);
        instance.insertPriceParameters(listPriceParameters);
        instance.insertOrder(listOrder);
        instance.insertCorrections(listCorrections);
        instance.takeAwayOrder(1);
        assertNotNull(instance.getBookByID(Order.class, 1));
    }

    @Test
    public void getListOfCorrectionsSuccess() throws Exception {
        log.info("getListOfCorrectionsSuccess");
        List<Corrections> listCorrections = new ArrayList<>();
        List<Employee> listEmployee = new ArrayList<>();
        List<Author> listAuthor = new ArrayList<>();
        List<CoverPrice> listCoverPrice = new ArrayList<>();
        List<PriceParameters> listPriceParameters = new ArrayList<>();
        List<Order> listOrder = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Employee employee2 = createEmployee(2,"Петр","Петрович","Петров","82345678901","234567890123", "2345678", EmployeeType.MAKER);
        Employee employee3 = createEmployee(3,"Виктор","Иванович","Ткач","83456789012", "345678901234", "3456789", EmployeeType.EDITOR);
        Author author = createAuthor(10,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        CoverPrice coverPrice = createCoverPrice(1,CoverType.RIGID_COVER, 123.5);
        CoverPrice coverPrice2 = createCoverPrice(2,CoverType.PAPERBACK, 143.8);
        listCoverPrice.add(coverPrice);
        listCoverPrice.add(coverPrice2);
        PriceParameters priceParameters = createPriceParameters(1, 2.4, listCoverPrice, 1.3, "2019-01-01", "2021-01-01");
        Order order = createOrder(1,author,"Цифровая бухгалтерия",4,"2020-09-03", CoverType.RIGID_COVER, employee2, employee3, priceParameters, 229, 100, 9700.75 , BookStatus.EDITING  );
        Corrections corrections = createCorrections(1,35, "Цифровой контроль - это компьютерные системы",
                "Цифровой контроль представляет собой компьютерные системы", "Повторяется конструкция", order, null, CorrectionsStatus.WAIT_AUTHOR_AGR );
        Corrections corrections2 = createCorrections(2,65, "Цифровой контроль - это компьютерные системы",
                "Цифровой контроль представляет собой компьютерные системы", "Повторяется конструкция", order, null, CorrectionsStatus.WAIT_AUTHOR_AGR );
        listEmployee.add(employee2);
        listEmployee.add(employee3);
        listAuthor.add(author);
        listPriceParameters.add(priceParameters);
        listOrder.add(order);
        listCorrections.add(corrections);
        listCorrections.add(corrections2);
        instance.deleteFile(Employee.class);
        instance.deleteFile(Author.class);
        instance.deleteFile(PriceParameters.class);
        instance.deleteFile(Order.class);
        instance.deleteFile(Corrections.class);
        instance.insertPeople(Employee.class, listEmployee);
        instance.insertPeople(Author.class, listAuthor);
        instance.insertCoverPrice(listCoverPrice);
        instance.insertPriceParameters(listPriceParameters);
        instance.insertOrder(listOrder);
        instance.insertCorrections(listCorrections);
        //assertEquals(listCorrections, instance.getListOfCorrections());
    }

    @Test
    public void getListOfCorrectionsFail() throws Exception {
        log.info("getListOfCorrectionsFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteFile(Corrections.class);
        //assertTrue(instance.getListOfCorrections().isEmpty());
    }



}