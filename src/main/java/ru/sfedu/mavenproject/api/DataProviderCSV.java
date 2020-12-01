package ru.sfedu.mavenproject.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.bean.Author;
import ru.sfedu.mavenproject.bean.Book;
import ru.sfedu.mavenproject.bean.People;
import ru.sfedu.mavenproject.utils.ConfigurationUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;

import static ru.sfedu.mavenproject.Constants.PATH;
import static ru.sfedu.mavenproject.Constants.FILE_EXTENSION;

public class DataProviderCSV {

    private static Logger log = LogManager.getLogger(DataProviderCSV.class);

    public FileWriter commonWriter(Class cl) throws IOException {
        FileWriter writer = new FileWriter(getPath(cl));
        return writer;
    }

    public <T> List<T> select(Class cl) throws IOException{
        FileReader fileReader;
        String path = getPath(cl);
        try{
            fileReader = new FileReader(path);
        }catch (FileNotFoundException e){
            createFile(path);
            fileReader = new FileReader(path);
        }
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                .withType(cl)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<T> list = csvToBean.parse();
        return list;
    }

    public String getPath(Class cl) throws IOException {
        return ConfigurationUtil.getConfigurationEntry(PATH)
                + cl.getSimpleName().toLowerCase()
                + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION);
    }

    public void insertPeople(List<People> listPeople) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try {
            CSVWriter csvWriter = new CSVWriter(commonWriter(People.class));
            StatefulBeanToCsv<People> beanToCsv = new StatefulBeanToCsvBuilder<People>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listPeople);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
    }

    public void insertAuthor(List<Author> listAuthor) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try {
            CSVWriter csvWriter = new CSVWriter(commonWriter(Author.class));
            StatefulBeanToCsv<Author> beanToCsv = new StatefulBeanToCsvBuilder<Author>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listAuthor);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
    }

    public People getPeopleById(long id) throws  IOException{
        List<People> listPeople = select(People.class);
        try {
            People people = listPeople.stream()
                    .filter(e1 -> e1.getId() == id)
                    .findFirst().get();
            return people;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    public <T extends People,Book,Corrections,Meeting> Object getByID(Class cl, long id) throws IOException {
        List<T> list = this.select(cl);
        try {
            Object obj = list.stream()
                    .filter(e1 -> e1.getId() == id)
                    .findFirst().get();
            return obj;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    public String createFile(String path) throws IOException {
        File file = new File(path);
        file.createNewFile();
        return path;
    }

    public <T> void insert(Class cl, List<T> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try {
            CSVWriter csvWriter = new CSVWriter(commonWriter(cl));
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(list);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
    }



}
