package ru.sfedu.mavenproject;

import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private static Logger log = LogManager.getLogger(MainTest.class);

    @Test
    void main() {
        log.info("main");
        log.info(Constants.TEST_CONST);
        log.info("Good_test");
        //fail("ddd");
    }
}