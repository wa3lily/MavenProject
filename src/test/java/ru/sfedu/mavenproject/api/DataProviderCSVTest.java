package ru.sfedu.mavenproject.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.mavenproject.TestBase;
import ru.sfedu.mavenproject.bean.People;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderCSVTest extends TestBase {

    private static Logger log = LogManager.getLogger(DataProviderCSVTest.class);

    @Test
    public void testInsertPeopleSuccess() throws Exception{
        log.info("insertPeopleSuccess");
        List<People> listPeople = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        People people1 = createPeople(1,"Иван","Иванович","Иванов","81234567890");
        People people2 = createPeople(2,"Петр","Петрович","Петров","82345678901");
        People people3 = createPeople(3,"Виктор","Иванович","Ткач","83456789012");
        listPeople.add(people1);
        listPeople.add(people2);
        listPeople.add(people3);
        instance.insertPeople(listPeople);
        assertEquals(people2, instance.getPeopleById(2));
    }

    @Test
    public void testInsertPeopleFalse() throws Exception{
        log.info("insertPeopleFalse");
        List<People> listPeople = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        People people1 = createPeople(1,"Иван","Иванович","Иванов","81234567890");
        People people2 = createPeople(2,"Петр","Петрович","Петров","82345678901");
        People people3 = createPeople(3,"Виктор","Иванович","Ткач","83456789012");
        listPeople.add(people1);
        listPeople.add(people2);
        listPeople.add(people3);
        instance.insertPeople(listPeople);
        assertNull(instance.getPeopleById(4));
    }
}