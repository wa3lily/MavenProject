package ru.sfedu.mavenproject;

public class Constants {
    static public final int TEST_CONST=33;
    static public final String ENV_CONST="source";
    static public final String FORMAT_CONST="There are constants %d and %s";

    static public final String PATH_CSV = "csv_path";
    static public final String FILE_EXTENSION_CSV = "csv";

    static public final String DB_DRIVER="db_driver";
    public static final String DB_CONNECT = "db_url";
    static public final String DB_USER="db_user";
    static public final String DB_PASS="db_pass";
    static public final String DB_URL="db_url";

    static public final String DB_INSERT="INSERT INTO \"%s\"(%s) VALUES(%s)";
    static public final String DB_SELECT_BY_ID="SELECT * FROM \"%s\" WHERE id = %d";
    static public final String DB_SELECT_ALL="SELECT * FROM \"%s\"";
    static public final String DB_SELECT_WHERE="SELECT * FROM %s WHERE %s";
    static public final String DB_UPDATE="UPDATE \"%s\" SET %s WHERE id = %d";
    static public final String DB_TRUNCATE="TRUNCATE TABLE \"%s\"";
    static public final String DB_DROP="DROP TABLE IF EXISTS \"%s\"";
    static public final String DB_DELETE_BY_ID="DELETE FROM %s WHERE id = %d";

    static public final String CREATE_PEOPLE="DROP TABLE IF EXISTS people; " +
            "CREATE TABLE people (" +
            "id bigint serial Primary Key," +
            "firstName varchar(40)," +
            "secondName varchar(40)," +
            "lastName varchar(40)," +
            "phone varchar(11)" +
            ") ";

    static public final String ID="id";
    static public final String PEOPLE_FIRST_NAME="firstName";
    static public final String PEOPLE_SECOND_NAME="secondName";
    static public final String PEOPLE_LAST_NAME="lastName";
    static public final String PEOPLE_PHONE="phone";
    static public final String PEOPLE_FIELDS=ID+","+PEOPLE_FIRST_NAME+","+PEOPLE_SECOND_NAME+","+PEOPLE_LAST_NAME+","+PEOPLE_PHONE;
    static public final String PEOPLE_INSERT_FORMAT="%d,'%s','%s','%s','%s'";

    static public final String CREATE_AUTHOR="DROP TABLE IF EXISTS author; " +
            "CREATE TABLE author " +
            "(id bigint serial Primary Key," +
            "firstName varchar(40)," +
            "secondName varchar(40)," +
            "lastName varchar(40)," +
            "phone varchar(11)," +
            "email varchar(40)," +
            "degree varchar(40)," +
            "organization varchar(40)" +
            ") ";

    static public final String AUTHOR_EMAIL="email";
    static public final String AUTHOR_DEGREE="degree";
    static public final String AUTHOR_ORGANIZATION="organization";
    static public final String AUTHOR_FIELDS=ID+","+PEOPLE_FIRST_NAME+","+PEOPLE_SECOND_NAME+","+PEOPLE_LAST_NAME +","+PEOPLE_PHONE+","
            +AUTHOR_EMAIL+","+AUTHOR_DEGREE+","+AUTHOR_ORGANIZATION;
    static public final String AUTHOR_INSERT_FORMAT="%d,'%s','%s','%s','%s','%s','%s','%s'";

    static public final String CREATE_EMPLOYEE="DROP TABLE IF EXISTS employee; " +
            "CREATE TABLE employee " +
            "(id bigint serial Primary Key," +
            "firstName varchar(40)," +
            "secondName varchar(40)," +
            "lastName varchar(40)," +
            "phone varchar(11)," +
            "inn varchar(12)," +
            "workRecordBook varchar(7)," +
            "employeeType varchar(7)" +
            ") ";

    static public final String EMPLOYEE_INN="inn";
    static public final String EMPLOYEE_WORK_RECORD_BOOK="workRecordBook";
    static public final String EMPLOYEE_TYPE="employeeType";
    static public final String EMPLOYEE_FIELDS=ID+","+PEOPLE_FIRST_NAME+","+PEOPLE_SECOND_NAME+","+PEOPLE_LAST_NAME+","+PEOPLE_PHONE+","
            +EMPLOYEE_INN+","+EMPLOYEE_WORK_RECORD_BOOK+","+EMPLOYEE_TYPE;
    static public final String EMPLOYEE_INSERT_FORMAT="%d,'%s','%s','%s','%s','%s','%s','%s'";

