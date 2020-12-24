package ru.sfedu.mavenproject.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.h2.jdbc.JdbcSQLSyntaxErrorException;
import org.postgresql.util.PSQLException;
import ru.sfedu.mavenproject.Constants;
import ru.sfedu.mavenproject.bean.*;
import ru.sfedu.mavenproject.bean.enums.CoverType;
import ru.sfedu.mavenproject.bean.enums.EmployeeType;
import ru.sfedu.mavenproject.utils.ConfigurationUtil;

import java.io.*;
import java.sql.*;
import java.util.*;

import static ru.sfedu.mavenproject.Constants.*;
import static ru.sfedu.mavenproject.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderDB {

    private static Logger log = LogManager.getLogger(DataProviderDB.class);
    private Connection connection;

    private Connection connection() throws ClassNotFoundException, SQLException, IOException {
        connection = DriverManager.getConnection(
                getConfigurationEntry(Constants.DB_CONNECT),
                getConfigurationEntry(Constants.DB_USER),
                getConfigurationEntry(Constants.DB_PASS)
        );
        return connection;
    }

//    private Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
//        Class.forName(getConfigurationEntry(DB_DRIVER));
//        return DriverManager.getConnection(
//                getConfigurationEntry(DB_URL),
//                getConfigurationEntry(DB_USER),
//                getConfigurationEntry(DB_PASS)
//        );
//    }

    public void execute(String sql) throws SQLException, IOException, ClassNotFoundException {
        log.debug(sql);
        PreparedStatement statement = connection().prepareStatement(sql);
        statement.executeUpdate();
        statement.close();
    }

    public ResultSet getResultSet (String sql) throws SQLException, IOException, ClassNotFoundException {
        log.debug(sql);
        try {
            PreparedStatement statement = connection().prepareStatement(sql);
            connection().close();
            return statement.executeQuery();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            log.error(e);
        }
        return null;
    }

    //CRUD and helper methods

    public boolean createAllTables (){
        try {
            this.execute(CREATE_PEOPLE);
            this.execute(CREATE_AUTHOR);
            this.execute(CREATE_EMPLOYEE);
            this.execute(CREATE_MEETING);
            this.execute(CREATE_COVERPRICE);
            this.execute(CREATE_PRICEPARAMETERS);
            this.execute(CREATE_ORDER);
            this.execute(CREATE_CORRECTIONS);
            this.execute(CREATE_BOOK);
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
            return false;
        }
    }

    public boolean dropAllTables (){
        try {
            this.execute(String.format(DB_DROP,PEOPLE));
            this.execute(String.format(DB_DROP,AUTHOR));
            this.execute(String.format(DB_DROP,EMPLOYEE));
            this.execute(String.format(DB_DROP,MEETING));
            this.execute(String.format(DB_DROP,COVERPRICE));
            this.execute(String.format(DB_DROP,PRICEPARAMETERS));
            this.execute(String.format(DB_DROP,ORDER));
            this.execute(String.format(DB_DROP,CORRECTIONS));
            this.execute(String.format(DB_DROP,BOOK));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
            return false;
        }
    }

    public People getPeopleById(long id) {
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT_BY_ID, People.class.getSimpleName().toUpperCase(),id));
            if (set!=null && set.next()) {
                People people = new People();
                people.setId(set.getLong(ID));
                people.setFirstName(set.getString(PEOPLE_FIRST_NAME));
                people.setSecondName(set.getString(PEOPLE_SECOND_NAME));
                people.setLastName(set.getString(PEOPLE_LAST_NAME));
                people.setPhone(set.getString(PEOPLE_PHONE));
                return people;
            }else{
                return null;
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
            return null;
        }
    }

    public Author getAuthorById(long id)  {
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT_BY_ID, Author.class.getSimpleName().toUpperCase(),id));
            set.next();
            Author author = new Author();
            author.setId(set.getLong(ID));
            author.setFirstName(set.getString(PEOPLE_FIRST_NAME));
            author.setSecondName(set.getString(PEOPLE_SECOND_NAME));
            author.setLastName(set.getString(PEOPLE_LAST_NAME));
            author.setPhone(set.getString(PEOPLE_PHONE));
            author.setEmail(set.getString(AUTHOR_EMAIL));
            author.setDegree(set.getString(AUTHOR_DEGREE));
            author.setOrganization(set.getString(AUTHOR_ORGANIZATION));
            return author;
        }catch (SQLException | IOException | ClassNotFoundException e){
            log.error(e);
            return null;
        }
    }

    public Employee getEmployeeById(long id) throws SQLException, IOException, ClassNotFoundException {
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT_BY_ID, Employee.class.getSimpleName().toUpperCase(),id));
            set.next();
            Employee employee = new Employee();
            employee.setId(set.getLong(ID));
            employee.setFirstName(set.getString(PEOPLE_FIRST_NAME));
            employee.setSecondName(set.getString(PEOPLE_SECOND_NAME));
            employee.setLastName(set.getString(PEOPLE_LAST_NAME));
            employee.setPhone(set.getString(PEOPLE_PHONE));
            employee.setInn(set.getString(EMPLOYEE_INN));
            employee.setWorkRecordBook(set.getString(EMPLOYEE_WORK_RECORD_BOOK));
            employee.setEmplpyeeType(EmployeeType.valueOf(set.getString(EMPLOYEE_TYPE)));
            return employee;
        }catch (SQLException | IOException | ClassNotFoundException e){
            log.error(e);
            return null;
        }
    }

