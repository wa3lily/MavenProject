package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import ru.sfedu.mavenproject.converters.ConverterAuthor;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class Book
 */
public class Book implements Serializable {

  @Attribute
  @CsvBindByName
  long id;
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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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
    Book book = (Book) o;
    return id == book.id &&
            numberOfPages == book.numberOfPages &&
            author.getId() == book.author.getId() &&
            Objects.equals(title, book.title);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(id, author, title, numberOfPages);
    return result;
  }

  @Override
  public String toString() {
    return "Book{" +
            "id=" + id +
            ", author=" + author.getId() +
            ", title='" + title + '\'' +
            ", numberOfPages=" + numberOfPages +
            '}';
  }
}
