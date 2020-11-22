package ru.sfedu.mavenproject.api;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.Main;
import ru.sfedu.mavenproject.bean.Author;
import ru.sfedu.mavenproject.bean.People;
import ru.sfedu.mavenproject.utils.ConfigurationUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static ru.sfedu.mavenproject.Constants.PATH;
import static ru.sfedu.mavenproject.Constants.FILE_EXTENSION;

public class DataProviderCSV {

    private static Logger log = LogManager.getLogger(DataProviderCSV.class);

    static public void insertPeople(List<People> listPeople) throws IOException {
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
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }
}