//    public Book getBookByID(long id) {
//        ResultSet set = getResultSet(String.format(DB_SELECT_BY_ID, Employee.class.getSimpleName().toLowerCase(),id));
//        try {
//            set.next();
//            Employee employee = new Employee();
//            employee.setId(set.getLong(ID));
//            employee.setFirstName(set.getString(PEOPLE_FIRST_NAME));
//            employee.setSecondName(set.getString(PEOPLE_SECOND_NAME));
//            employee.setLastName(set.getString(PEOPLE_LAST_NAME));
//            employee.setPhone(set.getString(PEOPLE_PHONE));
//            employee.setInn(set.getString(EMPLOYEE_INN));
//            employee.setWorkRecordBook(set.getString(EMPLOYEE_WORK_RECORD_BOOK));
//            employee.setEmplpyeeType(EmployeeType.valueOf(set.getString(EMPLOYEE_TYPE)));
//            return employee;
//        }catch (PSQLException e){
//            log.error(e);
//            return null;
//        }
//    }

    public Meeting getMeetingByID(long id) throws SQLException, IOException, ClassNotFoundException {
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT_BY_ID, Meeting.class.getSimpleName().toUpperCase(),id));
            set.next();
            Meeting meeting = new Meeting();
            meeting.setId(set.getLong(ID));
            meeting.setMeetDate(set.getString(MEETING_MEET_DATE));
            meeting.setAuthorAgreement(set.getBoolean(MEETING_AUTHOR_AGREEMENT));
            meeting.setEditorAgreement(set.getBoolean(MEETING_EDITOR_AGREEMENT));
            return meeting;
        }catch (SQLException | IOException | ClassNotFoundException e){
            log.error(e);
            return null;
        }
    }

//    public Corrections getCorrectionsByID(long id) throws IOException {
//        List<Corrections> list = this.read(Corrections.class);
//        try {
//            Corrections obj = list.stream()
//                    .filter(e1 -> e1.getId() == id)
//                    .findFirst().get();
//            return obj;
//        }catch (NoSuchElementException e){
//            log.error(e);
//            return null;
//        }
//    }
//
//    public PriceParameters getPriceParametersByID(long id) throws IOException {
//        List<PriceParameters> list = this.read(PriceParameters.class);
//        try {
//            PriceParameters obj = list.stream()
//                    .filter(e1 -> e1.getId() == id)
//                    .findFirst().get();
//            return obj;
//        }catch (NoSuchElementException e){
//            log.error(e);
//            return null;
//        }
//    }

    public CoverPrice getCoverPriceByID(long id) throws SQLException, IOException, ClassNotFoundException {
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT_BY_ID, CoverPrice.class.getSimpleName().toUpperCase(),id));
            set.next();
            CoverPrice coverPrice = new CoverPrice();
            coverPrice.setId(set.getLong(ID));
            coverPrice.setCoverType(CoverType.valueOf(set.getString(COVERPRICE_COVER_TYPE)));
            coverPrice.setPrice(set.getLong(COVERPRICE_PRICE));
            return coverPrice;
        }catch (SQLException | IOException | ClassNotFoundException e){
            log.error(e);
            return null;
        }
    }
