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
import ru.sfedu.mavenproject.bean.People;
import ru.sfedu.mavenproject.utils.ConfigurationUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static ru.sfedu.mavenproject.Constants.PATH;
import static ru.sfedu.mavenproject.Constants.FILE_EXTENSION;

public class DataProviderCSV {

    private static Logger log = LogManager.getLogger(DataProviderCSV.class);

    public void insertPeople(List<People> listPeople) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try {
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(PATH)
                    + listPeople.get(0).getClass().getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
            CSVWriter csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<People> beanToCsv = new StatefulBeanToCsvBuilder<People>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listPeople);
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

    public <T> List<T> select(Class cl) throws IOException{
        FileReader fileReader = new FileReader(ConfigurationUtil.getConfigurationEntry(PATH)
                + cl.getSimpleName().toLowerCase()
                + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                .withType(cl)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<T> list = csvToBean.parse();
        return list;
    }
}