    static public final String CREATE_MEETING="DROP TABLE IF EXISTS meeting; " +
            "CREATE TABLE meeting " +
            "( id bigint serial Primary Key," +
            "meetDate varchar(20)," +
            "authorAgreement boolean," +
            "editorAgreement boolean" +
            ") ";

    static public final String MEETING_MEET_DATE="meetDate";
    static public final String MEETING_AUTHOR_AGREEMENT="authorAgreement";
    static public final String MEETING_EDITOR_AGREEMENT="editorAgreement";
    static public final String MEETING_FIELDS=ID+","+MEETING_MEET_DATE+","+MEETING_AUTHOR_AGREEMENT+","+MEETING_EDITOR_AGREEMENT;
    static public final String MEETING_INSERT_FORMAT="%d,'%s',%b,%b";

    static public final String CREATE_COVERPRICE="DROP TABLE IF EXISTS coverprice; " +
            "CREATE TABLE coverprice " +
            "(id bigint serial Primary Key," +
            "coverType varchar(30)," +
            "price numeric(4)" +
            ") ";

    static public final String COVERPRICE_COVER_TYPE="coverType";
    static public final String COVERPRICE_PRICE="price";
    static public final String COVERPRICE_FIELDS=ID+","+COVERPRICE_COVER_TYPE+","+COVERPRICE_PRICE;
    static public final String COVERPRICE_INSERT_FORMAT="%d,'%s',%f";

    static public final String CREATE_PRICEPARAMETERS="DROP TABLE IF EXISTS priceparameters; " +
            "CREATE TABLE priceparameters " +
            "(id bigint serial Primary Key," +
            "pagePrice numeric(4)," +
            "coverPrice bigint," +
            "workPrice numeric(4)," +
            "validFromDate varchar(20)," +
            "validToDate varchar(20)" +
            ") ";

    static public final String PRICEPARAMETERS_PAGE_PRICE="pagePrice";
    static public final String PRICEPARAMETERS_COVER_PRICE="coverPrice";
    static public final String PRICEPARAMETERS_WORK_PRICE="workPrice";
    static public final String PRICEPARAMETERS_VALID_FROM_DATE="validFromDate";
    static public final String PRICEPARAMETERS_VALID_TO_DATE="validToDate";
    static public final String PRICEPARAMETERS_FIELDS=ID+","+PRICEPARAMETERS_PAGE_PRICE+","+PRICEPARAMETERS_COVER_PRICE+","
            +PRICEPARAMETERS_WORK_PRICE+","+PRICEPARAMETERS_VALID_FROM_DATE+","+PRICEPARAMETERS_VALID_TO_DATE;
    static public final String PRICEPARAMETERS_INSERT_FORMAT="%d,%f,%d,%f,'%s','%s'";

    static public final String CREATE_BOOK="DROP TABLE IF EXISTS book; " +
            "CREATE TABLE book " +
            "(id bigint serial Primary Key," +
            "author bigint," +
            "title varchar(40)," +
            "numberOfPages integer" +
            ") ";

    static public final String BOOK_AUTHOR="author";
    static public final String BOOK_TITLE="title";
    static public final String BOOK_NUMBER_OF_PAGES="numberOfPages";
    static public final String BOOK_FIELDS=ID+","+BOOK_AUTHOR+","+BOOK_TITLE+","+BOOK_NUMBER_OF_PAGES;
    static public final String BOOK_INSERT_FORMAT="%d,%d,'%s',%d";

    static public final String CREATE_ORDER="DROP TABLE IF EXISTS \"ORDER\"; " +
            "CREATE TABLE \"ORDER\" " +
            "( id bigint serial Primary Key," +
            "author bigint," +
            "title varchar(40)," +
            "numberOfPages integer," +
            "orderDate varchar(20)," +
            "coverType varchar(30)," +
            "bookMaker bigint," +
            "bookEditor bigint," +
            "bookPriceParameters bigint," +
            "finalNumberOfPages integer," +
            "numberOfCopies integer," +
            "price numeric(4)," +
            "bookStatus varchar(30)" +
            ") ";