//
//    public String createFile(String path) throws IOException {
//        File file = new File(path);
//        file.createNewFile();
//        return path;
//    }

    public List<People> insertPeople(List<People> list) throws SQLException, IOException, ClassNotFoundException {
        log.info("insertPeople");
        List<People> returnList = new ArrayList<>();
        list.stream().forEach(people -> {
            try {
                this.execute(
                        String.format(
                                DB_INSERT,
                                people.getClass().getSimpleName().toUpperCase(),
                                PEOPLE_FIELDS,
                                String.format(
                                        PEOPLE_INSERT_FORMAT, people.getId(), people.getFirstName(), people.getSecondName(), people.getLastName(), people.getPhone()
                                )
                        )
                );
                log.debug("will add to db "+people);
            } catch (SQLException | IOException | ClassNotFoundException e){
                log.error(e);
                log.debug("id already exist in db "+people);
                returnList.add(people);
            }
        });
        return returnList;
    }

    public List<Author> insertAuthor(List<Author> list) throws SQLException, IOException, ClassNotFoundException {
        log.info("insertAuthor");
        List<Author> returnList = new ArrayList<>();
        list.stream().forEach(el -> {
            try {
                this.execute(
                        String.format(
                                DB_INSERT,
                                el.getClass().getSimpleName().toUpperCase(),
                                AUTHOR_FIELDS,
                                String.format(
                                        AUTHOR_INSERT_FORMAT, el.getId(), el.getFirstName(), el.getSecondName(), el.getLastName(), el.getPhone(),
                                        el.getEmail(), el.getDegree(), el.getOrganization()
                                )
                        )
                );
                log.debug("will add to db "+el);
            } catch (SQLException | IOException | ClassNotFoundException e){
                log.error(e);
                log.debug("id already exist in db "+el);
                returnList.add(el);
            }
        });
        return returnList;
    }

    public List<Employee> insertEmployee(List<Employee> list) throws SQLException, IOException, ClassNotFoundException {
        log.info("insertEmployee");
        List<Employee> returnList = new ArrayList<>();
        list.stream().forEach(el -> {
            try {
                this.execute(
                        String.format(
                                DB_INSERT,
                                el.getClass().getSimpleName().toUpperCase(),
                                EMPLOYEE_FIELDS,
                                String.format(
                                        EMPLOYEE_INSERT_FORMAT, el.getId(), el.getFirstName(), el.getSecondName(), el.getLastName(), el.getPhone(),
                                        el.getInn(), el.getWorkRecordBook(), el.getEmplpyeeType()
                                )
                        )
                );
                log.debug("will add to db "+el);
            } catch (SQLException | IOException | ClassNotFoundException e){
                log.error(e);
                log.debug("id already exist in db "+el);
                returnList.add(el);
            }
        });
        return returnList;
    }

    public List<Meeting> insertMeeting(List<Meeting> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        log.info("insertMeeting");
        List<Meeting> returnList = new ArrayList<>();
        list.stream().forEach(el -> {
            try {
                this.execute(
                        String.format(
                                DB_INSERT,
                                el.getClass().getSimpleName().toUpperCase(),
                                MEETING_FIELDS,
                                String.format(
                                        MEETING_INSERT_FORMAT, el.getId(), el.getMeetDate(), el.getAuthorAgreement(), el.getEditorAgreement()
                                )
                        )
                );
                log.debug("will add to db "+el);
            } catch (SQLException | IOException | ClassNotFoundException e){
                log.error(e);
                log.debug("id already exist in db "+el);
                returnList.add(el);
            }
        });
        return returnList;
    }

    public List<CoverPrice> insertCoverPrice(List<CoverPrice> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        log.info("insertCoverPrice");
        List<CoverPrice> returnList = new ArrayList<>();
        list.stream().forEach(el -> {
            try {
                this.execute(
                        String.format(
                                DB_INSERT,
                                el.getClass().getSimpleName().toUpperCase(),
                                COVERPRICE_FIELDS,
                                String.format(
                                        COVERPRICE_INSERT_FORMAT, el.getId(), el.getCoverType(), el.getPrice()
                                )
                        )
                );
                log.debug("will add to db "+el);
            } catch (SQLException | IOException | ClassNotFoundException e){
                log.error(e);
                log.debug("id already exist in db "+el);
                returnList.add(el);
            }
        });
        return returnList;
    }
