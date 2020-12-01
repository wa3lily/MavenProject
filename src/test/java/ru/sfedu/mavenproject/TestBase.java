package ru.sfedu.mavenproject;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.mavenproject.bean.Author;
import ru.sfedu.mavenproject.bean.Book;
import ru.sfedu.mavenproject.bean.People;

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

    public Book createBook (long id, Author author, String title, String pathFileOriginal, int numberOfPages){
        Book book = new Book();
        book.setId(id);
        book.setAuthor(author);
        book.setTitle(title);
        book.setPathFileOriginal(pathFileOriginal);
        book.setNumberOfPages(numberOfPages);
        return book;
    }


}
