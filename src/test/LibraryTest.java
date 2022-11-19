package test;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import main.Book;
import main.Department;
import main.Library;
import main.Loan;
import main.Subscription;
import main.User;

public class LibraryTest {
    /* 
     * Unnecessary duplicate acquisition, by several departments, of infrequently accessed
     * copies of books or proceedings that are relevant to more than one department.
     */
    @Test
    @DisplayName("Unnecessary Duplicates Test")
    public void unnecessaryDupTest() {
        Library UL = new Library(); 
        Department CSIS = new Department(UL);
        Department Kemmy = new Department(UL);
        Book book = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction");

        UL.addBook(book);    
        assertEquals(CSIS.getBooks(), Kemmy.getBooks());
    }

    
    /* Unnecessary subscription by several departments to expensive journals that are
     * relevant to more than one department.
     */
    @Test
    @DisplayName("Unnecessary Subscriptions Test")
    public void unnecessarySubTest() {
        Library UL = new Library();
        Library UCC = new Library();

        ArrayList<Book> books = new ArrayList<>(Arrays.asList(
                new Book("A Game of Thrones", "George R. R. Martin", "Fantasy"),
                new Book("A clash of Kings", "George R. R. Martin", "Fantasy"),
                new Book("A Storm of Swords", "George R. R. Martin", "Fantasy")
        ));

        Subscription subs = new Subscription("Game of Thrones Trilogy", books);

        UL.addSub(subs);
        assertNotEquals(UL.getSubs (), UCC.getSubs());
        UL.addAggreement(UCC);
        UL.shareSub(UCC, subs);
        assertEquals(UL.getSubs(), UCC.getSubs());
    }

    /* Acquisition of books or proceedings of marginal interest to the university, which
     * could be borrowed from other universities with which UWON has an agreement
     */
    @Test
    @DisplayName("University Sharing Books Test")
    public void shareBooksTest() {
        Library UL = new Library();
        Library UCC = new Library();

        Book book = new Book("Harry Potter and the Goblet of Fire", "J.K. Rowling", "Action");
        ArrayList<Book> books = new ArrayList<>();
        books.add(book);

        UL.addBook(book);
        UL.addAggreement(UCC);
        UCC.borrowBook(UL, book);
        assertEquals(books, UCC.getBooks());
    }

    /* Subscription to journals of marginal interest to the university, which could be
     * accessed in other universities with which UWON has an agreement.
     */
    @Test
    @DisplayName("Share Subscriptions Test")
    public void shareSubscriptionsTest() {
        Library UL = new Library();
        Library UCC = new Library();

        ArrayList<Book> books = new ArrayList<>(Arrays.asList(
                new Book("Shrek", "William Steig", "Comedy"),
                new Book("Shrek 2", "William Steig", "Comedy"),
                new Book("Shrek 3", "William Steig", "Comedy")
        ));

        Subscription sub = new Subscription("Netflix", books);

        UL.addSub(sub);
        UL.addAggreement(UCC);
        UL.shareSub(UCC, sub);
        assertEquals(UL.getSubs(), UCC.getSubs());
    }

    /* Unavailability of requested books, for a variety of reasons such as department
     * budget restrictions, excessive borrowing by the same user, lack of enforcement of
     * rules limiting loan periods, loss or stealing of book copies and so on.
     */
    @Test
    @DisplayName("Unavailability of books Test")
    public void unavailabilityTest() {
        Library UL = new Library();
        Department CSIS = new Department(UL);

        User michael = new User();
        Book book = new Book("1984", "George Orwell", "Dystopian");

        CSIS.loan(book, LocalDate.now(), michael);
        CSIS.returnLoan(book);

        CSIS.loan(book, LocalDate.now(), michael);
        assertFalse(CSIS.loan(book, LocalDate.now(), michael));              
    }
    /* Unavailability of journal issues while they are being bound into yearly volumes
     */

    /* Lack of traceability to previous borrowers when books, proceedings or journal
     * volumes are found to be damaged
     */
    @Test
    @DisplayName("Show History of Ownership Test")      // Tadhg
    public void traceabilityTest() {
        Library UL = new Library();
        Department CSIS = new Department(UL);

        User john = new User();
        User milan = new User();
        Book book = new Book("Foundations of Software Testing", "Dorothy Graham", "Pain");
        UL.addBook(book);

        CSIS.loan(book, LocalDate.now().minusDays(15), john);
        CSIS.returnLoan(book);

        CSIS.loan(book, LocalDate.now().minusDays(5), milan);
        CSIS.returnLoan(book);

        ArrayList<Loan> expectedResults = new ArrayList<>(Arrays.asList(
                new Loan(book, LocalDate.now().minusDays(15), john),
                new Loan(book, LocalDate.now().minusDays(5), milan)
        ));

        assertEquals(expectedResults.toString(), CSIS.getHistoryOfBook(book).toString());
    }

    /* Inaccuracy of card indexes, e.g. a book is stated as being available whereas it is not
     * found at the appropriate place on the shelves.
     */
  @Test
    @DisplayName("Card Index Test")
    public void cardIndexTest() {
        Library UL = new Library();
        Department CSIS = new Department(UL);
        User john = new User();
        User milan = new User();

        Book book = new Book("The Fellowship of the Ring", "J. R. R. Tolkien", "Fantasy");
        CSIS.loan(book, LocalDate.now(), john);
        //Book not available therefore it should not allow Milan to loan it.
        assertFalse(CSIS.loan(book, LocalDate.now(), milan));
        CSIS.returnLoan(book);
        //Book now available so, it should assert true as John returned the loan.
        assertTrue(CSIS.loan(book, LocalDate.now().minusDays(15), milan));

    }

    /* Bibliographical search restricted to library opening hours. Slow, tedious
     * bibliographical search due to manipulation of card indexes.
     */

    /* Inaccurate search results, due to poor classification of books, journals or
     * proceedings within departments
     */

    @Test
    @DisplayName("Inaccurate search resutls Test")
    public void searchResultsTest() {
        Library UL = new Library();
        
        Book book = new Book("The Very Hungry Caterpillar", "Eric Carle", "Fiction");
        Book book1 = new Book("The Gruffalo", "Julia Donaldson", "Nature");
        Book book2 = new Book("Diper Överlöde: Diary of a Wimpy Kid", "Jeff Kinney", "Humor");

        UL.addBook(book);
        UL.addBook(book1);
        UL.addBook(book2);

        assertEquals(book, UL.searchTopic("Fiction").get(0));
    }
        
    /* Incomplete or ineffective search results, due to relevant books, journals or
     * proceedings being indexed in other UWON department libraries, or unavailable at
     * UWON.
     */

}
