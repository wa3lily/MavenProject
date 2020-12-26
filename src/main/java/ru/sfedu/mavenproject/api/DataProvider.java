package ru.sfedu.mavenproject.api;

import ru.sfedu.mavenproject.bean.*;
import ru.sfedu.mavenproject.bean.enums.BookStatus;
import ru.sfedu.mavenproject.bean.enums.CorrectionsStatus;
import ru.sfedu.mavenproject.bean.enums.CoverType;
import java.util.List;
import java.util.Optional;

/**
 * The interface Data provider.
 */
public interface DataProvider {

     /**
      * Gets max id.
      *
      * @param cl the cl
      * @return the max id
      */
     long getMaxId (Class cl);

     /**
      * Delete obj boolean.
      *
      * @param <T> the type parameter
      * @param cl  the cl
      * @param obj the obj
      * @return the boolean
      */
     <T> boolean deleteObj(Class cl, T obj);

     //Author

     /**
      * Alter book boolean.
      *
      * @param authorId      the author id
      * @param id            the id
      * @param title         the title
      * @param numberOfPages the number of pages
      * @return the boolean
      */
     boolean alterBook (long authorId, long id, String title, int numberOfPages);

     /**
      * Save order information boolean.
      *
      * @param order the order
      * @return the boolean
      */
     boolean saveOrderInformation (Order order);

     /**
      * Make order optional.
      *
      * @param id             the id
      * @param orderDate      the order date
      * @param coverType      the cover type
      * @param numberOfCopies the number of copies
      * @return the optional
      */
     Optional<Order> makeOrder (long id, String orderDate, String coverType, int numberOfCopies);

     /**
      * Calculate cost double.
      *
      * @param orderId the order id
      * @return the double
      */
     double calculateCost (long orderId) ;

     /**
      * Select price parameters optional.
      *
      * @param date the date
      * @return the optional
      */
     Optional<PriceParameters> selectPriceParameters(String date);

     /**
      * Belong interval boolean.
      *
      * @param start the start
      * @param end   the end
      * @param date  the date
      * @return the boolean
      */
     boolean belongInterval (String start, String end, String date);

     /**
      * Calculate editor work cost double.
      *
      * @param idPriceParameters the id price parameters
      * @param numberOfPages     the number of pages
      * @return the double
      */
     double calculateEditorWorkCost (long idPriceParameters, int numberOfPages);

     /**
      * Calculate printing cost double.
      *
      * @param idPriceParameters the id price parameters
      * @param numberOfPages     the number of pages
      * @return the double
      */
     double calculatePrintingCost (long idPriceParameters, int numberOfPages);

     /**
      * Calculate cover cost double.
      *
      * @param idPriceParameters the id price parameters
      * @param coverType         the cover type
      * @return the double
      */
     double calculateCoverCost (long idPriceParameters, CoverType coverType);

     /**
      * Take away order boolean.
      *
      * @param id the id
      * @return the boolean
      */
     boolean takeAwayOrder (long id);

     /**
      * Gets list of corrections.
      *
      * @param authorId the author id
      * @return the list of corrections
      */
     List<Corrections> getListOfCorrections (long authorId);

     /**
      * Gets list of corrections to order.
      *
      * @param orderId the order id
      * @return the list of corrections to order
      */
     List<Corrections> getListOfCorrectionsToOrder (long orderId);

     /**
      * Gets list of author order.
      *
      * @param authorId the author id
      * @return the list of author order
      */
     List<Order> getListOfAuthorOrder (long authorId);

     /**
      * Gets list of author book.
      *
      * @param authorId the author id
      * @return the list of author book
      */
     List<Book> getListOfAuthorBook (long authorId);

     /**
      * Agreement correction boolean.
      *
      * @param correctionId the correction id
      * @return the boolean
      */
     boolean agreementCorrection (long correctionId);

     /**
      * Decline correction boolean.
      *
      * @param correctionId the correction id
      * @param comment      the comment
      * @return the boolean
      */
     boolean declineCorrection (long correctionId, String comment);

     /**
      * Gets meeting information.
      *
      * @param correctionId the correction id
      * @return the meeting information
      */
     long getMeetingInformation (long correctionId);

     /**
      * Agreement meeting boolean.
      *
      * @param meetingId the meeting id
      * @return the boolean
      */
     boolean agreementMeeting (long meetingId);

     /**
      * Decline meeting boolean.
      *
      * @param meetingId the meeting id
      * @param date      the date
      * @return the boolean
      */
     boolean declineMeeting (long meetingId, String date);