    static public final String ORDER_DATE="orderDate";
    static public final String ORDER_COVER_TYPE="coverType";
    static public final String ORDER_BOOK_MAKER="bookMaker";
    static public final String ORDER_BOOK_EDITOR="bookEditor";
    static public final String ORDER_BOOK_PRICE_PARAMETERS="bookPriceParameters";
    static public final String ORDER_FINAL_NUMBER_OF_PAGES="finalNumberOfPages";
    static public final String ORDER_NUMBER_OF_COPIES="numberOfCopies";
    static public final String ORDER_PRICE="price";
    static public final String ORDER_BOOK_STATUS="bookStatus";
    static public final String ORDER_FIELDS=ID+","+BOOK_AUTHOR+","+BOOK_TITLE+","+BOOK_NUMBER_OF_PAGES+","
            +ORDER_DATE+","+ORDER_COVER_TYPE+","+ORDER_BOOK_MAKER+","+ORDER_BOOK_EDITOR+"," +ORDER_BOOK_PRICE_PARAMETERS+","
            +ORDER_FINAL_NUMBER_OF_PAGES+","+ORDER_NUMBER_OF_COPIES+","+ORDER_PRICE+","+ORDER_BOOK_STATUS;
    static public final String ORDER_INSERT_FORMAT="%d,%d,'%s',%d,'%s','%s',%d,%d,%d,%d,%d,%f,'%s'";

    static public final String CREATE_CORRECTIONS="DROP TABLE IF EXISTS corrections; " +
            "CREATE TABLE corrections " +
            "(id bigint serial Primary Key," +
            "page integer," +
            "textBefore varchar(255)," +
            "textAfter varchar(255)," +
            "comment varchar(255)," +
            "\"ORDER\" bigint," +
            "meet bigint," +
            "status varchar(30)" +
            ") ";

    static public final String CORRECTIONS_PAGE="page";
    static public final String CORRECTIONS_TEXT_BEFORE="textBefore";
    static public final String CORRECTIONS_TEXT_AFTER="textAfter";
    static public final String CORRECTIONS_COMMENT="comment";
    static public final String CORRECTIONS_ORDER="order";
    static public final String CORRECTIONS_MEET="meet";
    static public final String CORRECTIONS_STATUS="status";
    static public final String CORRECTIONS_FIELDS=ID+","+CORRECTIONS_PAGE+","+CORRECTIONS_TEXT_BEFORE+","+CORRECTIONS_TEXT_AFTER+","
            +CORRECTIONS_COMMENT+","+CORRECTIONS_ORDER+","+CORRECTIONS_MEET+","+CORRECTIONS_STATUS;
    static public final String CORRECTIONS_INSERT_FORMAT="%d,%d,'%s','%s','%s',%d,%d,'%s'";

    static public final String PATH_XML = "xml_path";
    static public final String FILE_EXTENSION_XML = "xml";

    static public final String PEOPLE = "people";
    static public final String AUTHOR = "author";
    static public final String EMPLOYEE = "employee";
    static public final String MEETING = "meeting";
    static public final String COVERPRICE = "coverprice";
    static public final String BOOK = "book";
    static public final String PRICEPARAMETERS = "priceparameters";
    static public final String CORRECTIONS = "corrections";
    static public final String ORDER = "order";

    static public final String LOWER_PEOPLE = "PEOPLE";
    static public final String LOWER_AUTHOR = "AUTHOR";
    static public final String LOWER_EMPLOYEE = "EMPLOYEE";
    static public final String LOWER_MEETING = "MEETING";
    static public final String LOWER_COVERPRICE = "COVERPRICE";
    static public final String LOWER_BOOK = "BOOK";
    static public final String LOWER_PRICEPARAMETERS = "PRICEPARAMETERS";
    static public final String LOWER_CORRECTIONS = "CORRECTIONS";
    static public final String LOWER_ORDER = "ORDER";
}
