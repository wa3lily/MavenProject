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
import ru.sfedu.mavenproject.utils.ConfigurationUtil;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import static ru.sfedu.mavenproject.Constants.PATH_CSV;
import static ru.sfedu.mavenproject.Constants.FILE_EXTENSION_CSV;

public class DataProviderCSV implements DataProvider {

    private static Logger log = LogManager.getLogger(DataProviderCSV.class);

    //CRUD and helper methods

    public FileWriter commonWriter(Class cl) throws IOException {
        FileWriter writer = new FileWriter(getPath(cl));
        return writer;
    }

    public <T> List<T> read(Class cl) throws IOException{
        FileReader fileReader;
        String path = getPath(cl);
        try{
            fileReader = new FileReader(path);
        }catch (FileNotFoundException e){
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
    @Override
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

    //соединяет два массива, сохраняя повторяющиеся элементы второго в третий
    public <T> void concatLists( List<T> list, List<T> returnList, List<T> recordList) {
        list.stream().forEach(e1 -> separateDuplicates(returnList, recordList, e1));
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
            CSVWriter csvWriter = new CSVWriter(commonWriter(cl));
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(recordList);
            csvWriter.close();
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
            CSVWriter csvWriter = new CSVWriter(commonWriter(Meeting.class));
            StatefulBeanToCsv<Meeting> beanToCsv = new StatefulBeanToCsvBuilder<Meeting>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(recordList);
            csvWriter.close();
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
            CSVWriter csvWriter = new CSVWriter(commonWriter(CoverPrice.class));
            StatefulBeanToCsv<CoverPrice> beanToCsv = new StatefulBeanToCsvBuilder<CoverPrice>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(recordList);
            csvWriter.close();
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
            try {
                //проверка, есть ли элемент указанный в поле CoverPrice в соответствующем csv файле
                if (el.getCoverPrice() == null || getCoverPriceByID(el.getCoverPrice().getId()) != null){
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
            } catch (IOException e) {
                log.error(e);
            }
        });
        try {
            CSVWriter csvWriter = new CSVWriter(commonWriter(PriceParameters.class));
            StatefulBeanToCsv<PriceParameters> beanToCsv = new StatefulBeanToCsvBuilder<PriceParameters>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(recordList);
            csvWriter.close();
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
            CSVWriter csvWriter = new CSVWriter(commonWriter(Order.class));
            StatefulBeanToCsv<Order> beanToCsv = new StatefulBeanToCsvBuilder<Order>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(recordList);
            csvWriter.close();
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
            CSVWriter csvWriter = new CSVWriter(commonWriter(Corrections.class));
            StatefulBeanToCsv<Corrections> beanToCsv = new StatefulBeanToCsvBuilder<Corrections>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(recordList);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }finally {
            return returnList;
        }
    }

    public List<Book> insertBook(List<Book> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
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
            CSVWriter csvWriter = new CSVWriter(commonWriter(Book.class));
            StatefulBeanToCsv<Book> beanToCsv = new StatefulBeanToCsvBuilder<Book>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(recordList);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
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

/*
    public <T extends ClassId> boolean update(Class cl, T obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        List<T> list = read(cl);
        T prevObj;
        int index = list.indexOf(getByID(cl, obj.getId()));
        if (index>(-1)){
            prevObj = list.get(index);
            list.set(index, obj);
            deleteFile(cl);
            List<T> listNotInsert = insert(cl, list);
            if (listNotInsert.indexOf(obj)==-1){
                log.debug("update success");
                return true;
            }else{
                list.set(index, prevObj);
                deleteFile(cl);
                insert(cl, list);
            }
        }
        log.debug("update fail");
        return false;
    }
 */

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

    public <T> boolean deleteObj(Class cl, T obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        switch (cl.getSimpleName().toLowerCase()) {
            case Constants.CORRECTIONS:
                Corrections corObj = (Corrections) obj;
                log.info("delete "+Constants.CORRECTIONS);
                return deleteCorrections(corObj);
            case Constants.PEOPLE:
                People peoplObj = (People) obj;
                log.info("delete "+Constants.PEOPLE);
                return deletePeople(peoplObj);
            case Constants.BOOK:
                Book bookObj = (Book) obj;
                log.info("delete "+Constants.BOOK);
                return deleteBook(bookObj);
            case Constants.EMPLOYEE:
                Employee emplObj = (Employee) obj;
                log.info("delete "+Constants.EMPLOYEE);
                return deleteEmployee(emplObj);
            case Constants.PRICEPARAMETERS:
                PriceParameters priceObj = (PriceParameters) obj;
                log.info("delete "+Constants.PRICEPARAMETERS);
                return deletePriceParameters(priceObj);
            case Constants.ORDER:
                Order ordObj = (Order) obj;
                log.info("delete "+Constants.ORDER);
                return deleteOrder(ordObj);
            case Constants.MEETING:
                Meeting meetObj = (Meeting) obj;
                log.info("delete "+Constants.MEETING);
                return deleteMeeting(meetObj);
            case Constants.AUTHOR:
                Author authObj = (Author) obj;
                log.info("delete "+Constants.AUTHOR);
                return deleteAuthor(authObj);
            case Constants.COVERPRICE:
                CoverPrice covObj = (CoverPrice) obj;
                log.info("delete "+Constants.COVERPRICE);
                return deleteCoverPrice(covObj);
            default:
                log.debug("default case");
                return false;
        }
    }

    public boolean deleteCorrections(Corrections obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Corrections.class;
        List<Corrections> list = read(cl);
        if (list.indexOf(obj) > (-1)) {
            list.removeIf(el -> el.equals(obj));
            deleteFile(cl);
            insertCorrections(list);
            log.debug("delete success");
            return true;
        }else{
            log.debug("delete fail");
            return false;
        }
    }

    public boolean deletePeople(People obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = People.class;
        List<People> list = read(cl);
        if (list.indexOf(obj) > (-1)) {
            list.removeIf(el -> el.equals(obj));
            deleteFile(cl);
            insertPeople(cl, list);
            log.debug("delete success");
            return true;
        }else{
            log.debug("delete fail");
            return false;
        }
    }

    public boolean deleteBook(Book obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Book.class;
        List<Book> list = read(cl);
        if (list.indexOf(obj) > (-1)) {
            list.removeIf(el -> el.equals(obj));
            deleteFile(cl);
            insertBook(list);
            log.debug("delete success");
            return true;
        }else{
            log.debug("delete fail");
            return false;
        }
    }

    public boolean deleteEmployee(Employee obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Employee.class;
        List<Employee> list = read(cl);
        List<Order> listOrder = read(Order.class);
        if (!listOrder.stream().anyMatch(el -> (el.getBookEditor().getId() != obj.getId() || el.getBookMaker().getId() == obj.getId()))) {
            list.removeIf(el -> el.equals(obj));
            deleteFile(cl);
            insertPeople(cl, list);
            log.debug("delete success");
            return true;
        }else{
            log.debug("delete fail");
            return false;
        }
    }

    public boolean deletePriceParameters(PriceParameters obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = PriceParameters.class;
        List<PriceParameters> list = read(cl);
        List<Order> listOrder2 = read(Order.class);
        if (!listOrder2.stream().anyMatch(el -> el.getBookPriceParameters().getId() == obj.getId())) {
            list.removeIf(el -> el.equals(obj));
            deleteFile(cl);
            insertPriceParameters(list);
            log.debug("delete success");
            return true;
        }else{
            log.debug("delete fail");
            return false;
        }
    }

    public boolean deleteOrder(Order obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Order.class;
        List<Order> list = read(cl);
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
    }

    public boolean deleteMeeting(Meeting obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Meeting.class;
        List<Meeting> list = read(cl);
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
    }

    public boolean deleteAuthor(Author obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = Author.class;
        List<Author> list = read(cl);
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
    }

    public boolean deleteCoverPrice(CoverPrice obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Class cl = CoverPrice.class;
        List<CoverPrice> list = read(cl);
        List<PriceParameters> listPriceParameters = read(PriceParameters.class);
        if (!listPriceParameters.stream().anyMatch(el -> el.getCoverPrice().getId() == obj.getId())) {
            list.removeIf(el -> el.equals(obj));
            deleteFile(cl);
            insertCoverPrice(list);
            log.debug("delete success");
            return true;
        } else {
            log.debug("delete fail");
            return false;
        }
    }

    //Author

    public void Alter_book (){}

//public Set_title (){}

//public Attach_file (){}

//public Set_number_of_pages (){}

//public Make_order (){}

//public Set_order_date (){}

//public Set_cover_type (){}

//public Set_number_of_copies (){}

//public Calculate_cost (){}

//public Calculate_editor_work_cost (){}

//public Calculate_printing_cost (){}

//public Calculate_cover_cost (){}

//public Take_away_order (){}

//public Get_list_of_corrections (){}

//public Agreement_correction (){}

//public Agreement_edits (){}

//public Add_comment (){}

//public Agreement_meeting (){}

//public Add_author (){}

////Editor

//public Edit_book (){}

//public Get_Book (){}

//public Return_to_author (){}

//public Upload_editing_book_ (){}

//public Upload_book_for_printing (){}

//public End_editing (){}

//public Send_corrections_to_author (){}

//public Add_information_about_edits (){}

//public Make_meeting (){}

//public Return_to_author (){}

////Maker

//public Return_to_editor (){}

//public Take_for_printing (){}

//public Mark_as_finished (){}

////Chief

//public Count_published_books (){}

//public Count_printing_books (){}

//public Count_editing_books (){}

////Admin

//public Set_parameters (){}

//public Change_page_cost (){}

//public Change_work_cost (){}

//public Change_cover_cost (){}
}