//
//    public List<PriceParameters> insertPriceParameters(List<PriceParameters> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        List<PriceParameters> returnList = new ArrayList<>();
//        List<PriceParameters> recordList = read(PriceParameters.class);
//        list.stream().forEach(el -> {
//            //проверка, есть ли элемент указанный в поле CoverPrice в соответствующем csv файле
//            if (el.getCoverPrice().isEmpty() || el.getCoverPrice().stream().allMatch(e2 -> {
//                try {
//                    return getCoverPriceByID(e2.getId()) != null;
//                } catch (IOException e) {
//                    log.error(e);
//                    return false;
//                }
//            })){
//                log.debug("there is such CoverPrice");
//                if (recordList.stream().anyMatch(e2 -> e2.equals(el))){
//                    log.debug("already exist in csv "+el);
//                }else if (recordList.stream().anyMatch(e2 -> e2.getId() == el.getId())) {
//                    log.debug("id already exist in csv " + el);
//                    returnList.add(el);
//                }else{
//                    log.debug("will add to csv "+el);
//                    recordList.add(el);
//                }
//            }else{
//                log.debug("there is not such CoverPrice");
//                returnList.add(el);
//            }
//        });
//        try {
//            csvWriter(PriceParameters.class, recordList);
//        }catch (IndexOutOfBoundsException e){
//            log.error(e);
//        }finally {
//            return returnList;
//        }
//    }
//
//    public List<Order> insertOrder(List<Order> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        List<Order> returnList = new ArrayList<>();
//        List<Order> recordList = read(Order.class);
//        list.stream().forEach(el -> {
//            try {
//                //проверка, есть ли элементы указанный в поле CoverPrice в соответствующем csv файле
//                if ((el.getBookMaker() == null || getPeopleByID(Employee.class,el.getBookMaker().getId()) != null)
//                        && (el.getBookEditor() == null || getPeopleByID(Employee.class,el.getBookEditor().getId()) != null)
//                        && (el.getBookPriceParameters() == null || getPriceParametersByID(el.getBookPriceParameters().getId()) != null)){
//                    log.debug("there is such BookMaker and BookEditor and PriceParameters");
//                    if (recordList.stream().anyMatch(e2 -> e2.equals(el))){
//                        log.debug("already exist in csv "+el);
//                    }else if (recordList.stream().anyMatch(e2 -> e2.getId() == el.getId())) {
//                        log.debug("id already exist in csv " + el);
//                        returnList.add(el);
//                    }else{
//                        log.debug("will add to csv "+el);
//                        recordList.add(el);
//                    }
//                }else{
//                    log.debug("there is not such BookMaker or BookEditor or PriceParameters");
//                    returnList.add(el);
//                }
//            } catch (IOException e) {
//                log.error(e);
//            }
//        });
//        try {
//            csvWriter(Order.class, recordList);
//        }catch (IndexOutOfBoundsException e){
//            log.error(e);
//        }finally {
//            return returnList;
//        }
//    }
//
//    public List<Corrections> insertCorrections(List<Corrections> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        List<Corrections> returnList = new ArrayList<>();
//        List<Corrections> recordList = read(Corrections.class);
//        list.stream().forEach(el -> {
//            try {
//                //проверка, есть ли элементы указанный в поле order и meet в соответствующем csv файле
//                if ((el.getOrder() == null || getBookByID(Order.class,el.getOrder().getId()) != null)
//                        && (el.getMeet() == null || getMeetingByID(el.getMeet().getId()) != null)){
//                    log.debug("there is such Order and Meeting");
//                    if (recordList.stream().anyMatch(e2 -> e2.equals(el))){
//                        log.debug("already exist in csv "+el);
//                    }else if (recordList.stream().anyMatch(e2 -> e2.getId() == el.getId())) {
//                        log.debug("id already exist in csv " + el);
//                        returnList.add(el);
//                    }else{
//                        log.debug("will add to csv "+el);
//                        recordList.add(el);
//                    }
//                }else{
//                    log.debug("there is not such Order or Meeting");
//                    returnList.add(el);
//                }
//            } catch (IOException e) {
//                log.error(e);
//            }
//        });
//        try {
//            csvWriter(Corrections.class, recordList);
//        }catch (IndexOutOfBoundsException e){
//            log.error(e);
//        }finally {
//            return returnList;
//        }
//    }
//
//    public List<Book> insertBook(List<Book> list) throws IOException {
//        List<Book> returnList = new ArrayList<>();
//        List<Book> recordList = read(Book.class);
//        list.stream().forEach(el -> {
//            try {
//                //проверка, есть ли элемент указанный в поле Author в соответствующем csv файле
//                if (el.getAuthor() == null || getPeopleByID(Author.class, el.getAuthor().getId()) != null){
//                    log.debug("there is such Author");
//                    if (recordList.stream().anyMatch(e2 -> e2.equals(el))){
//                        log.debug("already exist in csv "+el);
//                    }else if (recordList.stream().anyMatch(e2 -> e2.getId() == el.getId())) {
//                        log.debug("id already exist in csv " + el);
//                        returnList.add(el);
//                    }else{
//                        log.debug("will add to csv "+el);
//                        recordList.add(el);
//                    }
//                }else{
//                    log.debug("there is not such Author");
//                    returnList.add(el);
//                }
//            } catch (IOException e) {
//                log.error(e);
//            }
//        });
//        try {
//            csvWriter(Book.class, recordList);
//        }catch (IndexOutOfBoundsException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e){
//            log.error(e);
//        }finally {
//            return returnList;
//        }
//    }
//
//    public boolean deleteFile(Class cl) {
//        try{
//            CSVWriter csvWriter = new CSVWriter(commonWriter(cl));
//            csvWriter.close();
//            return true;
//        }catch (IOException e){
//            log.error(e);
//            return false;
//        }
//    }
//
//    public boolean dropFile(Class cl) {
//        try{
//            File file= new File(getPath(cl));
//            log.debug("file delete "+file.delete());
//            return true;
//        }catch (IOException e){
//            log.error(e);
//            return false;
//        }
//    }
//
//    public <T extends People> boolean updatePeople(Class cl, T obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        List<T> list = read(cl);
//        T prevObj;
//        int index = list.indexOf(getPeopleByID(cl, obj.getId()));
//        if (index>(-1)){
//            prevObj = list.get(index);
//            list.set(index, obj);
//            deleteFile(cl);
//            List<T> listNotInsert = insertPeople(cl, list);
//            if (listNotInsert.indexOf(obj)==-1){
//                log.debug("update success");
//                return true;
//            }else{
//                list.set(index, prevObj);
//                deleteFile(cl);
//                insertPeople(cl, list);
//            }
//        }
//        log.debug("update fail");
//        return false;
//    }
//
//    public boolean updateMeeting(Meeting obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = Meeting.class;
//        List<Meeting> list = read(cl);
//        Meeting prevObj;
//        int index = list.indexOf(getMeetingByID(obj.getId()));
//        if (index>(-1)){
//            prevObj = list.get(index);
//            list.set(index, obj);
//            deleteFile(cl);
//            List<Meeting> listNotInsert = insertMeeting(list);
//            if (listNotInsert.indexOf(obj)==-1){
//                log.debug("update success");
//                return true;
//            }else{
//                list.set(index, prevObj);
//                deleteFile(cl);
//                insertMeeting(list);
//            }
//        }
//        log.debug("update fail");
//        return false;
//    }
//
//    public boolean updateCoverPrice(CoverPrice obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = CoverPrice.class;
//        List<CoverPrice> list = read(cl);
//        CoverPrice prevObj;
//        int index = list.indexOf(getCoverPriceByID(obj.getId()));
//        if (index>(-1)){
//            prevObj = list.get(index);
//            list.set(index, obj);
//            deleteFile(cl);
//            List<CoverPrice> listNotInsert = insertCoverPrice(list);
//            if (listNotInsert.indexOf(obj)==-1){
//                log.debug("update success");
//                return true;
//            }else{
//                list.set(index, prevObj);
//                deleteFile(cl);
//                insertCoverPrice(list);
//            }
//        }
//        log.debug("update fail");
//        return false;
//    }
//
//    public boolean updatePriceParameters(PriceParameters obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = PriceParameters.class;
//        List<PriceParameters> list = read(cl);
//        PriceParameters prevObj;
//        int index = list.indexOf(getPriceParametersByID(obj.getId()));
//        if (index>(-1)){
//            prevObj = list.get(index);
//            list.set(index, obj);
//            deleteFile(cl);
//            List<PriceParameters> listNotInsert = insertPriceParameters(list);
//            if (listNotInsert.indexOf(obj)==-1){
//                log.debug("update success");
//                return true;
//            }else{
//                list.set(index, prevObj);
//                deleteFile(cl);
//                insertPriceParameters(list);
//            }
//        }
//        log.debug("update fail");
//        return false;
//    }
//
//    public boolean updateOrder(Order obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = Order.class;
//        List<Order> list = read(cl);
//        Order prevObj;
//        int index = list.indexOf(getBookByID(cl, obj.getId()));
//        if (index>(-1)){
//            prevObj = list.get(index);
//            list.set(index, obj);
//            deleteFile(cl);
//            List<Order> listNotInsert = insertOrder(list);
//            if (listNotInsert.indexOf(obj)==-1){
//                log.debug("update success");
//                return true;
//            }else{
//                list.set(index, prevObj);
//                deleteFile(cl);
//                insertOrder(list);
//            }
//        }
//        log.debug("update fail");
//        return false;
//    }
//
//    public boolean updateCorrections(Corrections obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = Corrections.class;
//        List<Corrections> list = read(cl);
//        Corrections prevObj;
//        int index = list.indexOf(getCorrectionsByID(obj.getId()));
//        if (index>(-1)){
//            prevObj = list.get(index);
//            list.set(index, obj);
//            deleteFile(cl);
//            List<Corrections> listNotInsert = insertCorrections(list);
//            if (listNotInsert.indexOf(obj)==-1){
//                log.debug("update success");
//                return true;
//            }else{
//                list.set(index, prevObj);
//                deleteFile(cl);
//                insertCorrections(list);
//            }
//        }
//        log.debug("update fail");
//        return false;
//    }
//
//    public boolean updateBook(Book obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = Book.class;
//        List<Book> list = read(cl);
//        Book prevObj;
//        int index = list.indexOf(getBookByID(cl, obj.getId()));
//        if (index>(-1)){
//            prevObj = list.get(index);
//            list.set(index, obj);
//            deleteFile(cl);
//            List<Book> listNotInsert = insertBook(list);
//            if (listNotInsert.indexOf(obj)==-1){
//                log.debug("update success");
//                return true;
//            }else{
//                list.set(index, prevObj);
//                deleteFile(cl);
//                insertBook(list);
//            }
//        }
//        log.debug("update fail");
//        return false;
//    }
//
//    public <T> boolean deleteObj(Class cl, T obj) {
//        try {
//            switch (cl.getSimpleName().toLowerCase()) {
//                case Constants.CORRECTIONS:
//                    Corrections corObj = (Corrections) obj;
//                    log.info("delete " + Constants.CORRECTIONS);
//                    return deleteCorrections(corObj);
//                case Constants.PEOPLE:
//                    People peoplObj = (People) obj;
//                    log.info("delete " + Constants.PEOPLE);
//                    return deletePeople(peoplObj);
//                case Constants.BOOK:
//                    Book bookObj = (Book) obj;
//                    log.info("delete " + Constants.BOOK);
//                    return deleteBook(bookObj);
//                case Constants.EMPLOYEE:
//                    Employee emplObj = (Employee) obj;
//                    log.info("delete " + Constants.EMPLOYEE);
//                    return deleteEmployee(emplObj);
//                case Constants.PRICEPARAMETERS:
//                    PriceParameters priceObj = (PriceParameters) obj;
//                    log.info("delete " + Constants.PRICEPARAMETERS);
//                    return deletePriceParameters(priceObj);
//                case Constants.ORDER:
//                    Order ordObj = (Order) obj;
//                    log.info("delete " + Constants.ORDER);
//                    return deleteOrder(ordObj);
//                case Constants.MEETING:
//                    Meeting meetObj = (Meeting) obj;
//                    log.info("delete " + Constants.MEETING);
//                    return deleteMeeting(meetObj);
//                case Constants.AUTHOR:
//                    Author authObj = (Author) obj;
//                    log.info("delete " + Constants.AUTHOR);
//                    return deleteAuthor(authObj);
//                case Constants.COVERPRICE:
//                    CoverPrice covObj = (CoverPrice) obj;
//                    log.info("delete " + Constants.COVERPRICE);
//                    return deleteCoverPrice(covObj);
//                default:
//                    log.debug("default case");
//                    return false;
//            }
//        }catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e){
//            log.error(e);
//            return false;
//        }
//    }
//
//    public boolean deleteCorrections(Corrections obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = Corrections.class;
//        List<Corrections> list = read(cl);
//        try {
//            checkListIsNotEmpty(list);
//            if (list.indexOf(obj) > (-1)) {
//                list.removeIf(el -> el.equals(obj));
//                deleteFile(cl);
//                insertCorrections(list);
//                log.debug("delete success");
//                return true;
//            } else {
//                log.debug("delete fail");
//                return false;
//            }
//        }catch (Exception e){
//            log.debug("csv is empty");
//            return false;
//        }
//    }
//
//    public boolean deletePeople(People obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = People.class;
//        List<People> list = read(cl);
//        try {
//            checkListIsNotEmpty(list);
//            if (list.indexOf(obj) > (-1)) {
//                list.removeIf(el -> el.equals(obj));
//                deleteFile(cl);
//                insertPeople(cl, list);
//                log.debug("delete success");
//                return true;
//            } else {
//                log.debug("delete fail");
//                return false;
//            }
//        }catch (Exception e){
//            log.debug("csv is empty");
//            return false;
//        }
//    }
//
//    public boolean deleteBook(Book obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = Book.class;
//        List<Book> list = read(cl);
//        try {
//            checkListIsNotEmpty(list);
//            if (list.indexOf(obj) > (-1)) {
//                list.removeIf(el -> el.equals(obj));
//                deleteFile(cl);
//                insertBook(list);
//                log.debug("delete success");
//                return true;
//            } else {
//                log.debug("delete fail");
//                return false;
//            }
//        }catch (Exception e){
//            log.debug("csv is empty");
//            return false;
//        }
//    }
//
//    public boolean deleteEmployee(Employee obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = Employee.class;
//        List<Employee> list = read(cl);
//        try {
//            checkListIsNotEmpty(list);
//            List<Order> listOrder = read(Order.class);
//            if (!listOrder.stream().anyMatch(el -> (el.getBookEditor().getId() != obj.getId() || el.getBookMaker().getId() == obj.getId()))) {
//                list.removeIf(el -> el.equals(obj));
//                deleteFile(cl);
//                insertPeople(cl, list);
//                log.debug("delete success");
//                return true;
//            } else {
//                log.debug("delete fail");
//                return false;
//            }
//        }catch (Exception e){
//            log.debug("csv is empty");
//            return false;
//        }
//    }
//
//    public boolean deletePriceParameters(PriceParameters obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = PriceParameters.class;
//        List<PriceParameters> list = read(cl);
//        try {
//            checkListIsNotEmpty(list);
//            List<Order> listOrder2 = read(Order.class);
//            if (!listOrder2.stream().anyMatch(el -> el.getBookPriceParameters().getId() == obj.getId())) {
//                list.removeIf(el -> el.equals(obj));
//                deleteFile(cl);
//                insertPriceParameters(list);
//                log.debug("delete success");
//                return true;
//            } else {
//                log.debug("delete fail");
//                return false;
//            }
//        }catch (Exception e){
//            log.debug("csv is empty");
//            return false;
//        }
//    }
//
//    public boolean deleteOrder(Order obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = Order.class;
//        List<Order> list = read(cl);
//        try {
//            checkListIsNotEmpty(list);
//            List<Corrections> listCorrections = read(Corrections.class);
//            if (!listCorrections.stream().anyMatch(el -> el.getOrder().getId() == obj.getId())) {
//                list.removeIf(el -> el.equals(obj));
//                deleteFile(cl);
//                insertOrder(list);
//                log.debug("delete success");
//                return true;
//            } else {
//                log.debug("delete fail");
//                return false;
//            }
//        }catch (Exception e){
//            log.debug("csv is empty");
//            return false;
//        }
//    }
//
//    public boolean deleteMeeting(Meeting obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = Meeting.class;
//        List<Meeting> list = read(cl);
//        try {
//            checkListIsNotEmpty(list);
//            List<Corrections> listCorrections2 = read(Book.class);
//            if (!listCorrections2.stream().anyMatch(el -> el.getMeet().getId() == obj.getId())) {
//                list.removeIf(el -> el.equals(obj));
//                deleteFile(cl);
//                insertMeeting(list);
//                log.debug("delete success");
//                return true;
//            } else {
//                log.debug("delete fail");
//                return false;
//            }
//        }catch (Exception e){
//            log.debug("csv is empty");
//            return false;
//        }
//    }
//
//    public boolean deleteAuthor(Author obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = Author.class;
//        List<Author> list = read(cl);
//        try {
//            checkListIsNotEmpty(list);
//            List<Book> listBook = read(Book.class);
//            if (!listBook.stream().anyMatch(el -> el.getAuthor().getId() == obj.getId())) {
//                list.removeIf(el -> el.equals(obj));
//                deleteFile(cl);
//                insertPeople(cl, list);
//                log.debug("delete success");
//                return true;
//            } else {
//                log.debug("delete fail");
//                return false;
//            }
//        }catch (Exception e){
//            log.debug("csv is empty");
//            return false;
//        }
//    }
//
//    public boolean deleteCoverPrice(CoverPrice obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        Class cl = CoverPrice.class;
//        List<CoverPrice> list = read(cl);
//        try {
//            checkListIsNotEmpty(list);
//            List<PriceParameters> listPriceParameters = read(PriceParameters.class);
//            if (!listPriceParameters.stream().anyMatch(el -> el.getCoverPrice().stream().anyMatch(id -> id.getId() == obj.getId()))) {
//                list.removeIf(el -> el.equals(obj));
//                deleteFile(cl);
//                insertCoverPrice(list);
//                log.debug("delete success");
//                return true;
//            } else {
//                log.debug("delete fail");
//                return false;
//            }
//        }catch (Exception e){
//            log.debug("csv is empty");
//            return false;
//        }
//    }
//
//    public long getMaxId (Class cl) {
//        try {
//            switch (cl.getSimpleName().toLowerCase()) {
//                case Constants.CORRECTIONS:
//                    List<Corrections> corList = read(cl);
//                    return corList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
//                case Constants.PEOPLE:
//                    List<People> poepList = read(cl);
//                    return poepList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
//                case Constants.BOOK:
//                    List<Book> bookList = read(cl);
//                    return bookList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
//                case Constants.EMPLOYEE:
//                    List<Employee> emplList = read(cl);
//                    return emplList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
//                case Constants.PRICEPARAMETERS:
//                    List<PriceParameters> priceList = read(cl);
//                    return priceList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
//                case Constants.ORDER:
//                    List<Order> orderList = read(cl);
//                    return orderList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
//                case Constants.MEETING:
//                    List<Meeting> meetList = read(cl);
//                    return meetList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
//                case Constants.AUTHOR:
//                    List<Author> authList = read(cl);
//                    return authList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
//                case Constants.COVERPRICE:
//                    List<CoverPrice> covList = read(cl);
//                    return covList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
//                default:
//                    log.debug("default case");
//                    return -1;
//            }
//        }catch (NoSuchElementException | IOException e){
//            log.error(e);
//            return -1;
//        }
//    }
//
//    public Employee createDefaultEmloyee(){
//        Employee employee = new Employee();
//        employee.setId(0);
//        employee.setFirstName("Default");
//        employee.setSecondName("Default");
//        employee.setLastName("Default");
//        employee.setPhone("00000000000");
//        employee.setInn("000000000000");
//        employee.setWorkRecordBook("0000000");
//        employee.setEmplpyeeType(EmployeeType.ADMIN);
//        return employee;
//    }
//
//    public PriceParameters createDefaultPriceParameters() throws IOException {
//        try {
//            List<CoverPrice> list = new ArrayList<>();
//            createDefaultCoverPrice();
//            list.add(getCoverPriceByID(0));
//            PriceParameters priceParameters = new PriceParameters();
//            priceParameters.setId(0);
//            priceParameters.setPagePrice(0.0);
//            priceParameters.setCoverPrice(list);
//            priceParameters.setWorkPrice(0.0);
//            priceParameters.setValidFromDate("1970-01-01");
//            priceParameters.setValidToDate("1970-01-02");
//            return priceParameters;
//        }catch (IOException e){
//            return null;
//        }
//    }
//
//    public CoverPrice createDefaultCoverPrice(){
//        CoverPrice coverPrice = new CoverPrice();
//        coverPrice.setId(0);
//        coverPrice.setCoverType(CoverType.PAPERBACK);
//        coverPrice.setCoverType(CoverType.PAPERBACK);
//        coverPrice.setPrice(0.0);
//        return coverPrice;
//    }
//
//    public Meeting createDefaultMeeting(){
//        Meeting meet = null;
//        try {
//            meet = getMeetingByID(0);
//        } catch (IOException e) {
//            log.error(e);
//            meet = new Meeting();
//            meet.setId(0);
//            meet.setMeetDate("1970-01-01");
//            meet.setAuthorAgreement(false);
//            meet.setEditorAgreement(false);
//            List<Meeting> list = new ArrayList<>();
//            list.add(meet);
//            insertMeeting(list);
//        } finally {
//            return meet;
//        }
//    }
//
//    public <T> void checkNotNullObject (T obj) throws Exception {
//        if (obj == null) throw new Exception("Object is null");
//    }
//
//    public <T> void checkListIsNotEmpty (List<T> list) throws Exception {
//        if (list.isEmpty()) throw new Exception("List is Empty");
//    }
//
//    public void checkTrue (boolean result) throws Exception {
//        if (!result) throw new Exception("Result false");
//    }
//
//    public <T> void checkNullObject (T obj) throws Exception {
//        if (obj != null) throw new Exception("Object is not null");
//    }


}
