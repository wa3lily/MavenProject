package ru.sfedu.mavenproject.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import ru.sfedu.mavenproject.TestBase;
import ru.sfedu.mavenproject.bean.Author;
import ru.sfedu.mavenproject.bean.Book;
import ru.sfedu.mavenproject.bean.People;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderCSVTest extends TestBase {

    private static Logger log = LogManager.getLogger(DataProviderCSVTest.class);

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
    public void testInsertPeopleFalse() throws Exception{
        log.info("insertPeopleFalse");
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
    public void testInsertAuthorFalse() throws Exception{
        log.info("insertAuthorFalse");
        List<Author> listAuthor = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(1,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        listAuthor.add(author);
        instance.insert(Author.class, listAuthor);
        assertNull(instance.getByID(Author.class, 2));
    }

    @Test
    public void testInsertBookSuccess() throws Exception{
        log.info("insertBookSuccess");
        List<Book> listBook = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Author author = createAuthor(1,"Виктор","Иванович","Ткач","83456789012", "tkach@gmail.com", "docent", "Donstu");
        Book book = createBook(1,author,"Цифровая бухгалтерия","F:\\Digital_account",4);
        listBook.add(book);
        instance.insert(Book.class, listBook);
        assertEquals(book, instance.getByID(Book.class,1));

    }
}