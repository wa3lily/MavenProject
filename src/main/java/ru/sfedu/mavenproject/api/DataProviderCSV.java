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
import ru.sfedu.mavenproject.ClassId;
import ru.sfedu.mavenproject.bean.*;
import ru.sfedu.mavenproject.utils.ConfigurationUtil;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import static ru.sfedu.mavenproject.Constants.PATH_CSV;
import static ru.sfedu.mavenproject.Constants.FILE_EXTENSION_CSV;

public class DataProviderCSV {

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

    public <T extends ClassId> Object getByID(Class cl, long id) throws IOException {
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

    public String createFile(String path) throws IOException {
        File file = new File(path);
        file.createNewFile();
        return path;
    }

    public <T extends ClassId> List<T> insertWithoutRelation(Class cl, List<T> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        //список для элементов, нарушающих уникальность ключей
        List<T> returnList = new ArrayList<T>();
        //список для элементов для записи в csv
        List<T> recordList = read(cl);
        log.debug("csv: "+recordList);
        for (T elem : list) {
            if (recordList.stream().noneMatch(e1 -> e1.getId() == elem.getId())){
                recordList.add(elem);
                log.debug("add to csv "+elem);
            }else if ((recordList.stream().anyMatch(e1 -> e1.getId() == elem.getId() && e1.equals(elem)))){
                log.debug("already exist "+elem);
            }else{
                returnList.add(elem);
                log.debug("wrong id "+elem);
            }
        }
        try {
            CSVWriter csvWriter = new CSVWriter(commonWriter(cl));
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(recordList);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
        //возвращает список элементов, которые не удалось записать
        return returnList;
    }

    public <T extends ClassId> List<T> insert(Class cl, List<T> list) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        List<T> returnList = new ArrayList<T>();
        List<T> recordList = new ArrayList<>();
        switch (cl.getSimpleName().toLowerCase()){
            //classes without relation to other classes
            case "people":
            case "author":
            case "employee":
            case "meeting":
            case "coverprice":
                returnList.addAll(insertWithoutRelation(cl, list));
                break;
                //classes with relation to other classes
            case "book":
                List<Book> bookList = (List<Book>) list;
                for (Book elem : bookList) {
                    if (getByID(Author.class, elem.getAuthor().getId()) != null){
                        recordList.add((T)elem);
                        log.debug("there is such Author in csv");
                    }else{
                        returnList.add((T)elem);
                        log.debug("there is no such Author in csv");
                    }
                }
                returnList.addAll(insertWithoutRelation(cl, recordList));
                break;
            case "priceparameters":
                List<PriceParameters> priceList = (List<PriceParameters>) list;
                for (PriceParameters elem : priceList) {
                    if (getByID(CoverPrice.class, elem.getCoverPrice().getId()) != null){
                        recordList.add((T)elem);
                        log.debug("there is such CoverPrice in csv");
                    }else{
                        returnList.add((T)elem);
                        log.debug("there is no such CoverPrice in csv");
                    }
                }
                returnList.addAll(insertWithoutRelation(cl, recordList));
                break;
            case "corrections":
                List<Corrections> correctionsList = (List<Corrections>) list;
                for (Corrections elem : correctionsList) {
                    if (getByID(Order.class, elem.getOrder().getId()) != null && (elem.getMeet() == null || getByID(Meeting.class, elem.getMeet().getId()) != null)){
                        recordList.add((T)elem);
                        log.debug("there is such Order and Meeting in csv");
                    }else{
                        returnList.add((T)elem);
                        log.debug("there is no such Order or Meeting in csv");
                    }
                }
                returnList.addAll(insertWithoutRelation(cl, recordList));
                break;
            case "order":
                List<Order> orderList = (List<Order>) list;
                for (Order elem : orderList) {
                    if ((elem.getBookPriceParameters() == null || getByID(PriceParameters.class, elem.getBookPriceParameters().getId()) != null)
                            && (elem.getBookMaker() == null || getByID(Employee.class, elem.getBookMaker().getId()) != null)
                            && (elem.getBookEditor() == null || getByID(Employee.class, elem.getBookEditor().getId()) != null)){
                        recordList.add((T)elem);
                        log.debug("there is such PriceParameters and Employee in csv");
                    }else{
                        returnList.add((T)elem);
                        log.debug("there is no such PriceParameters or Employee in csv");
                    }
                }
                returnList.addAll(insertWithoutRelation(cl, recordList));
                break;
            default:
                log.debug("default case");
        }
        return returnList;
    }

    public <T extends ClassId> boolean deleteFile(Class cl) {
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

    public <T extends ClassId> List<Long> readId(Class cl) throws IOException {
        List<T> list = read(cl);
        List<Long> result = new ArrayList<>();
        for (T elem : list) {
            result.add(elem.getId());
        }
        return result;
    }

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

    public <T extends ClassId> boolean deleteObj(Class cl, T obj) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        List<T> list = read(cl);
        int index = list.indexOf(obj);
        boolean result = true;
        switch (cl.getSimpleName().toLowerCase()) {
            case "corrections":
            case "people":
            case "book":
                result = (index > (-1));
                break;
            //Order
            case "employee":
                List<Order> listOrder = read(Order.class);
                result = !listOrder.stream().anyMatch(el -> (el.getBookEditor().getId() != obj.getId() || el.getBookMaker().getId() == obj.getId()));
                break;
            case "priceparameters":
                List<Order> listOrder2 = read(Order.class);
                result = !listOrder2.stream().anyMatch(el -> el.getBookPriceParameters().getId() == obj.getId());
                break;
            //Corrections
            case "order":
                List<Corrections> listCorrections = read(Corrections.class);
                result = !listCorrections.stream().anyMatch(el -> el.getOrder().getId() == obj.getId());
                break;
            case "meeting":
                List<Corrections> listCorrections2 = read(Book.class);
                result = !listCorrections2.stream().anyMatch(el -> el.getMeet().getId() == obj.getId());
                break;
            //Book
            case "author":
                List<Book> listBook = read(Book.class);
                result = !listBook.stream().anyMatch(el -> el.getAuthor().getId() == obj.getId());
                break;
            //PriceParameters
            case "coverprice":
                List<PriceParameters> listPriceParameters = read(PriceParameters.class);
                result = !listPriceParameters.stream().anyMatch(el -> el.getCoverPrice().getId() == obj.getId());
                break;
            default:
                result = false;
                log.debug("default case");
        }
        if (result) {
            list.removeIf(el -> el.equals(obj));
            deleteFile(cl);
            insert(cl, list);
            log.debug("delete success");
        }else{
            log.debug("delete fail");
        }
        return result;
    }








}
