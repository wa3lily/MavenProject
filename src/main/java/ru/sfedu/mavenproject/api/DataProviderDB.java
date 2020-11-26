package ru.sfedu.mavenproject.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.util.PSQLException;
import ru.sfedu.mavenproject.Constants;
import ru.sfedu.mavenproject.bean.People;
import ru.sfedu.mavenproject.utils.ConfigurationUtil;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.NoSuchElementException;
import static ru.sfedu.mavenproject.Constants.DB_DRIVER;
import static ru.sfedu.mavenproject.Constants.DB_URL;
import static ru.sfedu.mavenproject.Constants.DB_USER;
import static ru.sfedu.mavenproject.Constants.DB_PASS;
import static ru.sfedu.mavenproject.Constants.DB_INSERT;
import static ru.sfedu.mavenproject.Constants.DB_SELECT;
import static ru.sfedu.mavenproject.Constants.ID;
import static ru.sfedu.mavenproject.Constants.PEOPLE_FIELDS;
import static ru.sfedu.mavenproject.Constants.PEOPLE_FIRST_NAME;
import static ru.sfedu.mavenproject.Constants.PEOPLE_SECOND_NAME;
import static ru.sfedu.mavenproject.Constants.PEOPLE_LAST_NAME;
import static ru.sfedu.mavenproject.Constants.PEOPLE_PHONE;
import static ru.sfedu.mavenproject.Constants.PEOPLE_INSERT_FORMAT;
import static ru.sfedu.mavenproject.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderDB {

    private static Logger log = LogManager.getLogger(DataProviderDB.class);
    private Connection connection;

    public void insertPeople(People people) throws SQLException, IOException, ClassNotFoundException {
        this.execute(
                String.format(
                        DB_INSERT,
                        people.getClass().getSimpleName().toLowerCase(),
                        PEOPLE_FIELDS,
                        String.format(
                                PEOPLE_INSERT_FORMAT,people.getFirstName(),people.getSecondName(),people.getLastName(),people.getPhone()
                        )
                )
        );

    }

    public People getPeopleById(long id) throws SQLException, IOException, ClassNotFoundException {
        ResultSet set = getResultSet(String.format(DB_SELECT, People.class.getSimpleName().toLowerCase(),id));
        try {
            set.next();
            People people = new People();
            people.setId(set.getLong(ID));
            people.setFirstName(set.getString(PEOPLE_FIRST_NAME));
            people.setSecondName(set.getString(PEOPLE_SECOND_NAME));
            people.setLastName(set.getString(PEOPLE_LAST_NAME));
            people.setPhone(set.getString(PEOPLE_PHONE));
            return people;
        }catch (PSQLException e){
            log.error(e);
            return null;
        }
    }

    private Connection connection() throws IOException, ClassNotFoundException, SQLException {
        Class.forName(getConfigurationEntry(DB_DRIVER));
        return DriverManager.getConnection(
                getConfigurationEntry(DB_URL),
                getConfigurationEntry(DB_USER),
                getConfigurationEntry(DB_PASS)
        );
    }

    private void execute(String sql) throws SQLException, IOException, ClassNotFoundException {
        log.debug(sql);
        PreparedStatement statement = connection().prepareStatement(sql);
        statement.executeUpdate();
        statement.close();
    }

    private ResultSet getResultSet (String sql) throws SQLException, IOException, ClassNotFoundException {
        log.debug(sql);
        PreparedStatement statement = connection().prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        statement.close();
        return set;
    }
}
