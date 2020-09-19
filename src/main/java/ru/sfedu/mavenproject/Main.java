package ru.sfedu.mavenproject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static Logger log = LogManager.getLogger(Main.class);

    static public void main(String[] args){
        log.info("Info_test");
        log.debug("Debag_test");
        log.error("Error_test");
        log.info(Constants.TEST_CONST);
    }
}
