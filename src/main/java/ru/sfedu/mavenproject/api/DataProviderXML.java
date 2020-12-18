package ru.sfedu.mavenproject.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.mavenproject.bean.*;
import ru.sfedu.mavenproject.bean.enums.BookStatus;
import ru.sfedu.mavenproject.bean.enums.CorrectionsStatus;
import ru.sfedu.mavenproject.bean.enums.CoverType;
import ru.sfedu.mavenproject.utils.ConfigurationUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static ru.sfedu.mavenproject.Constants.PATH_XML;
import static ru.sfedu.mavenproject.Constants.FILE_EXTENSION_XML;

public class DataProviderXML implements DataProvider{

    private static Logger log = LogManager.getLogger(DataProviderXML.class);

    /**
     * Получаем путь к файлу
     * @param cl
     * @return
     * @throws IOException
     */
    private String getFilePath(Class cl) throws  Exception{
        return ConfigurationUtil.getConfigurationEntry(PATH_XML)+cl.getSimpleName().toLowerCase()+ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION_XML);
    }

    public <T> List<T> read(Class cl) throws IOException, Exception{
        //Подключаемся к считывающему потоку из файла
        FileReader fileReader = new FileReader(this.getFilePath(cl));
        //Определяем сериалайзер
        Serializer serializer = new Persister();
        //Определяем контейнер и записываем в него объекты
        WrapperXML xml = serializer.read(WrapperXML.class, fileReader);
        //Если список null, то делаем его пустым списком
        if (xml.getList() == null) xml.setList(new ArrayList<T>());
        //Возвращаем список объектов
        return xml.getList();
    }

    public void insertPeople (List<People> peopleList) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, Exception{
        try{
            //Проверяем, создан ли файл. Если нет, то создаём
            (new File(this.getFilePath(People.class))).createNewFile();
            //Подключаемся к потоку записи файла
            FileWriter writer = new FileWriter(this.getFilePath(People.class), false);
            //Определяем сериалайзер
            Serializer serializer = new Persister();

            //Определяем контейнер, в котором будут находиться все объекты
            WrapperXML<People> xml = new WrapperXML<People>();
            //Записываем список объектов в котнейнер
            xml.setList(peopleList);

            //Записываем в файл
            serializer.write(xml, writer);

        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
    }

    public People getPeopleById (long id) throws IOException, Exception{
        List<People> list = this.read(People.class);
        try{
            People people = list.stream()
                    .filter(el -> el.getId() == id)
                    .limit(1)
                    .findFirst().get();
            return people;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }


    @Override
    public long getMaxId(Class cl) {
        return 0;
    }

    @Override
    public <T> boolean deleteObj(Class cl, T obj) {
        return false;
    }

    @Override
    public boolean alterBook(long authorId, long id, String title, int numberOfPages) {
        return false;
    }

    @Override
    public boolean saveOrderInformation(Order order) {
        return false;
    }

    @Override
    public Optional<Order> makeOrder(long id, String orderDate, String coverType, int numberOfCopies) {
        return Optional.empty();
    }

    @Override
    public double calculateCost(long orderId) {
        return 0;
    }

    @Override
    public Optional<PriceParameters> selectPriceParameters(String date) {
        return Optional.empty();
    }

    @Override
    public boolean belongInterval(String start, String end, String date) {
        return false;
    }

    @Override
    public double calculateEditorWorkCost(long idPriceParameters, int numberOfPages) {
        return 0;
    }

    @Override
    public double calculatePrintingCost(long idPriceParameters, int numberOfPages) {
        return 0;
    }

    @Override
    public double calculateCoverCost(long idPriceParameters, CoverType coverType) {
        return 0;
    }

    @Override
    public boolean takeAwayOrder(long id) {
        return false;
    }

    @Override
    public List<Corrections> getListOfCorrections(long authorId) {
        return null;
    }

    @Override
    public List<Order> getListOfAuthorOrder(long authorId) {
        return null;
    }

    @Override
    public List<Book> getListOfAuthorBook(long authorId) {
        return null;
    }

    @Override
    public Optional<Author> addAuthor(long id, String firstName, String secondName, String lastName, String phone, String email, String degree, String organization) {
        return Optional.empty();
    }

    @Override
    public void setAuthor(Author author, long id, String firstName, String secondName, String lastName, String phone, String email, String degree, String organization) {

    }

    @Override
    public boolean addBookEditor(long OrderId, long EmployeeId) {
        return false;
    }

    @Override
    public boolean returnToAuthor(long OrderId) {
        return false;
    }

    @Override
    public boolean endEditing(long OrderId) {
        return false;
    }

    @Override
    public Optional<Corrections> sendCorrectionsToAuthor(long id, int page, String textBefore, String textAfter, String comment, Order order, Meeting meet, CorrectionsStatus status) {
        return Optional.empty();
    }

    @Override
    public boolean makeMeeting(long correctionsId, long id, String meetDate, boolean authorAgreement, boolean editorAgreement) {
        return false;
    }

    @Override
    public Optional<Order> findOrder(long orderId) {
        return Optional.empty();
    }

    @Override
    public boolean returnToEditor(long OrderId) {
        return false;
    }

    @Override
    public boolean takeForPrinting(long OrderId, long EmployeeId) {
        return false;
    }

    @Override
    public boolean markAsFinished(long OrderId) {
        return false;
    }

    @Override
    public long countPublishedBooks(String startDate, String deadline) {
        return 0;
    }

    @Override
    public long countPrintingBooks(String startDate, String deadline) {
        return 0;
    }

    @Override
    public long countEditingBooks(String startDate, String deadline) {
        return 0;
    }

    @Override
    public long countStatistic(String startDate, String deadline, BookStatus bookStatus) {
        return 0;
    }

    @Override
    public Optional<PriceParameters> addPriceParameters(long id, double pagePrice, List<CoverPrice> coverPrice, double workPrice, String validFromDate, String validToDate) {
        return Optional.empty();
    }

    @Override
    public Optional<CoverPrice> addCoverPrice(long id, String coverType, double price) {
        return Optional.empty();
    }

    @Override
    public boolean returnTo(long OrderId, BookStatus bookStatus) {
        return false;
    }
}
