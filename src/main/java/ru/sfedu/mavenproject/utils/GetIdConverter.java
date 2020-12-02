package ru.sfedu.mavenproject.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.bean.Author;
import ru.sfedu.mavenproject.bean.Book;

public class GetIdConverter extends AbstractBeanField<Author, Integer> {
    private static Logger log = LogManager.getLogger(GetIdConverter.class);
    @Override
    protected Object convert(String s){
        Author author = new Author();
        author.setId(Long.parseLong(s));
        return author;
    }

    public String convertToWrite(Object value){
        String res="";
        Author author = (Author) value;
        res += author.getId();
        return res;
    }
}
