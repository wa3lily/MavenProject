package ru.sfedu.mavenproject;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.api.DataProvider;
import ru.sfedu.mavenproject.api.DataProviderCSV;
import ru.sfedu.mavenproject.api.DataProviderDB;
import ru.sfedu.mavenproject.api.DataProviderXML;
import ru.sfedu.mavenproject.bean.People;
import ru.sfedu.mavenproject.utils.ConfigurationUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.sfedu.mavenproject.Constants.ENV_CONST;
import static ru.sfedu.mavenproject.utils.ConfigurationUtil.getConfigurationEntry;

public class Main {

    private static Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            ConfigurationUtil.getConfigurationEntry(Constants.CONFIG_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataProvider dataProvider;
        if (args[0].equals("Csv")) {
            dataProvider = new DataProviderCSV();
        } else if (args[0].equals("Xml")) {
            dataProvider = new DataProviderXML();
        } else if (args[0].equals("Jdbc")) {
            dataProvider = new DataProviderDB();
        } else dataProvider = null;

//        System.out.println(getAnswer(dataProvider, args));
    }

//    public static String getAnswer(DataProvider dataProvider, String[] args) {
//        try {
//            switch (CliCommand.valueOf(args[1].toUpperCase())) {
//                case GETTASKBYID:
//
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
