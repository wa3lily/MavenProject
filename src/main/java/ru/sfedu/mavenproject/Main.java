package ru.sfedu.mavenproject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.api.DataProviderCSV;
import ru.sfedu.mavenproject.bean.People;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.sfedu.mavenproject.Constants.ENV_CONST;
import static ru.sfedu.mavenproject.utils.ConfigurationUtil.getConfigurationEntry;

public class Main {

    private static Logger log = LogManager.getLogger(Main.class);

    static public void main(String[] args) throws IOException {
        log.info("Info_test");
        log.debug("Debag_test");
        log.error("Error_test");
        log.info(Constants.TEST_CONST);
        log.info(getConfigurationEntry(ENV_CONST));
        log.info(String.format(Constants.FORMAT_CONST, Constants.TEST_CONST, ENV_CONST));

        People people = new People();
        people.setId(1);
        people.setFirstName("Иван");
        people.setSecondName("Иванович");
        people.setLastName("Иванов");
        people.setPhone("81234567890");

        List<People> listPeople = new ArrayList<>();
        listPeople.add(people);

        DataProviderCSV providerCSV = new DataProviderCSV();
        providerCSV.insertPeople(listPeople);

    }
}
