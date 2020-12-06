package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import ru.sfedu.mavenproject.ClassId;
import ru.sfedu.mavenproject.converters.ConverterAuthor;
import java.util.Objects;

/**
 * Class Book
 */
public class Book extends ClassId {

  @Element
  @CsvCustomBindByName(converter = ConverterAuthor.class)
  private Author author;
  @Element
  @CsvBindByName
  private String title;
  @Element
  @CsvBindByName
  private int numberOfPages;

  public Book () { };

  public void setAuthor (Author newVar) {
    author = newVar;
  }

  public Author getAuthor () {
    return author;
  }

  public void setTitle (String newVar) {
    title = newVar;
  }

  public String getTitle () {
    return title;
  }

  public void setNumberOfPages (int newVar) {
    numberOfPages = newVar;
  }

  public int getNumberOfPages () {
    return numberOfPages;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Book book = (Book) o;
    return numberOfPages == book.numberOfPages &&
            //Objects.equals(author, book.author) &&
            author.getId() == book.author.getId() &&
            Objects.equals(title, book.title);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(super.hashCode(), author, title, numberOfPages);
    return result;
  }

  @Override
  public String toString() {
    return "Book{" +
            "id=" + super.getId() +
            ", author=" + author.getId() +
            ", title='" + title + '\'' +
            ", numberOfPages=" + numberOfPages +
            '}';
  }
}
