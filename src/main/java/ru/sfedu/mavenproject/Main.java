package ru.sfedu.mavenproject;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.api.DataProvider;
import ru.sfedu.mavenproject.api.DataProviderCSV;
import ru.sfedu.mavenproject.api.DataProviderDB;
import ru.sfedu.mavenproject.api.DataProviderXML;
import ru.sfedu.mavenproject.bean.CoverPrice;
import ru.sfedu.mavenproject.bean.enums.Commands;
import ru.sfedu.mavenproject.bean.enums.CoverType;
import ru.sfedu.mavenproject.utils.ConfigurationUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            ConfigurationUtil.getConfigurationEntry(Constants.CONFIG_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataProvider dataProvider;
        if (args[0].equals("csv")) {
            dataProvider = new DataProviderCSV();
        } else if (args[0].equals("xml")) {
            dataProvider = new DataProviderXML();
        } else if (args[0].equals("jdbc")) {
            dataProvider = new DataProviderDB();
        } else dataProvider = null;

        System.out.println(getAnswer(dataProvider, args));
    }

    public static String getAnswer(DataProvider dataProvider, String[] args) {
        try {
            switch (Commands.valueOf(args[1].toUpperCase())) {
                case ADDAUTHOR:
                   return String.valueOf(dataProvider.addAuthor(Long.parseLong(args[2]),args[3],args[4],args[5],args[6],args[7],args[8],args[9]));
                case CALCULATECOST:
                    dataProvider.addCoverPrice(1,"RIGID_COVER", 123.5);
                    CoverPrice coverPrice = dataProvider.setCoverPrice(1,"RIGID_COVER", 123.5).orElse(null);
                    List<CoverPrice> list = new ArrayList<>();
                    list.add(coverPrice);
                    dataProvider.addAuthor(1,"Иван","Иванович","Иванов","81234567890","iivanov@mail.ru","доцент","ЮФУ");
                    dataProvider.alterBook(1,10,"Цифровая бухгалтерия",288);
                    dataProvider.addPriceParameters(1, 13.4, list, 16.3, "2019-01-01", "2021-01-01");
                    dataProvider.saveOrderInformation (10, "2019-07-01", "RIGID_COVER", 100);
                    dataProvider.calculateCost(Long.parseLong(args[2]));
                case COUNTPUBLISHEDBOOK:
                    dataProvider.addAuthor(1,"Иван","Иванович","Иванов","81234567890","iivanov@mail.ru","доцент","ЮФУ");
                    dataProvider.alterBook(1,10,"Цифровая бухгалтерия",288);
                    dataProvider.saveOrderInformation (10, "2019-07-01", "RIGID_COVER", 100);
                    dataProvider.alterBook(1,12,"Цифровая бухгалтерия",288);
                    dataProvider.saveOrderInformation (12, "2020-07-01", "PAPERBACK", 200);
                    dataProvider.countPublishedBooks(args[2],args[3]);
                default:
                    return Constants.WRONG_COMMAND;
            }
        } catch (Exception e) {
            log.error(e);
            return Constants.WRONG_COMMAND;
        }
    }
}
