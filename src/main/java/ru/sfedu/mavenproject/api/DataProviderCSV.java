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
import ru.sfedu.mavenproject.Constants;
import ru.sfedu.mavenproject.bean.*;
import ru.sfedu.mavenproject.bean.enums.BookStatus;
import ru.sfedu.mavenproject.bean.enums.CorrectionsStatus;
import ru.sfedu.mavenproject.bean.enums.CoverType;
import ru.sfedu.mavenproject.bean.enums.EmployeeType;
import ru.sfedu.mavenproject.utils.ConfigurationUtil;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.stream.Collectors;

import static ru.sfedu.mavenproject.Constants.*;

public class DataProviderCSV implements DataProvider {

    private static Logger log = LogManager.getLogger(DataProviderCSV.class);

    //Author

    @Override
    public boolean alterBook (long authorId, long id, String title, int numberOfPages) {
        try {
            checkNotNullObject(getPeopleByID(Author.class, authorId));
            Author author = (Author) getPeopleByID(Author.class, authorId);
            Book book = new Book();
            book.setId(id);
            book.setAuthor(author);
            book.setTitle(title);
            book.setNumberOfPages(numberOfPages);
            if (getBookByID(Book.class, id) != null) {
                log.info("Update Book");
                return updateBook(book);
            } else {
                log.info("Insert Book");
                List<Book> list = new ArrayList<>();
                list.add(book);
                return insertBook(list).isEmpty();
            }
        }catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e){
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
            Book book = (Book) getBookByID(Book.class, id);
            checkNotNullObject(book);
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
            insertPeople(Employee.class, listEmployee);
            List<CoverPrice> listCoverPrice = new ArrayList<>();
            listCoverPrice.add(createDefaultCoverPrice());
            insertCoverPrice(listCoverPrice);
            List<PriceParameters> listPriceParameters = new ArrayList<>();
            listPriceParameters.add(createDefaultPriceParameters());
            insertPriceParameters(listPriceParameters);
            order.setBookMaker((Employee) getPeopleByID(Employee.class, DEFAULT_ID));
            order.setBookEditor((Employee) getPeopleByID(Employee.class, DEFAULT_ID));
            order.setBookPriceParameters(getPriceParametersByID(DEFAULT_ID));
            order.setNumberOfCopies(numberOfCopies);
            order.setBookStatus(BookStatus.UNTOUCHED);
            return Optional.of(order);
        }catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
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
            Order order = makeOrder (id, orderDate,coverType, numberOfCopies).orElse(null);
            checkNotNullObject(order);
            List<Order> list = new ArrayList<>();
            list.add(order);
            return (insertOrder(list).isEmpty());
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException  e) {
            log.error(e);
        } catch (Exception e){
            log.error(e);
            log.info("Order is null");
        }
        return false;
    }

    @Override
    public double calculateCost (long orderId) {
        Order order = (Order) getBookByID(Order.class, orderId);
        try {
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
        }catch (IOException e){
            log.error(e);
        } catch (Exception e){
            log.error(e);
            log.debug("There is not such Order");
            return -1;
        }finally {
            return order.getPrice();
        }
    }

    @Override
    public Optional<PriceParameters> selectPriceParameters(String date) {
        try {
            List<PriceParameters> list = read(PriceParameters.class);
            list = list.stream().filter(el -> belongInterval(el.getValidFromDate(), el.getValidToDate(), date)).collect(Collectors.toList());
            log.info((list.isEmpty()) ? "there is not suit PriceParameters" : "PriceParamets selected");
            return (list.isEmpty()) ? Optional.empty() : Optional.of(list.get(0));
        }catch (IOException e){
            log.error(e);
            return Optional.empty();
        }
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
        }catch (IOException e){
            log.error(e);
            return -1;
        }
    }

    @Override
    public double calculatePrintingCost (long idPriceParameters, int numberOfPages){
        try {
            PriceParameters priceParameters = getPriceParametersByID(idPriceParameters);
            return priceParameters.getPagePrice() * numberOfPages;
        }catch (IOException e){
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
                } catch (IOException e) {
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
        }catch (IOException e){
            log.error(e);
            return -1;
        }
    }

    @Override
    public boolean takeAwayOrder (long id) {
        try {
            Order order = (Order) getBookByID(Order.class, id);
            return deleteOrder(order);
        }catch (CsvRequiredFieldEmptyException | IOException | CsvDataTypeMismatchException e){
            log.error(e);
            return false;
        }
    }

    @Override
    public List<Corrections> getListOfCorrections (long authorId) {
        try {
            checkNotNullObject(getPeopleByID(Author.class, authorId));
            List<Order> orderList = getListOfAuthorOrder(authorId);
            List<Corrections> correctionsList = read(Corrections.class);
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
            checkNotNullObject(getBookByID(Order.class, orderId));
            List<Corrections> correctionsList = read(Corrections.class);
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
            checkNotNullObject(getPeopleByID(Author.class, authorId));
            List<Order> list = read(Order.class);
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
            checkNotNullObject(getPeopleByID(Author.class, authorId));
            List<Book> list = read(Book.class);
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
            List<Author> list = read(Author.class);
            Author author = new Author();
            setAuthor(author, id, firstName, secondName, lastName, phone, email, degree, organization);
            list.add(author);
            log.debug(author);
            insertPeople(Author.class,list);
            return Optional.of(author);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
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
            List<Order> list = read(Order.class);
            list = list.stream().filter(el -> el.getBookEditor() == null || el.getBookEditor().getId() == 0).collect(Collectors.toList());
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
            Order order = (Order) getBookByID(Order.class,OrderId);
            Employee employee = (Employee) getPeopleByID(Employee.class,EmployeeId);
            checkNotNullObject(order);
            checkNotNullObject(employee);
            order.setBookEditor(employee);
            log.debug(order);
            return updateOrder(order);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
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
            Order order = (Order) getBookByID(Order.class, OrderId);
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
            Order order = (Order) getBookByID(Order.class, orderId);
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
            Order order = (Order) getBookByID(Order.class,OrderId);
            Employee employee = (Employee) getPeopleByID(Employee.class,EmployeeId);
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
            Order order = (Order) getBookByID(Order.class, OrderId);
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
            List<Order> list = read(Order.class);
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
            Order order = (Order) getBookByID(Order.class, orderId);
            checkNotNullObject(order);
            return Optional.of(order);
        }catch (Exception e) {
            log.error(e);
            return Optional.empty();
        }
    }

    //CRUD and helper methods

    public FileWriter commonWriter(Class cl) throws IOException {
        FileWriter writer = new FileWriter(getPath(cl));
        return writer;
    }

    public <T> void csvWriter(Class cl, List<T> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        CSVWriter csvWriter = new CSVWriter(commonWriter(cl));
        StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter)
                .withApplyQuotesToAll(false)
                .build();
        beanToCsv.write(list);
        csvWriter.close();
    }

    public <T> List<T> read(Class cl) throws IOException{
        FileReader fileReader;
        String path = getPath(cl);
        try{
            fileReader = new FileReader(path);
        }catch (FileNotFoundException  e){
            createFile(path);
            fileReader = new FileReader(path);
        }
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                .withType(cl)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<T> list = csvToBean.parse();
        return list;
    }

    public String getPath(Class cl) throws IOException {
        return ConfigurationUtil.getConfigurationEntry(PATH_CSV)
                + cl.getSimpleName().toLowerCase()
                + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION_CSV);
    }

    public <T extends People> Object getPeopleByID(Class cl, long id) throws IOException {
        List<T> list = this.read(cl);
        try {
            Object obj = list.stream()
                    .filter(e1 -> e1.getId() == id)
                    .findFirst().get();
            return obj;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    public <T extends Book> Object getBookByID(Class cl, long id) {
        try {
            List<T> list = this.read(cl);
            Object obj = list.stream()
                    .filter(e1 -> e1.getId() == id)
                    .findFirst().get();
            return obj;
        }catch (IOException | NoSuchElementException  e){
            log.error(e);
            return null;
        }
    }

    public Meeting getMeetingByID(long id) throws IOException {
        List<Meeting> list = this.read(Meeting.class);
        try {
            Meeting obj = list.stream()
                    .filter(e1 -> e1.getId() == id)
                    .findFirst().get();
            return obj;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    public Corrections getCorrectionsByID(long id) throws IOException {
        List<Corrections> list = this.read(Corrections.class);
        try {
            Corrections obj = list.stream()
                    .filter(e1 -> e1.getId() == id)
                    .findFirst().get();
            return obj;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    public PriceParameters getPriceParametersByID(long id) throws IOException {
        List<PriceParameters> list = this.read(PriceParameters.class);
        try {
            PriceParameters obj = list.stream()
                    .filter(e1 -> e1.getId() == id)
                    .findFirst().get();
            return obj;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    public CoverPrice getCoverPriceByID(long id) throws IOException {
        List<CoverPrice> list = this.read(CoverPrice.class);
        try {
            CoverPrice obj = list.stream()
                    .filter(e1 -> e1.getId() == id)
                    .findFirst().get();
            return obj;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    public String createFile(String path) throws IOException {
        File file = new File(path);
        file.createNewFile();
        return path;
    }

    //добавляет элемент к одному из двух массивов: 1 массив - не повторяющиеся, 2 - повторяющиеся
    public <T> void separateDuplicates( List<T> returnList, List<T> recordList, T obj) {
        if (recordList.stream().anyMatch(e2 -> e2.equals(obj))){
            log.debug("already exist in csv "+obj);
            returnList.add(obj);
        }else{
            log.debug("will add to csv "+obj);
            recordList.add(obj);
        }
    }

    public <T extends People> List<T> insertPeople(Class cl, List<T> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        List<T> returnList = new ArrayList<>();
        List<T> recordList = read(cl);
        list.stream().forEach(e1 -> {
            if (recordList.stream().anyMatch(e2 -> e2.equals(e1))){
                log.debug("already exist in csv "+e1);
            }else if (recordList.stream().anyMatch(e2 -> e2.getId() == e1.getId())){
                log.debug("id already exist in csv "+e1);
                returnList.add(e1);
            }else{
                log.debug("will add to csv "+e1);
                recordList.add(e1);
            }
        });
        try {
            csvWriter(cl,recordList);
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }finally {
            return returnList;
        }
    }

    public List<Meeting> insertMeeting(List<Meeting> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        List<Meeting> returnList = new ArrayList<>();
        List<Meeting> recordList = read(Meeting.class);
        list.stream().forEach(e1 -> {
            if (recordList.stream().anyMatch(e2 -> e2.equals(e1))){
                log.debug("already exist in csv "+e1);
            }else if (recordList.stream().anyMatch(e2 -> e2.getId() == e1.getId())){
                log.debug("id already exist in csv "+e1);
                returnList.add(e1);
            }else{
                log.debug("will add to csv "+e1);
                recordList.add(e1);
            }
        });
        try {
            csvWriter(Meeting.class,recordList);
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }finally {
            return returnList;
        }
    }

    public List<CoverPrice> insertCoverPrice(List<CoverPrice> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        List<CoverPrice> returnList = new ArrayList<>();
        List<CoverPrice> recordList = read(CoverPrice.class);
        list.stream().forEach(e1 -> {
            if (recordList.stream().anyMatch(e2 -> e2.equals(e1))){
                log.debug("already exist in csv "+e1);
            }else if (recordList.stream().anyMatch(e2 -> e2.getId() == e1.getId())){
                log.debug("id already exist in csv "+e1);
                returnList.add(e1);
            }else{
                log.debug("will add to csv "+e1);
                recordList.add(e1);
            }
        });
        try {
            csvWriter(CoverPrice.class, recordList);
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }finally {
            return returnList;
        }
    }

    public List<PriceParameters> insertPriceParameters(List<PriceParameters> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        List<PriceParameters> returnList = new ArrayList<>();
        List<PriceParameters> recordList = read(PriceParameters.class);
        list.stream().forEach(el -> {
            //проверка, есть ли элемент указанный в поле CoverPrice в соответствующем csv файле
            if (el.getCoverPrice().isEmpty() || el.getCoverPrice().stream().allMatch(e2 -> {
                try {
                    return getCoverPriceByID(e2.getId()) != null;
                } catch (IOException e) {
                    log.error(e);
                    return false;
                }
            })){
                log.debug("there is such CoverPrice");
                if (recordList.stream().anyMatch(e2 -> e2.equals(el))){
                    log.debug("already exist in csv "+el);
                }else if (recordList.stream().anyMatch(e2 -> e2.getId() == el.getId())) {
                    log.debug("id already exist in csv " + el);
                    returnList.add(el);
                }else{
                    log.debug("will add to csv "+el);
                    recordList.add(el);
                }
            }else{
                log.debug("there is not such CoverPrice");
                returnList.add(el);
            }
        });
        try {
            csvWriter(PriceParameters.class, recordList);
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }finally {
            return returnList;
        }
    }

    public List<Order> insertOrder(List<Order> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        List<Order> returnList = new ArrayList<>();
        List<Order> recordList = read(Order.class);
        list.stream().forEach(el -> {
            try {
                //проверка, есть ли элементы указанный в поле CoverPrice в соответствующем csv файле
                if ((el.getBookMaker() == null || getPeopleByID(Employee.class,el.getBookMaker().getId()) != null)
                && (el.getBookEditor() == null || getPeopleByID(Employee.class,el.getBookEditor().getId()) != null)
                && (el.getBookPriceParameters() == null || getPriceParametersByID(el.getBookPriceParameters().getId()) != null)){
                    log.debug("there is such BookMaker and BookEditor and PriceParameters");
                    if (recordList.stream().anyMatch(e2 -> e2.equals(el))){
                        log.debug("already exist in csv "+el);
                    }else if (recordList.stream().anyMatch(e2 -> e2.getId() == el.getId())) {
                        log.debug("id already exist in csv " + el);
                        returnList.add(el);
                    }else{
                        log.debug("will add to csv "+el);
                        recordList.add(el);
                    }
                }else{
                    log.debug("there is not such BookMaker or BookEditor or PriceParameters");
                    returnList.add(el);
                }
            } catch (IOException e) {
                log.error(e);
            }
        });
        try {
            csvWriter(Order.class, recordList);
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }finally {
            return returnList;
        }
    }

    public List<Corrections> insertCorrections(List<Corrections> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        List<Corrections> returnList = new ArrayList<>();
        List<Corrections> recordList = read(Corrections.class);
        list.stream().forEach(el -> {
            try {
                //проверка, есть ли элементы указанный в поле order и meet в соответствующем csv файле
                if ((el.getOrder() == null || getBookByID(Order.class,el.getOrder().getId()) != null)
                        && (el.getMeet() == null || getMeetingByID(el.getMeet().getId()) != null)){
                    log.debug("there is such Order and Meeting");
                    if (recordList.stream().anyMatch(e2 -> e2.equals(el))){
                        log.debug("already exist in csv "+el);
                    }else if (recordList.stream().anyMatch(e2 -> e2.getId() == el.getId())) {
                        log.debug("id already exist in csv " + el);
                        returnList.add(el);
                    }else{
                        log.debug("will add to csv "+el);
                        recordList.add(el);
                    }
                }else{
                    log.debug("there is not such Order or Meeting");
                    returnList.add(el);
                }
            } catch (IOException e) {
                log.error(e);
            }
        });
        try {
            csvWriter(Corrections.class, recordList);
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }finally {
            return returnList;
        }
    }

    public List<Book> insertBook(List<Book> list) throws IOException {
        List<Book> returnList = new ArrayList<>();
        List<Book> recordList = read(Book.class);
        list.stream().forEach(el -> {
            try {
                //проверка, есть ли элемент указанный в поле Author в соответствующем csv файле
                if (el.getAuthor() == null || getPeopleByID(Author.class, el.getAuthor().getId()) != null){
                    log.debug("there is such Author");
                    if (recordList.stream().anyMatch(e2 -> e2.equals(el))){
                        log.debug("already exist in csv "+el);
                    }else if (recordList.stream().anyMatch(e2 -> e2.getId() == el.getId())) {
                        log.debug("id already exist in csv " + el);
                        returnList.add(el);
                    }else{
                        log.debug("will add to csv "+el);
                        recordList.add(el);
                    }
                }else{
                    log.debug("there is not such Author");
                    returnList.add(el);
                }
            } catch (IOException e) {
                log.error(e);
            }
        });
        try {
            csvWriter(Book.class, recordList);
        }catch (IndexOutOfBoundsException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e){
            log.error(e);
        }finally {
            return returnList;
        }
    }

    public boolean deleteFile(Class cl) {
        try{
            CSVWriter csvWriter = new CSVWriter(commonWriter(cl));
            csvWriter.close();
            return true;
        }catch (IOException e){
            log.error(e);
            return false;
        }
    }

    public boolean dropFile(Class cl) {
        try{
            File file= new File(getPath(cl));
            log.debug("file delete "+file.delete());
            return true;
        }catch (IOException e){
            log.error(e);
            return false;
        }
    }

    public <T extends People> boolean updatePeople(Class cl, T obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        List<T> list = read(cl);
        T prevObj;
        int index = list.indexOf(getPeopleByID(cl, obj.getId()));
        if (index>(-1)){
            prevObj = list.get(index);
            list.set(index, obj);
            deleteFile(cl);
            List<T> listNotInsert = insertPeople(cl, list);
            if (listNotInsert.indexOf(obj)==-1){
                log.debug("update success");
                return true;
            }else{
                list.set(index, prevObj);
                deleteFile(cl);
                insertPeople(cl, list);
            }
        }
        log.debug("update fail");
        return false;
    }

    public boolean updateMeeting(Meeting obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Meeting.class;
        List<Meeting> list = read(cl);
        Meeting prevObj;
        int index = list.indexOf(getMeetingByID(obj.getId()));
        if (index>(-1)){
            prevObj = list.get(index);
            list.set(index, obj);
            deleteFile(cl);
            List<Meeting> listNotInsert = insertMeeting(list);
            if (listNotInsert.indexOf(obj)==-1){
                log.debug("update success");
                return true;
            }else{
                list.set(index, prevObj);
                deleteFile(cl);
                insertMeeting(list);
            }
        }
        log.debug("update fail");
        return false;
    }

    public boolean updateCoverPrice(CoverPrice obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = CoverPrice.class;
        List<CoverPrice> list = read(cl);
        CoverPrice prevObj;
        int index = list.indexOf(getCoverPriceByID(obj.getId()));
        if (index>(-1)){
            prevObj = list.get(index);
            list.set(index, obj);
            deleteFile(cl);
            List<CoverPrice> listNotInsert = insertCoverPrice(list);
            if (listNotInsert.indexOf(obj)==-1){
                log.debug("update success");
                return true;
            }else{
                list.set(index, prevObj);
                deleteFile(cl);
                insertCoverPrice(list);
            }
        }
        log.debug("update fail");
        return false;
    }

    public boolean updatePriceParameters(PriceParameters obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = PriceParameters.class;
        List<PriceParameters> list = read(cl);
        PriceParameters prevObj;
        int index = list.indexOf(getPriceParametersByID(obj.getId()));
        if (index>(-1)){
            prevObj = list.get(index);
            list.set(index, obj);
            deleteFile(cl);
            List<PriceParameters> listNotInsert = insertPriceParameters(list);
            if (listNotInsert.indexOf(obj)==-1){
                log.debug("update success");
                return true;
            }else{
                list.set(index, prevObj);
                deleteFile(cl);
                insertPriceParameters(list);
            }
        }
        log.debug("update fail");
        return false;
    }

    public boolean updateOrder(Order obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Order.class;
        List<Order> list = read(cl);
        Order prevObj;
        int index = list.indexOf(getBookByID(cl, obj.getId()));
        if (index>(-1)){
            prevObj = list.get(index);
            list.set(index, obj);
            deleteFile(cl);
            List<Order> listNotInsert = insertOrder(list);
            if (listNotInsert.indexOf(obj)==-1){
                log.debug("update success");
                return true;
            }else{
                list.set(index, prevObj);
                deleteFile(cl);
                insertOrder(list);
            }
        }
        log.debug("update fail");
        return false;
    }

    public boolean updateCorrections(Corrections obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Corrections.class;
        List<Corrections> list = read(cl);
        Corrections prevObj;
        int index = list.indexOf(getCorrectionsByID(obj.getId()));
        if (index>(-1)){
            prevObj = list.get(index);
            list.set(index, obj);
            deleteFile(cl);
            List<Corrections> listNotInsert = insertCorrections(list);
            if (listNotInsert.indexOf(obj)==-1){
                log.debug("update success");
                return true;
            }else{
                list.set(index, prevObj);
                deleteFile(cl);
                insertCorrections(list);
            }
        }
        log.debug("update fail");
        return false;
    }

    public boolean updateBook(Book obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Book.class;
        List<Book> list = read(cl);
        Book prevObj;
        int index = list.indexOf(getBookByID(cl, obj.getId()));
        if (index>(-1)){
            prevObj = list.get(index);
            list.set(index, obj);
            deleteFile(cl);
            List<Book> listNotInsert = insertBook(list);
            if (listNotInsert.indexOf(obj)==-1){
                log.debug("update success");
                return true;
            }else{
                list.set(index, prevObj);
                deleteFile(cl);
                insertBook(list);
            }
        }
        log.debug("update fail");
        return false;
    }

    public <T> boolean deleteObj(Class cl, T obj) {
        try {
            switch (cl.getSimpleName().toLowerCase()) {
                case Constants.CORRECTIONS:
                    Corrections corObj = (Corrections) obj;
                    log.info("delete " + Constants.CORRECTIONS);
                    return deleteCorrections(corObj);
                case Constants.PEOPLE:
                    People peoplObj = (People) obj;
                    log.info("delete " + Constants.PEOPLE);
                    return deletePeople(peoplObj);
                case Constants.BOOK:
                    Book bookObj = (Book) obj;
                    log.info("delete " + Constants.BOOK);
                    return deleteBook(bookObj);
                case Constants.EMPLOYEE:
                    Employee emplObj = (Employee) obj;
                    log.info("delete " + Constants.EMPLOYEE);
                    return deleteEmployee(emplObj);
                case Constants.PRICEPARAMETERS:
                    PriceParameters priceObj = (PriceParameters) obj;
                    log.info("delete " + Constants.PRICEPARAMETERS);
                    return deletePriceParameters(priceObj);
                case Constants.ORDER:
                    Order ordObj = (Order) obj;
                    log.info("delete " + Constants.ORDER);
                    return deleteOrder(ordObj);
                case Constants.MEETING:
                    Meeting meetObj = (Meeting) obj;
                    log.info("delete " + Constants.MEETING);
                    return deleteMeeting(meetObj);
                case Constants.AUTHOR:
                    Author authObj = (Author) obj;
                    log.info("delete " + Constants.AUTHOR);
                    return deleteAuthor(authObj);
                case Constants.COVERPRICE:
                    CoverPrice covObj = (CoverPrice) obj;
                    log.info("delete " + Constants.COVERPRICE);
                    return deleteCoverPrice(covObj);
                default:
                    log.debug("default case");
                    return false;
            }
        }catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e){
            log.error(e);
            return false;
        }
    }

    public boolean deleteCorrections(Corrections obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Corrections.class;
        List<Corrections> list = read(cl);
        try {
            checkListIsNotEmpty(list);
            if (list.indexOf(obj) > (-1)) {
                list.removeIf(el -> el.equals(obj));
                deleteFile(cl);
                insertCorrections(list);
                log.debug("delete success");
                return true;
            } else {
                log.debug("delete fail");
                return false;
            }
        }catch (Exception e){
            log.debug("csv is empty");
            return false;
        }
    }

    public boolean deletePeople(People obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = People.class;
        List<People> list = read(cl);
        try {
            checkListIsNotEmpty(list);
            if (list.indexOf(obj) > (-1)) {
                list.removeIf(el -> el.equals(obj));
                deleteFile(cl);
                insertPeople(cl, list);
                log.debug("delete success");
                return true;
            } else {
                log.debug("delete fail");
                return false;
            }
        }catch (Exception e){
            log.debug("csv is empty");
            return false;
        }
    }

    public boolean deleteBook(Book obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Book.class;
        List<Book> list = read(cl);
        try {
            checkListIsNotEmpty(list);
            if (list.indexOf(obj) > (-1)) {
                list.removeIf(el -> el.equals(obj));
                deleteFile(cl);
                insertBook(list);
                log.debug("delete success");
                return true;
            } else {
                log.debug("delete fail");
                return false;
            }
        }catch (Exception e){
            log.debug("csv is empty");
            return false;
        }
    }

    public boolean deleteEmployee(Employee obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Employee.class;
        List<Employee> list = read(cl);
        try {
            checkListIsNotEmpty(list);
            List<Order> listOrder = read(Order.class);
            if (!listOrder.stream().anyMatch(el -> (el.getBookEditor().getId() != obj.getId() || el.getBookMaker().getId() == obj.getId()))) {
                list.removeIf(el -> el.equals(obj));
                deleteFile(cl);
                insertPeople(cl, list);
                log.debug("delete success");
                return true;
            } else {
                log.debug("delete fail");
                return false;
            }
        }catch (Exception e){
            log.debug("csv is empty");
            return false;
        }
    }

    public boolean deletePriceParameters(PriceParameters obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = PriceParameters.class;
        List<PriceParameters> list = read(cl);
        try {
            checkListIsNotEmpty(list);
            List<Order> listOrder2 = read(Order.class);
            if (!listOrder2.stream().anyMatch(el -> el.getBookPriceParameters().getId() == obj.getId())) {
                list.removeIf(el -> el.equals(obj));
                deleteFile(cl);
                insertPriceParameters(list);
                log.debug("delete success");
                return true;
            } else {
                log.debug("delete fail");
                return false;
            }
        }catch (Exception e){
            log.debug("csv is empty");
            return false;
        }
    }

    public boolean deleteOrder(Order obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Order.class;
        List<Order> list = read(cl);
        try {
            checkListIsNotEmpty(list);
            List<Corrections> listCorrections = read(Corrections.class);
            if (!listCorrections.stream().anyMatch(el -> el.getOrder().getId() == obj.getId())) {
                list.removeIf(el -> el.equals(obj));
                deleteFile(cl);
                insertOrder(list);
                log.debug("delete success");
                return true;
            } else {
                log.debug("delete fail");
                return false;
            }
        }catch (Exception e){
            log.debug("csv is empty");
            return false;
        }
    }

    public boolean deleteMeeting(Meeting obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Meeting.class;
        List<Meeting> list = read(cl);
        try {
            checkListIsNotEmpty(list);
            List<Corrections> listCorrections2 = read(Book.class);
            if (!listCorrections2.stream().anyMatch(el -> el.getMeet().getId() == obj.getId())) {
                list.removeIf(el -> el.equals(obj));
                deleteFile(cl);
                insertMeeting(list);
                log.debug("delete success");
                return true;
            } else {
                log.debug("delete fail");
                return false;
            }
        }catch (Exception e){
            log.debug("csv is empty");
            return false;
        }
    }

    public boolean deleteAuthor(Author obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Author.class;
        List<Author> list = read(cl);
        try {
            checkListIsNotEmpty(list);
            List<Book> listBook = read(Book.class);
            if (!listBook.stream().anyMatch(el -> el.getAuthor().getId() == obj.getId())) {
                list.removeIf(el -> el.equals(obj));
                deleteFile(cl);
                insertPeople(cl, list);
                log.debug("delete success");
                return true;
            } else {
                log.debug("delete fail");
                return false;
            }
        }catch (Exception e){
            log.debug("csv is empty");
            return false;
        }
    }

    public boolean deleteCoverPrice(CoverPrice obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = CoverPrice.class;
        List<CoverPrice> list = read(cl);
        try {
            checkListIsNotEmpty(list);
            List<PriceParameters> listPriceParameters = read(PriceParameters.class);
            if (!listPriceParameters.stream().anyMatch(el -> el.getCoverPrice().stream().anyMatch(id -> id.getId() == obj.getId()))) {
                list.removeIf(el -> el.equals(obj));
                deleteFile(cl);
                insertCoverPrice(list);
                log.debug("delete success");
                return true;
            } else {
                log.debug("delete fail");
                return false;
            }
        }catch (Exception e){
            log.debug("csv is empty");
            return false;
        }
    }

    public long getMaxId (Class cl) {
        try {
            switch (cl.getSimpleName().toLowerCase()) {
                case Constants.CORRECTIONS:
                    List<Corrections> corList = read(cl);
                    return corList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
                case Constants.PEOPLE:
                    List<People> poepList = read(cl);
                    return poepList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
                case Constants.BOOK:
                    List<Book> bookList = read(cl);
                    return bookList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
                case Constants.EMPLOYEE:
                    List<Employee> emplList = read(cl);
                    return emplList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
                case Constants.PRICEPARAMETERS:
                    List<PriceParameters> priceList = read(cl);
                    return priceList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
                case Constants.ORDER:
                    List<Order> orderList = read(cl);
                    return orderList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
                case Constants.MEETING:
                    List<Meeting> meetList = read(cl);
                    return meetList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
                case Constants.AUTHOR:
                    List<Author> authList = read(cl);
                    return authList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
                case Constants.COVERPRICE:
                    List<CoverPrice> covList = read(cl);
                    return covList.stream().max(Comparator.comparing(el -> el.getId())).get().getId();
                default:
                    log.debug("default case");
                    return -1;
            }
        }catch (NoSuchElementException | IOException e){
            log.error(e);
            return -1;
        }
    }

    public Employee createDefaultEmloyee(){
        Employee employee = new Employee();
        employee.setId(DEFAULT_ID);
        employee.setFirstName(DEFAULT_NAME);
        employee.setSecondName(DEFAULT_NAME);
        employee.setLastName(DEFAULT_NAME);
        employee.setPhone(DEFAULT_PHONE);
        employee.setInn(DEFAULT_PHONE);
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
        }catch (IOException e){
            return null;
        }
    }

    public CoverPrice createDefaultCoverPrice(){
        CoverPrice coverPrice = new CoverPrice();
        coverPrice.setId(DEFAULT_ID);
        coverPrice.setCoverType(CoverType.PAPERBACK);
        coverPrice.setCoverType(CoverType.PAPERBACK);
        coverPrice.setPrice(DEFAULT_PRICE);
        return coverPrice;
    }

    public Meeting createDefaultMeeting(){
        Meeting meet = null;
        try {
            meet = getMeetingByID(DEFAULT_ID);
        } catch (IOException e) {
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

    public <T> void checkNullObject (T obj) throws Exception {
        if (obj != null) throw new Exception(EXCEPTION_OBJECT_IS_NOT_NULL);
    }
}
