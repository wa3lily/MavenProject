package ru.sfedu.mavenproject;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.mavenproject.bean.People;

public class TestBase {

    public People createPeople (long id, String firstName, String secondName, String lastName, String phone){
        People people = new People();
        people.setId(id);
        people.setFirstName(firstName);
        people.setSecondName(secondName);
        people.setLastName(lastName);
        people.setPhone(phone);
        return people;
    }
}