     /**
      * Add author optional.
      *
      * @param id           the id
      * @param firstName    the first name
      * @param secondName   the second name
      * @param lastName     the last name
      * @param phone        the phone
      * @param email        the email
      * @param degree       the degree
      * @param organization the organization
      * @return the optional
      */
     Optional<Author> addAuthor(long id,String firstName,String secondName,String lastName,String phone, String email,String degree,String organization);

     /**
      * Sets author.
      *
      * @param author       the author
      * @param id           the id
      * @param firstName    the first name
      * @param secondName   the second name
      * @param lastName     the last name
      * @param phone        the phone
      * @param email        the email
      * @param degree       the degree
      * @param organization the organization
      */
     void setAuthor(Author author, long id,String firstName,String secondName,String lastName,String phone, String email,String degree,String organization);

     ////Editor


     /**
      * Gets order list without editor.
      *
      * @return the order list without editor
      */
     List<Order> getOrderListWithoutEditor ();

     /**
      * Add book editor boolean.
      *
      * @param OrderId    the order id
      * @param EmployeeId the employee id
      * @return the boolean
      */
     boolean addBookEditor(long OrderId, long EmployeeId);

     /**
      * Return to author boolean.
      *
      * @param OrderId the order id
      * @return the boolean
      */
     boolean returnToAuthor (long OrderId);

     /**
      * End editing boolean.
      *
      * @param OrderId the order id
      * @return the boolean
      */
     boolean endEditing (long OrderId);

     /**
      * Send corrections to author optional.
      *
      * @param id         the id
      * @param page       the page
      * @param textBefore the text before
      * @param textAfter  the text after
      * @param comment    the comment
      * @param orderId    the order id
      * @param meetingId  the meeting id
      * @return the optional
      */
     Optional<Corrections> sendCorrectionsToAuthor(long id, int page, String textBefore, String textAfter, String comment, long orderId, long meetingId);

     /**
      * Make meeting boolean.
      *
      * @param correctionsId the corrections id
      * @param id            the id
      * @param meetDate      the meet date
      * @return the boolean
      */
     boolean makeMeeting (long correctionsId, long id, String meetDate);

////Maker

     /**
      * Find order optional.
      *
      * @param orderId the order id
      * @return the optional
      */
     Optional<Order> findOrder(long orderId);

     /**
      * Return to editor boolean.
      *
      * @param OrderId the order id
      * @return the boolean
      */
     boolean returnToEditor (long OrderId);

     /**
      * Take for printing boolean.
      *
      * @param OrderId    the order id
      * @param EmployeeId the employee id
      * @return the boolean
      */
     boolean takeForPrinting (long OrderId,long EmployeeId);

     /**
      * Mark as finished boolean.
      *
      * @param OrderId the order id
      * @return the boolean
      */
     boolean markAsFinished (long OrderId);

////Chief

     /**
      * Count published books long.
      *
      * @param startDate the start date
      * @param deadline  the deadline
      * @return the long
      */
     long countPublishedBooks (String startDate, String deadline);

     /**
      * Count printing books long.
      *
      * @param startDate the start date
      * @param deadline  the deadline
      * @return the long
      */
     long countPrintingBooks (String startDate, String deadline);

     /**
      * Count editing books long.
      *
      * @param startDate the start date
      * @param deadline  the deadline
      * @return the long
      */
     long countEditingBooks (String startDate, String deadline);

////Admin

     /**
      * Sets price parameters.
      *
      * @param id            the id
      * @param pagePrice     the page price
      * @param coverPrice    the cover price
      * @param workPrice     the work price
      * @param validFromDate the valid from date
      * @param validToDate   the valid to date
      * @return the price parameters
      */
     Optional<PriceParameters> setPriceParameters(long id, double pagePrice, List<CoverPrice> coverPrice, double workPrice, String validFromDate, String validToDate);

     /**
      * Add price parameters boolean.
      *
      * @param id            the id
      * @param pagePrice     the page price
      * @param coverPrice    the cover price
      * @param workPrice     the work price
      * @param validFromDate the valid from date
      * @param validToDate   the valid to date
      * @return the boolean
      */
     boolean addPriceParameters(long id, double pagePrice, List<CoverPrice> coverPrice, double workPrice, String validFromDate, String validToDate);

     /**
      * Sets cover price.
      *
      * @param id        the id
      * @param coverType the cover type
      * @param price     the price
      * @return the cover price
      */
     Optional<CoverPrice> setCoverPrice(long id, String coverType, double price);

     /**
      * Add cover price boolean.
      *
      * @param id        the id
      * @param coverType the cover type
      * @param price     the price
      * @return the boolean
      */
     boolean addCoverPrice(long id, String coverType, double price);
}
