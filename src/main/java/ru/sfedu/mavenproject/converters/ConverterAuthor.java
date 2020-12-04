package ru.sfedu.mavenproject.converters;

import com.opencsv.bean.AbstractBeanField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.bean.Author;

public class ConverterAuthor extends AbstractBeanField<Author, Integer> {
    private static Logger log = LogManager.getLogger(ConverterAuthor.class);
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
