package ru.sfedu.mavenproject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static ru.sfedu.mavenproject.Constants.ENV_CONST;
import static ru.sfedu.mavenproject.utils.ConfigurationUtil.getConfigurationEntry;

public class Main {

    private static Logger log = LogManager.getLogger(Main.class);

    static public void main(String[] args) throws IOException {
        log.info("Info_test");
        log.debug("Debag_test");
        log.error("Error_test");
        log.info(Constants.TEST_CONST);
        System.out.println(getConfigurationEntry(ENV_CONST));
    }
}
