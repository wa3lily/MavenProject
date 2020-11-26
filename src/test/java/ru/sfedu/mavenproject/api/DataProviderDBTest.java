package ru.sfedu.mavenproject.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.mavenproject.TestBase;
import ru.sfedu.mavenproject.bean.People;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DataProviderDBTest extends TestBase {

    private static Logger log = LogManager.getLogger(DataProviderDBTest.class);

    @Test
    public void testInsertPeopleSuccess() throws Exception{
        log.info("insertPeopleSuccess");
        DataProviderDB instance = new DataProviderDB();
        People people = createPeople(1,"Иван","Иванович","Иванов","81234567890");
        instance.insertPeople(people);
        assertEquals(people, instance.getPeopleById(1));
    }

    @Test
    public void testInsertPeopleFalse() throws Exception{
        log.info("insertPeopleFalse");
        DataProviderDB instance = new DataProviderDB();
        People people = createPeople(1,"Иван","Иванович","Иванов","81234567890");
        instance.insertPeople(people);
        assertNull(instance.getPeopleById(100));
    }
}