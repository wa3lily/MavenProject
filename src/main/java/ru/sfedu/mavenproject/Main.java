package ru.sfedu.mavenproject;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static Logger log = LogManager.getLogger(Main.class);

    static public void main(){
        log.info("Info_test");
        log.debug("Debag_test");
        log.error("Error_test");
    }
}
