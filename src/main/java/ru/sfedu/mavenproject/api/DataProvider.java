package ru.sfedu.mavenproject.api;

import ru.sfedu.mavenproject.bean.*;
import ru.sfedu.mavenproject.bean.enums.BookStatus;
import ru.sfedu.mavenproject.bean.enums.CorrectionsStatus;
import ru.sfedu.mavenproject.bean.enums.CoverType;
import java.util.List;
import java.util.Optional;

public interface DataProvider {

     /**
      * @param cl
      * @return
      */
     long getMaxId (Class cl);

     /**
      * @param cl
      * @param obj
      * @param <T>
      * @return
      */
     <T> boolean deleteObj(Class cl, T obj);

     //Author

     /**
      * @param authorId
      * @param id
      * @param title
      * @param numberOfPages
      * @return
      */
     boolean alterBook (long authorId, long id, String title, int numberOfPages);

     /**
      * @param order
      * @return
      */
     boolean sendOrderInformation (Order order);

     /**
      * @param id
      * @param orderDate
      * @param coverType
      * @param numberOfCopies
      * @return
      */
     Optional<Order> makeOrder (long id, String orderDate, String coverType, int numberOfCopies);

     /**
      * @param orderId
      * @return
      */
     double calculateCost (long orderId) ;

     /**
      * @param date
      * @return
      */
     Optional<PriceParameters> selectPriceParameters(String date);

     /**
      * @param start
      * @param end
      * @param date
      * @return
      */
     boolean belongInterval (String start, String end, String date);

     /**
      * @param idPriceParameters
      * @param numberOfPages
      * @return
      */
     double calculateEditorWorkCost (long idPriceParameters, int numberOfPages);

     /**
      * @param idPriceParameters
      * @param numberOfPages
      * @return
      */
     double calculatePrintingCost (long idPriceParameters, int numberOfPages);

     /**
      * @param idPriceParameters
      * @param coverType
      * @return
      */
     double calculateCoverCost (long idPriceParameters, CoverType coverType);

     /**
      * @param id
      * @return
      */
     boolean takeAwayOrder (long id);

     /**
      * @param authorId
      * @return
      */
     List<Corrections> getListOfCorrections (long authorId);

     /**
      * @param authorId
      * @return
      */
     List<Order> getListOfAuthorOrder (long authorId);

     /**
      * @param authorId
      * @return
      */
     List<Book> getListOfAuthorBook (long authorId);

     /**
      * @param id
      * @param firstName
      * @param secondName
      * @param lastName
      * @param phone
      * @param email
      * @param degree
      * @param organization
      * @return
      */
     Optional<Author> addAuthor(long id,String firstName,String secondName,String lastName,String phone, String email,String degree,String organization);

     /**
      * @param author
      * @param id
      * @param firstName
      * @param secondName
      * @param lastName
      * @param phone
      * @param email
      * @param degree
      * @param organization
      */
     void setAuthor(Author author, long id,String firstName,String secondName,String lastName,String phone, String email,String degree,String organization);

     ////Editor

     /**
      * @param OrderId
      * @param EmployeeId
      * @return
      */
     boolean addBookEditor(long OrderId, long EmployeeId);

     /**
      * @param OrderId
      * @return
      */
     boolean returnToAuthor (long OrderId);

     /**
      * @param OrderId
      * @return
      */
     boolean endEditing (long OrderId);

     /**
      * @param id
      * @param page
      * @param textBefore
      * @param textAfter
      * @param comment
      * @param order
      * @param meet
      * @param status
      * @return
      */
     Optional<Corrections> sendCorrectionsToAuthor(long id, int page, String textBefore, String textAfter, String comment, Order order, Meeting meet, CorrectionsStatus status);

     /**
      * @param correctionsId
      * @param id
      * @param meetDate
      * @param authorAgreement
      * @param editorAgreement
      * @return
      */
     boolean makeMeeting (long correctionsId, long id, String meetDate, boolean authorAgreement, boolean editorAgreement);

////Maker

     /**
      * @param orderId
      * @return
      */
     Optional<Order> findOrder(long orderId);

     /**
      * @param OrderId
      * @return
      */
     boolean returnToEditor (long OrderId);

     /**
      * @param OrderId
      * @param EmployeeId
      * @return
      */
     boolean takeForPrinting (long OrderId,long EmployeeId);

     /**
      * @param OrderId
      *
      * @return
      */
     boolean markAsFinished (long OrderId);

////Chief

     /**
      * @param startDate
      * @param deadline
      * @return
      */
     long countPublishedBooks (String startDate, String deadline);

     /**
      * @param startDate
      * @param deadline
      * @return
      */
     long countPrintingBooks (String startDate, String deadline);

     /**
      * @param startDate
      * @param deadline
      * @return
      */
     long countEditingBooks (String startDate, String deadline);

     /**
      * @param startDate
      * @param deadline
      * @param bookStatus
      * @return
      */
     long countStatistic (String startDate, String deadline, BookStatus bookStatus);

////Admin


     /**
      * @param id
      * @param pagePrice
      * @param coverPrice
      * @param workPrice
      * @param validFromDate
      * @param validToDate
      * @return
      */
     Optional<PriceParameters> addPriceParameters(long id, double pagePrice, List<CoverPrice> coverPrice, double workPrice, String validFromDate, String validToDate);


     /**
      * @param id
      * @param coverType
      * @param price
      * @return
      */
     Optional<CoverPrice> addCoverPrice(long id, String coverType, double price);


     /**
      * @param OrderId
      * @param bookStatus
      * @return
      */
     boolean returnTo (long OrderId,BookStatus bookStatus);
}
