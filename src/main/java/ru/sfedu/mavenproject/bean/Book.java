package ru.sfedu.mavenproject.bean;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.mavenproject.utils.GetIdConverter;


import java.io.Serializable;
import java.util.Objects;

/**
 * Class Book
 */
public class Book implements Serializable {

  @CsvBindByName
  private long id;
  @CsvCustomBindByName(converter = GetIdConverter.class)
  private Author author;
  @CsvBindByName
  private String title;
  @CsvBindByName
  private int numberOfPages;

  public Book () { };

  public void setId (long newVar) {
    id = newVar;
  }

  public long getId () {
    return id;
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
            //Objects.equals(author, book.author) &&
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
