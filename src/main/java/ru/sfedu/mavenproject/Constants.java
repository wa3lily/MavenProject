package ru.sfedu.mavenproject;

public class Constants {
    static public final int TEST_CONST=33;
    static public final String ENV_CONST="source";
    static public final String FORMAT_CONST="There are constants %d and %s";
    static public final String PATH = "csv_path";
    static public final String FILE_EXTENSION = "csv";
    static public final String DB_DRIVER="db_driver";
    static public final String DB_USER="db_user";
    static public final String DB_PASS="db_pass";
    static public final String DB_URL="db_url";
    static public final String DB_INSERT="INSERT INTO %s(%s) VALUES(%s)";
    static public final String DB_SELECT="SELECT * FROM %s WHERE id = %d";
    static public final String ID="id";
    static public final String PEOPLE_FIRST_NAME="firstName";
    static public final String PEOPLE_SECOND_NAME="secondName";
    static public final String PEOPLE_LAST_NAME="lastName";
    static public final String PEOPLE_PHONE="phone";
    static public final String PEOPLE_FIELDS=PEOPLE_FIRST_NAME+","+PEOPLE_SECOND_NAME+","+PEOPLE_LAST_NAME+","+PEOPLE_PHONE;
    static public final String PEOPLE_INSERT_FORMAT="'%s','%s','%s','%s'";
}
