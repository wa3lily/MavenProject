package ru.sfedu.mavenproject.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.Constants;
import ru.sfedu.mavenproject.bean.*;
import ru.sfedu.mavenproject.bean.enums.BookStatus;
import ru.sfedu.mavenproject.bean.enums.CorrectionsStatus;
import ru.sfedu.mavenproject.bean.enums.CoverType;
import ru.sfedu.mavenproject.bean.enums.EmployeeType;

import java.io.*;
import java.sql.*;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static ru.sfedu.mavenproject.Constants.*;
import static ru.sfedu.mavenproject.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderDB implements DataProvider{

    private final Logger log = LogManager.getLogger(DataProviderDB.class);
    private Connection connection;

    private Connection connection() throws ClassNotFoundException, SQLException, IOException {
        connection = DriverManager.getConnection(
                getConfigurationEntry(Constants.DB_CONNECT),
                getConfigurationEntry(Constants.DB_USER),
                getConfigurationEntry(Constants.DB_PASS)
        );
        return connection;
    }

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
            this.execute(CREATE_COVERLINK);
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
            this.execute(String.format(DB_DROP,COVERLINK));
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

    public Book getBookByID(long id) throws SQLException, IOException, ClassNotFoundException {
        ResultSet set = getResultSet(String.format(DB_SELECT_BY_ID, Book.class.getSimpleName().toUpperCase(),id));
        try {
            set.next();
            Book book = new Book();
            book.setId(set.getLong(ID));
            book.setAuthor(getAuthorById(set.getLong(BOOK_AUTHOR)));
            book.setTitle(set.getString(BOOK_TITLE));
            book.setNumberOfPages(set.getInt(BOOK_NUMBER_OF_PAGES));
            return book;
        }catch (SQLException e){
            log.error(e);
            return null;
        }
    }

    public Order getOrderByID(long id) throws SQLException, IOException, ClassNotFoundException {
        ResultSet set = getResultSet(String.format(DB_SELECT_BY_ID, Order.class.getSimpleName().toUpperCase(),id));
        try {
            set.next();
            Order order = new Order();
            order.setId(set.getLong(ID));
            order.setAuthor(getAuthorById(set.getLong(BOOK_AUTHOR)));
            order.setTitle(set.getString(BOOK_TITLE));
            order.setNumberOfPages(set.getInt(BOOK_NUMBER_OF_PAGES));
            order.setOrderDate(set.getString(ORDER_DATE));
            order.setCoverType(CoverType.valueOf(set.getString(ORDER_COVER_TYPE)));
            order.setBookMaker(getEmployeeById(set.getLong(ORDER_BOOK_MAKER)));
            order.setBookEditor(getEmployeeById(set.getLong(ORDER_BOOK_EDITOR)));
            order.setBookPriceParameters(getPriceParametersByID(set.getLong(ORDER_BOOK_PRICE_PARAMETERS)));
            order.setFinalNumberOfPages(set.getInt(ORDER_FINAL_NUMBER_OF_PAGES));
            order.setNumberOfCopies(set.getInt(ORDER_NUMBER_OF_COPIES));
            order.setPrice(set.getDouble(ORDER_PRICE));
            order.setBookStatus(BookStatus.valueOf(set.getString(ORDER_BOOK_STATUS)));
            return order;
        }catch (SQLException e){
            log.error(e);
            return null;
        }
    }

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

    public Corrections getCorrectionsByID(long id) throws SQLException, IOException, ClassNotFoundException {
        ResultSet set = getResultSet(String.format(DB_SELECT_BY_ID, Corrections.class.getSimpleName().toUpperCase(),id));
        try {
            set.next();
            Corrections corrections = new Corrections();
            corrections.setId(set.getLong(ID));
            corrections.setPage(set.getInt(CORRECTIONS_PAGE));
            corrections.setTextBefore(set.getString(CORRECTIONS_TEXT_BEFORE));
            corrections.setTextAfter(set.getString(CORRECTIONS_TEXT_AFTER));
            corrections.setComment(set.getString(CORRECTIONS_COMMENT));
            corrections.setOrder(getOrderByID(set.getLong(CORRECTIONS_ORDER)));
            corrections.setMeet(getMeetingByID(set.getLong(CORRECTIONS_MEET)));
            corrections.setStatus(CorrectionsStatus.valueOf(set.getString(CORRECTIONS_STATUS)));
            return corrections;
        }catch (SQLException e){
            log.error(e);
            return null;
        }
    }

    public PriceParameters getPriceParametersByID(long id) throws SQLException, IOException, ClassNotFoundException {
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT_BY_ID, PriceParameters.class.getSimpleName().toUpperCase(),id));
            set.next();
            PriceParameters priceParameters = new PriceParameters();
            priceParameters.setId(set.getLong(ID));
            priceParameters.setPagePrice(set.getDouble(PRICEPARAMETERS_PAGE_PRICE));
            ResultSet set2 = getResultSet(String.format(COVERLINK_GET_ID, set.getLong(ID)));
            List<CoverPrice> coverPriceList = new ArrayList<>();
            while (set2.next()){
                CoverPrice coverPrice = getCoverPriceByID(set2.getLong(COVERLINK_COVERPRICE));
                coverPriceList.add(coverPrice);
            }
            priceParameters.setCoverPrice(coverPriceList);
            priceParameters.setWorkPrice(set.getDouble(PRICEPARAMETERS_WORK_PRICE));
            priceParameters.setValidFromDate(set.getString(PRICEPARAMETERS_VALID_FROM_DATE));
            priceParameters.setValidToDate(set.getString(PRICEPARAMETERS_VALID_TO_DATE));
            return priceParameters;
        }catch (SQLException | IOException | ClassNotFoundException e){
            log.error(e);
            return null;
        }
    }

    public CoverPrice getCoverPriceByID(long id) throws SQLException, IOException, ClassNotFoundException {
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT_BY_ID, CoverPrice.class.getSimpleName().toUpperCase(),id));
            set.next();
            CoverPrice coverPrice = new CoverPrice();
            coverPrice.setId(set.getLong(ID));
            coverPrice.setCoverType(CoverType.valueOf(set.getString(COVERPRICE_COVER_TYPE)));
            coverPrice.setPrice(set.getDouble(COVERPRICE_PRICE));
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
                log.debug("insert fail "+people);
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
                log.debug("insert fail "+el);
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
                log.debug("insert fail "+el);
                returnList.add(el);
            }
        });
        return returnList;
    }

    public List<Meeting> insertMeeting(List<Meeting> list) throws SQLException, IOException, ClassNotFoundException {
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
                log.debug("insert fail "+el);
                returnList.add(el);
            }
        });
        return returnList;
    }



    public List<CoverPrice> insertCoverPrice(List<CoverPrice> list) throws SQLException, IOException, ClassNotFoundException {
        log.info("insertCoverPrice");
        List<CoverPrice> returnList = new ArrayList<>();
        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(Locale.getDefault());
        unusualSymbols.setDecimalSeparator('.');
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
                log.debug("insert fail "+el);
                returnList.add(el);
            }
        });
        return returnList;
    }

    public List<PriceParameters> insertPriceParameters(List<PriceParameters> list) throws SQLException, IOException, ClassNotFoundException {
        log.info("insertPriceParameters");
        AtomicReference<Boolean> flag= new AtomicReference<>(true);
        List<PriceParameters> returnList = new ArrayList<>();
        list.stream().forEach(el -> {
            try {
                checkNotNullObject(el.getCoverPrice());
                checkListIsNotEmpty(el.getCoverPrice());
                el.getCoverPrice().stream().forEach(e2 -> {
                    try {
                        checkNotNullObject(getCoverPriceByID(e2.getId()));
                        this.execute(
                                String.format(
                                        DB_INSERT,
                                        COVERLINK,
                                        COVERLINK_FIELDS,
                                        String.format(
                                                COVERLINK_INSERT_FORMAT, el.getId(), e2.getId()
                                        )
                                )
                        );
                    } catch (Exception e) {
                        log.error(e);
                        flag.set(false);
                    }
                        }
                );
                checkTrue(flag.get());
                this.execute(
                        String.format(
                                DB_INSERT,
                                el.getClass().getSimpleName().toUpperCase(),
                                PRICEPARAMETERS_FIELDS,
                                String.format(
                                        PRICEPARAMETERS_INSERT_FORMAT, el.getId(), el.getPagePrice(),
                                        el.getWorkPrice(), el.getValidFromDate(), el.getValidToDate()
                                )
                        )
                );
                log.debug("will add to db " + el);
            }catch (SQLException | IOException | ClassNotFoundException e){
                log.error(e);
                log.debug("insert fail "+el);
                returnList.add(el);
            } catch (Exception e){
                log.error(e);
                log.debug("incorrect CoverPrice "+el);
            }
        });
        return returnList;
    }

    public List<Order> insertOrder(List<Order> list) throws SQLException, IOException, ClassNotFoundException {
        log.info("insertOrder");
        List<Order> returnList = new ArrayList<>();
        list.stream().forEach(el -> {
            try {
                checkNotNullObject(getEmployeeById(el.getBookMaker().getId()));
                checkNotNullObject(getEmployeeById(el.getBookEditor().getId()));
                checkNotNullObject(getPriceParametersByID(el.getBookPriceParameters().getId()));
                this.execute(
                        String.format(
                                DB_INSERT,
                                el.getClass().getSimpleName().toUpperCase(),
                                ORDER_FIELDS,
                                String.format(
                                        ORDER_INSERT_FORMAT, el.getId(), el.getAuthor().getId(), el.getTitle(), el.getNumberOfPages(),
                                        el.getOrderDate(),el.getCoverType(),el.getBookMaker().getId(),el.getBookEditor().getId(),el.getBookPriceParameters().getId(),
                                        el.getFinalNumberOfPages(),el.getNumberOfCopies(),el.getPrice(),el.getBookStatus()
                                )
                        )
                );
                log.debug("will add to db "+el);
            } catch (SQLException | IOException | ClassNotFoundException e){
                log.error(e);
                log.debug("insert fail "+el);
                returnList.add(el);
            } catch (Exception e){
                log.error(e);
                log.debug("incorrect Employee or PriceParameters "+el);
                returnList.add(el);
            }
        });
        return returnList;
    }

    public List<Corrections> insertCorrections(List<Corrections> list) throws SQLException, IOException, ClassNotFoundException {
        log.info("insertCorrections");
        List<Corrections> returnList = new ArrayList<>();
        list.stream().forEach(el -> {
            try {
                checkNotNullObject(getOrderByID(el.getOrder().getId()));
                checkNotNullObject(getMeetingByID(el.getMeet().getId()));
                this.execute(
                        String.format(
                                DB_INSERT,
                                el.getClass().getSimpleName().toUpperCase(),
                                CORRECTIONS_FIELDS,
                                String.format(
                                        CORRECTIONS_INSERT_FORMAT, el.getId(), el.getPage(), el.getTextBefore(), el.getTextAfter(),
                                        el.getComment(), el.getOrder().getId(), el.getMeet().getId(), el.getStatus()
                                )
                        )
                );
                log.debug("will add to db "+el);
            } catch (SQLException | IOException | ClassNotFoundException e){
                log.error(e);
                log.debug("insert fail "+el);
                returnList.add(el);
            } catch (Exception e){
                log.error(e);
                log.debug("incorrect Order or Meeting "+el);
                returnList.add(el);
            }
        });
        return returnList;
    }

    public List<Book> insertBook(List<Book> list) throws IOException {
        log.info("insertBook");
        List<Book> returnList = new ArrayList<>();
        list.stream().forEach(el -> {
            try {
                checkNotNullObject(getAuthorById(el.getAuthor().getId()));
                this.execute(
                        String.format(
                                DB_INSERT,
                                el.getClass().getSimpleName().toUpperCase(),
                                BOOK_FIELDS,
                                String.format(
                                        BOOK_INSERT_FORMAT, el.getId(), el.getAuthor().getId(), el.getTitle(), el.getNumberOfPages()
                                )
                        )
                );
                log.debug("will add to db "+el);
            } catch (SQLException | IOException | ClassNotFoundException e){
                log.error(e);
                log.debug("insert fail "+el);
                returnList.add(el);
            } catch (Exception e){
                log.error(e);
                log.debug("incorrect Author "+el);
                returnList.add(el);
            }
        });
        return returnList;
    }

    public boolean deleteFile(String str) {
        try {
            this.execute(String.format(DB_TRUNCATE, str));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
        return false;
    }

    public boolean dropFile(String str) {
        try {
            this.execute(String.format(DB_DROP, str));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
        return false;
    }

    public boolean updatePeople(People people)  {
        log.info("updatePeople");
        try {
            this.execute(String.format(
                    DB_UPDATE, LOWER_PEOPLE,
                    String.format(PEOPLE_UPDATE_FORMAT,people.getFirstName(), people.getSecondName(), people.getLastName(), people.getPhone()),
                    people.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
        return false;
    }

    public boolean updateAuthor(Author el)  {
        log.info("updateAuthor");
        try {
            this.execute(String.format(
                    DB_UPDATE, LOWER_AUTHOR,
                    String.format(AUTHOR_UPDATE_FORMAT,el.getFirstName(), el.getSecondName(), el.getLastName(), el.getPhone(),
                            el.getEmail(), el.getDegree(), el.getOrganization()),
                    el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
        return false;
    }

    public boolean updateEmployee(Employee el)  {
        log.info("updateEmployee");
        try {
            this.execute(String.format(
                    DB_UPDATE, LOWER_EMPLOYEE,
                    String.format(EMPLOYEE_UPDATE_FORMAT,el.getFirstName(), el.getSecondName(), el.getLastName(), el.getPhone(),
                            el.getInn(), el.getWorkRecordBook(), el.getEmplpyeeType()),
                    el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
        return false;
    }

    public boolean updateMeeting(Meeting el)  {
        log.info("updateMeeting");
        try {
            this.execute(String.format(
                    DB_UPDATE, LOWER_MEETING,
                    String.format(MEETING_UPDATE_FORMAT, el.getMeetDate(), el.getAuthorAgreement(), el.getEditorAgreement()),
                    el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
        return false;
    }

    public boolean updateCoverPrice(CoverPrice el)  {
        log.info("updateCoverPrice");
        try {
            this.execute(String.format(
                    DB_UPDATE, LOWER_COVERPRICE,
                    String.format(COVERPRICE_UPDATE_FORMAT,el.getCoverType(), el.getPrice()),
                    el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
        return false;
    }

    public boolean updateOrder(Order el)  {
        log.info("updateOrder");
        try {
            checkNotNullObject(getEmployeeById(el.getBookMaker().getId()));
            checkNotNullObject(getEmployeeById(el.getBookEditor().getId()));
            checkNotNullObject(getPriceParametersByID(el.getBookPriceParameters().getId()));
            this.execute(String.format(
                    DB_UPDATE, LOWER_ORDER,
                    String.format(ORDER_UPDATE_FORMAT,el.getAuthor().getId(), el.getTitle(), el.getNumberOfPages(),
                            el.getOrderDate(),el.getCoverType(),el.getBookMaker().getId(),el.getBookEditor().getId(),
                            el.getBookPriceParameters().getId(),el.getFinalNumberOfPages(),el.getNumberOfCopies(),
                            el.getPrice(),el.getBookStatus()),
                    el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
        return false;
    }

    public boolean updateCorrections(Corrections el)  {
        log.info("updateCorrections");
        try {
            checkNotNullObject(getOrderByID(el.getOrder().getId()));
            checkNotNullObject(getMeetingByID(el.getMeet().getId()));
            this.execute(String.format(
                    DB_UPDATE, LOWER_CORRECTIONS,
                    String.format(CORRECTIONS_UPDATE_FORMAT,el.getPage(), el.getTextBefore(), el.getTextAfter(),
                            el.getComment(), el.getOrder().getId(), el.getMeet().getId(), el.getStatus()),
                    el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
        return false;
    }

    public boolean updateBook(Book el)  {
        log.info("updateBook");
        try {
            checkNotNullObject(getAuthorById(el.getAuthor().getId()));
            this.execute(String.format(
                    DB_UPDATE, LOWER_BOOK,
                    String.format(BOOK_UPDATE_FORMAT,el.getAuthor().getId(), el.getTitle(), el.getNumberOfPages()),
                    el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
        return false;
    }

    public <T> boolean deleteObj(Class cl, T obj) {
        switch (cl.getSimpleName().toLowerCase()) {
            case Constants.CORRECTIONS:
                Corrections corObj = (Corrections) obj;
                return deleteCorrections(corObj);
            case Constants.PEOPLE:
                People peoplObj = (People) obj;
                return deletePeople(peoplObj);
            case Constants.BOOK:
                Book bookObj = (Book) obj;
                return deleteBook(bookObj);
            case Constants.EMPLOYEE:
                Employee emplObj = (Employee) obj;
                return deleteEmployee(emplObj);
            case Constants.PRICEPARAMETERS:
                PriceParameters priceObj = (PriceParameters) obj;
                return deletePriceParameters(priceObj);
            case Constants.ORDER:
                Order ordObj = (Order) obj;
                return deleteOrder(ordObj);
            case Constants.MEETING:
                Meeting meetObj = (Meeting) obj;
                return deleteMeeting(meetObj);
            case Constants.AUTHOR:
                Author authObj = (Author) obj;
                return deleteAuthor(authObj);
            case Constants.COVERPRICE:
                CoverPrice covObj = (CoverPrice) obj;
                return deleteCoverPrice(covObj);
            default:
                log.debug("default case");
                return false;
        }
    }

    public boolean deleteCorrections(Corrections el) {
        log.info("deleteCorrections");
        try {
            this.execute(String.format(
                    DB_DELETE_BY_ID, LOWER_CORRECTIONS, el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
        return false;
    }

    public boolean deletePeople(People el) {
        log.info("deletePeople");
        try {
            this.execute(String.format(
                    DB_DELETE_BY_ID, LOWER_PEOPLE, el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
        return false;
    }

    public boolean deleteBook(Book el) {
        log.info("deleteBook");
        try {
            this.execute(String.format(
                    DB_DELETE_BY_ID, LOWER_BOOK, el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
        return false;
    }

    public boolean deleteEmployee(Employee el) {
        log.info("deleteEmployee");
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT_WHERE, Order.class.getSimpleName().toUpperCase(),ORDER_BOOK_MAKER,el.getId()));
            checkFalse(set.next());
            ResultSet set2 = getResultSet(String.format(DB_SELECT_WHERE, Order.class.getSimpleName().toUpperCase(),ORDER_BOOK_EDITOR,el.getId()));
            checkFalse(set2.next());
            this.execute(String.format(
                    DB_DELETE_BY_ID, LOWER_EMPLOYEE, el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        } catch (Exception e){
            log.error(e);
        }
        return false;
    }

    public boolean deletePriceParameters(PriceParameters el) {
        log.info("deletePriceParameters");
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT_WHERE, Order.class.getSimpleName().toUpperCase(),ORDER_BOOK_PRICE_PARAMETERS,el.getId()));
            checkFalse(set.next());
            this.execute(String.format(
                    DB_DELETE_BY_ID, LOWER_PRICEPARAMETERS, el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        } catch (Exception e){
            log.error(e);
        }
        return false;
    }

    public boolean deleteOrder(Order el) {
        log.info("deleteOrder");
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT_WHERE, Corrections.class.getSimpleName().toUpperCase(),CORRECTIONS_ORDER,el.getId()));
            checkFalse(set.next());
            this.execute(String.format(
                    DB_DELETE_BY_ID, LOWER_ORDER, el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        } catch (Exception e){
            log.error(e);
        }
        return false;
    }

    public boolean deleteMeeting(Meeting el) {
        log.info("deleteMeeting");
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT_WHERE, Corrections.class.getSimpleName().toUpperCase(),CORRECTIONS_MEET,el.getId()));
            checkFalse(set.next());
            this.execute(String.format(
                    DB_DELETE_BY_ID, LOWER_MEETING, el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        } catch (Exception e){
            log.error(e);
        }
        return false;
    }

    public boolean deleteAuthor(Author el) {
        log.info("deleteAuthor");
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT_WHERE, Book.class.getSimpleName().toUpperCase(),BOOK_AUTHOR,el.getId()));
            checkFalse(set.next());
            ResultSet set2 = getResultSet(String.format(DB_SELECT_WHERE, Order.class.getSimpleName().toUpperCase(),BOOK_AUTHOR,el.getId()));
            checkFalse(set2.next());
            this.execute(String.format(
                    DB_DELETE_BY_ID, LOWER_AUTHOR, el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        } catch (Exception e){
            log.error(e);
        }
        return false;
    }

    public boolean deleteCoverPrice(CoverPrice el) {
        log.info("deleteCoverPrice");
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT_WHERE, COVERLINK,COVERLINK_COVERPRICE,el.getId()));
            checkFalse(set.next());
            this.execute(String.format(
                    DB_DELETE_BY_ID, LOWER_COVERPRICE, el.getId()
            ));
            return true;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        } catch (Exception e){
            log.error(e);
        }
        return false;
    }

    public long getMaxId (Class cl) {
        try {
            String tableName = cl.getSimpleName().toUpperCase();
            ResultSet set = getResultSet(String.format(DB_SELECT_MAX_ID, tableName, tableName));
            set.next();
            return set.getLong(ID);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
        return -1;

    }

    public Employee createDefaultEmloyee(){
        Employee employee = new Employee();
        employee.setId(DEFAULT_ID);
        employee.setFirstName(DEFAULT_NAME);
        employee.setSecondName(DEFAULT_NAME);
        employee.setLastName(DEFAULT_NAME);
        employee.setPhone(DEFAULT_PHONE);
        employee.setInn(DEFAULT_INN);
        employee.setWorkRecordBook(DEFAUL_BOOK);
        employee.setEmplpyeeType(EmployeeType.ADMIN);
        return employee;
    }

    public PriceParameters createDefaultPriceParameters() throws IOException {
        try {
            List<CoverPrice> list = new ArrayList<>();
            createDefaultCoverPrice();
            list.add(getCoverPriceByID(DEFAULT_ID));
            PriceParameters priceParameters = new PriceParameters();
            priceParameters.setId(DEFAULT_ID);
            priceParameters.setPagePrice(DEFAULT_PRICE);
            priceParameters.setCoverPrice(list);
            priceParameters.setWorkPrice(DEFAULT_PRICE);
            priceParameters.setValidFromDate(DEFAULT_DATE);
            priceParameters.setValidToDate(DEFAULT_DATE);
            return priceParameters;
        } catch (SQLException | ClassNotFoundException e) {
            log.error(e);
        }
        return null;
    }

    public CoverPrice createDefaultCoverPrice(){
        CoverPrice coverPrice = new CoverPrice();
        coverPrice.setId(DEFAULT_ID);
        coverPrice.setCoverType(CoverType.PAPERBACK);
        coverPrice.setCoverType(CoverType.PAPERBACK);
        coverPrice.setPrice(0.0);
        return coverPrice;
    }

    public Meeting createDefaultMeeting(){
        Meeting meet = null;
        try {
            meet = getMeetingByID(DEFAULT_ID);
            checkNotNullObject(meet);
        } catch (Exception e) {
            log.error(e);
            meet = new Meeting();
            meet.setId(DEFAULT_ID);
            meet.setMeetDate(DEFAULT_DATE);
            meet.setAuthorAgreement(false);
            meet.setEditorAgreement(false);
            List<Meeting> list = new ArrayList<>();
            list.add(meet);
            insertMeeting(list);
        } finally {
            return meet;
        }
    }

    public <T> void checkNotNullObject (T obj) throws Exception {
        if (obj == null) throw new Exception(EXCEPTION_OBJECT_IS_NULL);
    }

    public <T> void checkListIsNotEmpty (List<T> list) throws Exception {
        if (list.isEmpty()) throw new Exception(EXCEPTION_LIST_IS_EMPTY);
    }

    public void checkTrue (boolean result) throws Exception {
        if (!result) throw new Exception(EXCEPTION_RESULT_FALSE);
    }

    public void checkFalse (boolean result) throws Exception {
        if (result) throw new Exception(EXCEPTION_RESULT_TRUE);
    }

    public <T> void checkNullObject (T obj) throws Exception {
        if (obj != null) throw new Exception(EXCEPTION_OBJECT_IS_NOT_NULL);
    }

    //Author

    @Override
    public boolean alterBook (long authorId, long id, String title, int numberOfPages) {
        try {
            checkNotNullObject(getAuthorById(authorId));
            Author author = getAuthorById(authorId);
            Book book = new Book();
            book.setId(id);
            book.setAuthor(author);
            book.setTitle(title);
            book.setNumberOfPages(numberOfPages);
            if (getBookByID(id) != null) {
                log.info("Update Book");
                return updateBook(book);
            } else {
                log.info("Insert Book");
                List<Book> list = new ArrayList<>();
                list.add(book);
                return insertBook(list).isEmpty();
            }
        }catch (IOException e){
            log.error(e);
            return false;
        }catch (Exception e) {
            log.error(e);
            log.info("There is not such Author");
            return false;
        }
    }

    @Override
    public Optional<Order> makeOrder (long id, String orderDate, String coverType, int numberOfCopies){
        try {
            checkNotNullObject(getBookByID(id));
            Book book = getBookByID(id);
            Order order = new Order();
            order.setId(book.getId());
            order.setAuthor(book.getAuthor());
            order.setTitle(book.getTitle());
            order.setNumberOfPages(book.getNumberOfPages());
            order.setFinalNumberOfPages(book.getNumberOfPages());
            order.setOrderDate(orderDate);
            order.setCoverType(CoverType.valueOf(coverType));
            List<Employee> listEmployee = new ArrayList<>();
            listEmployee.add(createDefaultEmloyee());
            insertEmployee(listEmployee);
            List<CoverPrice> listCoverPrice = new ArrayList<>();
            listCoverPrice.add(createDefaultCoverPrice());
            insertCoverPrice(listCoverPrice);
            List<PriceParameters> listPriceParameters = new ArrayList<>();
            listPriceParameters.add(createDefaultPriceParameters());
            insertPriceParameters(listPriceParameters);
            order.setBookMaker(getEmployeeById(DEFAULT_ID));
            order.setBookEditor(getEmployeeById(DEFAULT_ID));
            order.setBookPriceParameters(getPriceParametersByID(DEFAULT_ID));
            order.setNumberOfCopies(numberOfCopies);
            order.setBookStatus(BookStatus.UNTOUCHED);
            return Optional.of(order);
        }catch (IOException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
            log.info("There is not such Book");
        }
        return Optional.empty();


    }

    @Override
    public boolean saveOrderInformation (long id, String orderDate, String coverType, int numberOfCopies) {
        try {
            Order order = makeOrder  (id,  orderDate,  coverType,  numberOfCopies).orElse(null);
            checkNotNullObject(order);
            List<Order> list = new ArrayList<>();
            list.add(order);
            return (insertOrder(list).isEmpty());
        } catch (IOException  e) {
            log.error(e);
        } catch (Exception e){
            log.error(e);
            log.info("Order is null");
        }
        return false;
    }

    @Override
    public double calculateCost (long orderId) {
        try {
            Order order = getOrderByID(orderId);
            checkNotNullObject(order);
            double result = 0;
            order.setBookPriceParameters(selectPriceParameters(order.getOrderDate()).orElse(null));
            log.debug(selectPriceParameters(order.getOrderDate()).orElse(null));
            if (order.getBookPriceParameters() != null){
                long id = order.getBookPriceParameters().getId();
                double work = calculateEditorWorkCost (id, order.getFinalNumberOfPages());
                double print = calculatePrintingCost (id, order.getFinalNumberOfPages());
                double cover = calculateCoverCost (id, order.getCoverType());
                result = (work > -1 && print>-1 && cover>-1) ? (work+print+cover)*order.getNumberOfCopies() : -1;
                log.debug((result>-1) ? "calculate cost done" : "there is truble with PriceParamets");
                order.setPrice(result);
                updateOrder(order);
            }
            return order.getPrice();
        }catch (IOException | SQLException | ClassNotFoundException e){
            log.error(e);
        } catch (Exception e){
            log.error(e);
            log.debug("There is not such Order");
        }
        return -1;
    }

    @Override
    public Optional<PriceParameters> selectPriceParameters(String date) {
        List<PriceParameters> list = new ArrayList<>();
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT_ALL, PriceParameters.class.getSimpleName().toUpperCase()));
            while (set.next()) {
                PriceParameters priceParameters = new PriceParameters();
                priceParameters.setId(set.getLong(ID));
                priceParameters.setPagePrice(set.getDouble(PRICEPARAMETERS_PAGE_PRICE));
                ResultSet set2 = getResultSet(String.format(COVERLINK_GET_ID, set.getLong(ID)));
                List<CoverPrice> coverPriceList = new ArrayList<>();
                while (set2.next()) {
                    CoverPrice coverPrice = getCoverPriceByID(set2.getLong(COVERLINK_COVERPRICE));
                    coverPriceList.add(coverPrice);
                }
                priceParameters.setCoverPrice(coverPriceList);
                priceParameters.setWorkPrice(set.getDouble(PRICEPARAMETERS_WORK_PRICE));
                priceParameters.setValidFromDate(set.getString(PRICEPARAMETERS_VALID_FROM_DATE));
                priceParameters.setValidToDate(set.getString(PRICEPARAMETERS_VALID_TO_DATE));
                list.add(priceParameters);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
        list = list.stream().filter(el -> belongInterval(el.getValidFromDate(), el.getValidToDate(), date)).collect(Collectors.toList());
        log.info((list.isEmpty()) ? "there is not suit PriceParameters" : "PriceParamets selected");
        return (list.isEmpty()) ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public boolean belongInterval (String start, String end, String date) {
        try {
            SimpleDateFormat dateForm = new SimpleDateFormat(DATE_PATTERN);
            Date dstart = dateForm.parse(start);
            Date dend = dateForm.parse(end);
            Date ddate = dateForm.parse(date);
            if ((dstart.before(ddate) || dstart.equals(ddate) ) && (dend.after(ddate) || dend.equals(ddate))){
                return true;
            }else{
                return false;
            }
        }catch (ParseException e){
            log.error(e);
            return false;
        }
    }

    @Override
    public double calculateEditorWorkCost (long idPriceParameters, int numberOfPages) {
        try {
            PriceParameters priceParameters = getPriceParametersByID(idPriceParameters);
            return priceParameters.getWorkPrice() * numberOfPages;
        }catch (IOException | SQLException | ClassNotFoundException e){
            log.error(e);
            return -1;
        }
    }

    @Override
    public double calculatePrintingCost (long idPriceParameters, int numberOfPages){
        try {
            PriceParameters priceParameters = getPriceParametersByID(idPriceParameters);
            return priceParameters.getPagePrice() * numberOfPages;
        }catch (IOException | SQLException | ClassNotFoundException e){
            log.error(e);
            return -1;
        }
    }

    @Override
    public double calculateCoverCost (long idPriceParameters, CoverType coverType){
        try {
            PriceParameters priceParameters = getPriceParametersByID(idPriceParameters);
            List<CoverPrice> coverPriceList = priceParameters.getCoverPrice().stream().filter(el -> {
                try {
                    return getCoverPriceByID(el.getId()).getCoverType() == coverType;
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    log.error(e);
                    return false;
                }
            }).collect(Collectors.toList());
            log.debug(coverPriceList);
            try{
                checkListIsNotEmpty(coverPriceList);
                long actualId = coverPriceList.get(0).getId();
                return getCoverPriceByID(actualId).getPrice();
            }catch (Exception e){
                log.error(e);
                log.debug("There is not suit CoverPrice");
                return -1;
            }
        }catch (IOException | SQLException | ClassNotFoundException e){
            log.error(e);
            return -1;
        }
    }

    @Override
    public boolean takeAwayOrder (long id) {
        try {
            Order order = getOrderByID(id);
            return deleteOrder(order);
        }catch (IOException | SQLException | ClassNotFoundException e){
            log.error(e);
            return false;
        }
    }

    @Override
    public List<Corrections> getListOfCorrections (long authorId) {
        try {
            checkNotNullObject(getAuthorById(authorId));
            List<Order> orderList = getListOfAuthorOrder(authorId);
            List<Corrections> correctionsList = new ArrayList<>();
            ResultSet set = getResultSet(String.format(DB_SELECT_ALL, Corrections.class.getSimpleName().toUpperCase()));
            try {
                while (set.next()) {
                    Corrections corrections = new Corrections();
                    corrections.setId(set.getLong(ID));
                    corrections.setPage(set.getInt(CORRECTIONS_PAGE));
                    corrections.setTextBefore(set.getString(CORRECTIONS_TEXT_BEFORE));
                    corrections.setTextAfter(set.getString(CORRECTIONS_TEXT_AFTER));
                    corrections.setComment(set.getString(CORRECTIONS_COMMENT));
                    corrections.setOrder(getOrderByID(set.getLong(CORRECTIONS_ORDER)));
                    corrections.setMeet(getMeetingByID(set.getLong(CORRECTIONS_MEET)));
                    corrections.setStatus(CorrectionsStatus.valueOf(set.getString(CORRECTIONS_STATUS)));
                    correctionsList.add(corrections);
                }
            }catch (SQLException e){
                log.error(e);
            }
            correctionsList = correctionsList.stream().filter(el -> orderList.stream().anyMatch(e2 -> e2.getId() == el.getOrder().getId())).collect(Collectors.toList());
            return correctionsList;
        }catch (IOException e){
            log.error(e);
            return new ArrayList<Corrections>();
        } catch (Exception e){
            log.error(e);
            log.debug("Author is null");
            return new ArrayList<Corrections>();
        }
    }

    @Override
    public List<Corrections> getListOfCorrectionsToOrder (long orderId) {
        try {
            checkNotNullObject(getOrderByID(orderId));
            List<Corrections> correctionsList = new ArrayList<>();
            ResultSet set = getResultSet(String.format(DB_SELECT_ALL, Corrections.class.getSimpleName().toUpperCase()));
            try {
                while (set.next()) {
                    Corrections corrections = new Corrections();
                    corrections.setId(set.getLong(ID));
                    corrections.setPage(set.getInt(CORRECTIONS_PAGE));
                    corrections.setTextBefore(set.getString(CORRECTIONS_TEXT_BEFORE));
                    corrections.setTextAfter(set.getString(CORRECTIONS_TEXT_AFTER));
                    corrections.setComment(set.getString(CORRECTIONS_COMMENT));
                    corrections.setOrder(getOrderByID(set.getLong(CORRECTIONS_ORDER)));
                    corrections.setMeet(getMeetingByID(set.getLong(CORRECTIONS_MEET)));
                    corrections.setStatus(CorrectionsStatus.valueOf(set.getString(CORRECTIONS_STATUS)));
                    correctionsList.add(corrections);
                }
            }catch (SQLException e){
                log.error(e);
            }
            correctionsList = correctionsList.stream().filter(el -> orderId == el.getOrder().getId()).collect(Collectors.toList());
            return correctionsList;
        }catch (IOException e){
            log.error(e);
            return new ArrayList<Corrections>();
        } catch (Exception e){
            log.error(e);
            log.debug("Order is null");
            return new ArrayList<Corrections>();
        }
    }

    @Override
    public List<Order> getListOfAuthorOrder (long authorId) {
        try{
            checkNotNullObject(getAuthorById(authorId));
            List<Order> list = new ArrayList<>();
            ResultSet set = getResultSet(String.format(DB_SELECT_ALL, Order.class.getSimpleName().toUpperCase()));
            try {
                while(set.next()) {
                    Order order = new Order();
                    order.setId(set.getLong(ID));
                    order.setAuthor(getAuthorById(set.getLong(BOOK_AUTHOR)));
                    order.setTitle(set.getString(BOOK_TITLE));
                    order.setNumberOfPages(set.getInt(BOOK_NUMBER_OF_PAGES));
                    order.setOrderDate(set.getString(ORDER_DATE));
                    order.setCoverType(CoverType.valueOf(set.getString(ORDER_COVER_TYPE)));
                    order.setBookMaker(getEmployeeById(set.getLong(ORDER_BOOK_MAKER)));
                    order.setBookEditor(getEmployeeById(set.getLong(ORDER_BOOK_EDITOR)));
                    order.setBookPriceParameters(getPriceParametersByID(set.getLong(ORDER_BOOK_PRICE_PARAMETERS)));
                    order.setFinalNumberOfPages(set.getInt(ORDER_FINAL_NUMBER_OF_PAGES));
                    order.setNumberOfCopies(set.getInt(ORDER_NUMBER_OF_COPIES));
                    order.setPrice(set.getDouble(ORDER_PRICE));
                    order.setBookStatus(BookStatus.valueOf(set.getString(ORDER_BOOK_STATUS)));
                    list.add(order);
                }
            }catch (SQLException e){
                log.error(e);
            }
            list = list.stream().filter(el -> el.getAuthor().getId() == authorId).collect(Collectors.toList());
            return list;
        }catch (IOException e){
            log.error(e);
            return new ArrayList<>();
        }
        catch (Exception e){
            log.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Book> getListOfAuthorBook (long authorId) {
        try{
            checkNotNullObject(getAuthorById(authorId));
            List<Book> list = new ArrayList<>();
            ResultSet set = getResultSet(String.format(DB_SELECT_ALL, Book.class.getSimpleName().toUpperCase()));
            try {
                while(set.next()) {
                    Book book = new Book();
                    book.setId(set.getLong(ID));
                    book.setAuthor(getAuthorById(set.getLong(BOOK_AUTHOR)));
                    book.setTitle(set.getString(BOOK_TITLE));
                    book.setNumberOfPages(set.getInt(BOOK_NUMBER_OF_PAGES));
                    list.add(book);
                }
            }catch (SQLException e){
                log.error(e);
            }
            list = list.stream().filter(el -> el.getAuthor().getId() == authorId).collect(Collectors.toList());
            return list;
        }catch (IOException e){
            log.error(e);
            return new ArrayList<Book>();
        }catch (Exception e){
            log.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public boolean agreementCorrection (long correctionId){
        try{
            checkNotNullObject(getCorrectionsByID(correctionId));
            Corrections correction = getCorrectionsByID(correctionId);
            correction.setStatus(CorrectionsStatus.ACCEPTED);
            boolean result = updateCorrections(correction);
            checkTrue(result);
            log.info("Status changed");
            return result;
        }catch (Exception e){
            log.error(e);
            log.info("Status changed fail");
            return false;
        }
    }

    @Override
    public boolean declineCorrection (long correctionId, String comment){
        try{
            checkNotNullObject(getCorrectionsByID(correctionId));
            Corrections correction = getCorrectionsByID(correctionId);
            correction.setComment(comment);
            correction.setStatus(CorrectionsStatus.WAIT_EDITOR_AGR);
            boolean result = updateCorrections(correction);
            checkTrue(result);
            log.info("Comment added, status changed");
            return result;
        }catch (Exception e){
            log.error(e);
            log.info("Comment added and status changed fail");
            return false;
        }
    }

    public long getMeetingInformation (long correctionId){
        try{
            checkNotNullObject(getCorrectionsByID(correctionId));
            Corrections correction = getCorrectionsByID(correctionId);
            Meeting meet = correction.getMeet();
            checkNotNullObject(meet);
            log.info(getMeetingByID(meet.getId()));
            return meet.getId();
        }catch (Exception e){
            log.error(e);
            log.info("Comment added and status changed fail");
            return -1;
        }
    }

    @Override
    public boolean agreementMeeting (long meetingId){
        try{
            checkNotNullObject(getMeetingByID(meetingId));
            Meeting meet = getMeetingByID(meetingId);
            meet.setAuthorAgreement(true);
            boolean result = updateMeeting(meet);
            checkTrue(result);
            log.info("Status changed");
            return result;
        }catch (Exception e){
            log.error(e);
            log.info("Status changed fail");
            return false;
        }
    }

    @Override
    public boolean declineMeeting (long meetingId, String date){
        try{
            checkNotNullObject(getMeetingByID(meetingId));
            Meeting meet = getMeetingByID(meetingId);
            meet.setEditorAgreement(false);
            meet.setMeetDate(date);
            boolean result = updateMeeting(meet);
            checkTrue(result);
            log.info("Status changed");
            return result;
        }catch (Exception e){
            log.error(e);
            log.info("Status changed fail");
            return false;
        }
    }

    @Override
    public Optional<Author> addAuthor(long id,String firstName,String secondName,String lastName,String phone, String email,String degree,String organization){
        try {
            List<Author> list = new ArrayList<>();
            try {
                ResultSet set = getResultSet(String.format(DB_SELECT_BY_ID, Author.class.getSimpleName().toUpperCase(),id));
                while(set.next()) {
                    Author author = new Author();
                    author.setId(set.getLong(ID));
                    author.setFirstName(set.getString(PEOPLE_FIRST_NAME));
                    author.setSecondName(set.getString(PEOPLE_SECOND_NAME));
                    author.setLastName(set.getString(PEOPLE_LAST_NAME));
                    author.setPhone(set.getString(PEOPLE_PHONE));
                    author.setEmail(set.getString(AUTHOR_EMAIL));
                    author.setDegree(set.getString(AUTHOR_DEGREE));
                    author.setOrganization(set.getString(AUTHOR_ORGANIZATION));
                    list.add(author);
                }
            }catch (SQLException | IOException | ClassNotFoundException e){
                log.error(e);
            }
            Author author = new Author();
            setAuthor(author, id, firstName, secondName, lastName, phone, email, degree, organization);
            list.add(author);
            log.debug(author);
            insertAuthor(list);
            return Optional.of(author);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            log.error(e);
            return Optional.empty();
        }
    }

    @Override
    public void setAuthor(Author author, long id,String firstName,String secondName,String lastName,String phone, String email,String degree,String organization){
        author.setId(id);
        author.setFirstName(firstName);
        author.setSecondName(secondName);
        author.setLastName(lastName);
        author.setPhone(phone);
        author.setEmail(email);
        author.setDegree(degree);
        author.setOrganization(organization);
    }

    ////Editor

    @Override
    public List<Order> getOrderListWithoutEditor (){
        try {
            List<Order> list = new ArrayList<>();
            ResultSet set = getResultSet(String.format(DB_SELECT_ALL, Order.class.getSimpleName().toUpperCase()));
            try {
                while(set.next()) {
                    Order order = new Order();
                    order.setId(set.getLong(ID));
                    order.setAuthor(getAuthorById(set.getLong(BOOK_AUTHOR)));
                    order.setTitle(set.getString(BOOK_TITLE));
                    order.setNumberOfPages(set.getInt(BOOK_NUMBER_OF_PAGES));
                    order.setOrderDate(set.getString(ORDER_DATE));
                    order.setCoverType(CoverType.valueOf(set.getString(ORDER_COVER_TYPE)));
                    order.setBookMaker(getEmployeeById(set.getLong(ORDER_BOOK_MAKER)));
                    order.setBookEditor(getEmployeeById(set.getLong(ORDER_BOOK_EDITOR)));
                    order.setBookPriceParameters(getPriceParametersByID(set.getLong(ORDER_BOOK_PRICE_PARAMETERS)));
                    order.setFinalNumberOfPages(set.getInt(ORDER_FINAL_NUMBER_OF_PAGES));
                    order.setNumberOfCopies(set.getInt(ORDER_NUMBER_OF_COPIES));
                    order.setPrice(set.getDouble(ORDER_PRICE));
                    order.setBookStatus(BookStatus.valueOf(set.getString(ORDER_BOOK_STATUS)));
                    list.add(order);
                }
            }catch (SQLException e){
                log.error(e);
            }
            list = list.stream().filter(el -> el.getBookEditor() == null || el.getBookEditor().getId() == DEFAULT_ID).collect(Collectors.toList());
            return list;
        } catch (IOException e) {
            log.error(e);
            log.info("There is not information about orders");
            return new ArrayList<>();
        } catch (Exception e){
            log.error(e);
            log.info("There is not suit orders");
            return new ArrayList<>();
        }
    }

    @Override
    public boolean addBookEditor(long OrderId, long EmployeeId){
        try {
            Order order = getOrderByID(OrderId);
            Employee employee = getEmployeeById(EmployeeId);
            checkNotNullObject(order);
            checkNotNullObject(employee);
            order.setBookEditor(employee);
            log.debug(order);
            return updateOrder(order);
        } catch (IOException e) {
            log.error(e);
            return false;
        } catch (Exception e){
            log.error(e);
            return false;
        }
    }

    public boolean returnTo (long OrderId,BookStatus bookStatus){
        try {
            Order order = findOrder(OrderId).get();
            checkNotNullObject(order);
            order.setBookStatus(bookStatus);
            log.debug(order);
            return updateOrder(order);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean returnToAuthor (long OrderId){
        return returnTo(OrderId,BookStatus.WAIT_AUTHOR_CORRECTIONS);
    }

    @Override
    public boolean endEditing (long OrderId){
        try {
            Order order = getOrderByID(OrderId);
            checkNotNullObject(order);
            order.setBookStatus(BookStatus.MAKING);
            log.debug(order);
            return updateOrder(order);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public Optional<Corrections> sendCorrectionsToAuthor(long id, int page, String textBefore, String textAfter, String comment, long orderId, long meetingId){
        try {
            Corrections correction = new Corrections();
            correction.setId(id);
            correction.setPage(page);
            correction.setTextBefore(textBefore);
            correction.setTextAfter(textAfter);
            correction.setComment(comment);
            Order order = getOrderByID(orderId);
            checkNotNullObject(order);
            correction.setOrder(order);
            Meeting meet = getMeetingByID(meetingId);
            try{
                checkNotNullObject(meet);
            }catch(Exception e){
                log.error(e);
                meet = createDefaultMeeting();
            }
            correction.setMeet(meet);
            correction.setStatus(CorrectionsStatus.WAIT_AUTHOR_AGR);
            return Optional.of(correction);
        }catch (Exception e){
            log.error(e);
            return Optional.empty();
        }
    }

    @Override
    public boolean makeMeeting (long correctionsId, long id, String meetDate) {
        try {
            checkNullObject(getMeetingByID(id));
            Corrections corrections = getCorrectionsByID(correctionsId);
            Meeting meeting = new Meeting();
            meeting.setId(id);
            meeting.setMeetDate(meetDate);
            meeting.setAuthorAgreement(false);
            meeting.setEditorAgreement(true);
            corrections.setMeet(meeting);
            updateCorrections(corrections);
            List<Meeting> list = new ArrayList<>();
            list.add(meeting);
            insertMeeting(list);
            log.debug(corrections);
            return true;
        } catch (IOException e) {
            log.error(e);
            return false;
        } catch (Exception e){
            log.error(e);
            log.info("Meeting id already exist");
            return false;
        }
    }

////Maker

    @Override
    public boolean returnToEditor (long OrderId){
        return returnTo (OrderId, BookStatus.WAIT_EDITOR_AGR);
    }

    @Override
    public boolean takeForPrinting (long OrderId,long EmployeeId){
        try{
            Order order = (Order) getOrderByID(OrderId);
            Employee employee = getEmployeeById(EmployeeId);
            checkNotNullObject(order);
            checkNotNullObject(employee);
            order.setBookMaker(employee);
            log.debug(order);
            return updateOrder(order);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean markAsFinished (long OrderId){
        try{
            Order order = getOrderByID(OrderId);
            checkNotNullObject(order);
            order.setBookStatus(BookStatus.DONE);
            log.debug(order);
            return updateOrder(order);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

////Chief

    @Override
    public long countPublishedBooks (String startDate, String deadline){
        return countStatistic(startDate,deadline,BookStatus.DONE);
    }

    @Override
    public long countPrintingBooks (String startDate, String deadline){
        return countStatistic(startDate,deadline,BookStatus.MAKING);
    }

    @Override
    public long countEditingBooks (String startDate, String deadline){
        return countStatistic(startDate,deadline,BookStatus.EDITING);
    }

    public long countStatistic (String startDate, String deadline, BookStatus bookStatus){
        try {
            List<Order> list = new ArrayList<>();
            ResultSet set = getResultSet(String.format(DB_SELECT_ALL, Order.class.getSimpleName().toUpperCase()));
            try {
                while(set.next()) {
                    Order order = new Order();
                    order.setId(set.getLong(ID));
                    order.setAuthor(getAuthorById(set.getLong(BOOK_AUTHOR)));
                    order.setTitle(set.getString(BOOK_TITLE));
                    order.setNumberOfPages(set.getInt(BOOK_NUMBER_OF_PAGES));
                    order.setOrderDate(set.getString(ORDER_DATE));
                    order.setCoverType(CoverType.valueOf(set.getString(ORDER_COVER_TYPE)));
                    order.setBookMaker(getEmployeeById(set.getLong(ORDER_BOOK_MAKER)));
                    order.setBookEditor(getEmployeeById(set.getLong(ORDER_BOOK_EDITOR)));
                    order.setBookPriceParameters(getPriceParametersByID(set.getLong(ORDER_BOOK_PRICE_PARAMETERS)));
                    order.setFinalNumberOfPages(set.getInt(ORDER_FINAL_NUMBER_OF_PAGES));
                    order.setNumberOfCopies(set.getInt(ORDER_NUMBER_OF_COPIES));
                    order.setPrice(set.getDouble(ORDER_PRICE));
                    order.setBookStatus(BookStatus.valueOf(set.getString(ORDER_BOOK_STATUS)));
                    list.add(order);
                }
            }catch (SQLException e){
                log.error(e);
            }
            checkListIsNotEmpty(list);
            list = list.stream()
                    .filter(el->el.getBookStatus() == bookStatus)
                    .filter(el2->belongInterval(startDate,deadline,el2.getOrderDate()))
                    .collect(Collectors.toList());
            return list.size();
        } catch (Exception e) {
            log.error(e);
            return -1;
        }
    }

////Admin

    @Override
    public Optional<PriceParameters> setPriceParameters(long id, double pagePrice, List<CoverPrice> coverPrice, double workPrice, String validFromDate, String validToDate){
        PriceParameters priceParameters = new PriceParameters();
        priceParameters.setId(id);
        priceParameters.setPagePrice(pagePrice);
        priceParameters.setCoverPrice(coverPrice);
        priceParameters.setWorkPrice(workPrice);
        priceParameters.setValidFromDate(validFromDate);
        priceParameters.setValidToDate(validToDate);
        return Optional.of(priceParameters);
    }

    @Override
    public boolean addPriceParameters(long id, double pagePrice, List<CoverPrice> coverPrice, double workPrice, String validFromDate, String validToDate){
        try {
            checkNullObject(getPriceParametersByID(id));
            PriceParameters priceParameters = setPriceParameters(id, pagePrice, coverPrice, workPrice, validFromDate, validToDate).get();
            List<PriceParameters> list = new ArrayList<>();
            list.add(priceParameters);
            return (!insertPriceParameters(list).isEmpty());
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public Optional<CoverPrice> setCoverPrice(long id, String coverType, double price){
        CoverPrice coverPrice = new CoverPrice();
        coverPrice.setId(id);
        coverPrice.setCoverType(CoverType.valueOf(coverType));
        coverPrice.setPrice(price);
        return Optional.of(coverPrice);
    }

    @Override
    public boolean addCoverPrice(long id, String coverType, double price){
        try {
            checkNullObject(getCoverPriceByID(id));
            CoverPrice coverPrice = setCoverPrice(id, coverType, price).get();
            List<CoverPrice> list = new ArrayList<>();
            list.add(coverPrice);
            return (!insertCoverPrice(list).isEmpty());
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public Optional<Order> findOrder(long orderId){
        try {
            Order order = getOrderByID(orderId);
            checkNotNullObject(order);
            return Optional.of(order);
        }catch (Exception e) {
            log.error(e);
            return Optional.empty();
        }
    }
}
