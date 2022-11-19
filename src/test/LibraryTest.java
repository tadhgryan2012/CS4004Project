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
     * Sophie
     * Unnecessary duplicate acquisition, by several departments, of infrequently accessed
     * copies of books or proceedings that are relevant to more than one department.
     */

    /* Unnecessary subscription by several departments to expensive journals that are
     * relevant to more than one department.
     */
    @Test
    @DisplayName("Unnecessary Subscriptions Test")      // Brian
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
    @DisplayName("University Sharing Books Test")       // Tadhg
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
    @DisplayName("Share Subscriptions Test")            // Tadhg
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

    /* Sophie
     * Unavailability of requested books, for a variety of reasons such as department
     * budget restrictions, excessive borrowing by the same user, lack of enforcement of
     * rules limiting loan periods, loss or stealing of book copies and so on.
     */

    /* Unavailability of journal issues while they are being bound into yearly volumes
     */
    @Test
    @DisplayName("Unavailability of monthly journal issues")
    public void unavailabilityOfMonthlyJournalIssues() {
        Library UL = new Library();
        Department Main = new Department(UL);
        User Chuck = new User();

        //We have an empty arraylist here which we can add each volume of the journal when they come out.
        ArrayList<Book> books = new ArrayList<>(Arrays.asList(
        ));

        Book book = new Book("Exploring the World Volume 1", "John Doe", "Educational");
        Book book2 = new Book("Exploring the World Volume 2", "John Doe", "Educational");
        Book book3 = new Book("Exploring the World Volume 3", "John Doe", "Educational");
        Book book4 = new Book("Exploring the World Volume 4", "John Doe", "Educational");

        //Each time a new volume of the journal comes out simply add the book to the collection "Exploring the World"
        books.add(book);
        books.add(book2);
        books.add(book3);
        books.add(book4);

        /* This is basically our yearly volume collection but we add them into it one by one when we get the new volumes.
        Next year you can create Exploring the World 2023 subscription.
        Through this subscription you get access to every volume at all times and its organised under one subscription so its easy to track volumes */
        Subscription sub = new Subscription("Exploring the World 2022", books);
        //UL has the subscription and has the choice to share it with other partnered Libraries.
        UL.addSub(sub);
        //Chuck loans one volume only from the journal
        assertTrue(Main.loan(book, LocalDate.now(), Chuck));
        //When the yearly volume is completed you just simply add it as its own book
        Book book5 = new Book("Exploring the World 2022 All Volumes", "John Doe", "Educational");
    }

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
        Book book = new Book("Mein Kampf", "Adolf Hitler", "Gospel");
        UL.addBook(book);

        CSIS.loan(book, LocalDate.now().minusDays(15), john);
        CSIS.returnLoan(book);

        CSIS.loan(book, LocalDate.now().minusDays(5), milan);
        CSIS.returnLoan(book);

        ArrayList<Loan> expectedResults = new ArrayList<>(Arrays.asList(
                new Loan(book, LocalDate.now().minusDays(15), john),
                new Loan(book, LocalDate.now().minusDays(5), milan)
        ));

        System.out.println("Expected Results: " + expectedResults.toString());
        System.out.println("Actual Results: " + CSIS.getHistoryOfBook(book).toString());
        assertEquals(expectedResults.toString(), CSIS.getHistoryOfBook(book).toString());
    }

    /* Inaccuracy of card indexes, e.g. a book is stated as being available whereas it is not
     * found at the appropriate place on the shelves.
     */
    @Test
    @DisplayName("Card Index Test")             // Brian
    public void cardIndexTest() {
        Library UL = new Library();
        Department Main = new Department(UL);
        User john = new User();
        User milan = new User();

        Book book = new Book("The Fellowship of the Ring", "J. R. R. Tolkien", "Fantasy");
        Book book2 = new Book("The Two Towers", "J. R. R. Tolkien", "Fantasy");
        Book book3 = new Book("The Return of the King", "J. R. R. Tolkien", "Fantasy");
        Book book4 = new Book("The Fellowship of the Ring", "J. R. R. Tolkien", "Fantasy");
        //each book has a unique id
        //NOTE: Doesnt work if you run this test individually as the ID's are 0,1,2,3 then.
        assertEquals(5, book.getId());
        assertEquals(6, book2.getId());
        assertEquals(7, book3.getId());
        assertEquals(8, book4.getId());
        Main.loan(book, LocalDate.now(), john);
        Main.loan(book2, LocalDate.now(), john);
        //Book not available therefore it should not allow Milan to loan it.
        assertFalse(Main.loan(book, LocalDate.now(), milan));
        //Same user tries loan the book twice returns false.
        assertFalse(Main.loan(book2, LocalDate.now(), john));
        //Book3 has not been loaned, it returns true and adds the loan to an arraylist.
        assertTrue(Main.loan(book3, LocalDate.now(), john));
        Main.returnLoan(book);
        //Book now available so, it should assert true as John returned the loan.
        assertTrue(Main.loan(book, LocalDate.now(), milan));
    }

    /* Bibliographical search restricted to library opening hours. Slow, tedious
     * bibliographical search due to manipulation of card indexes.
     */

    /* Sophie
     * Inaccurate search results, due to poor classification of books, journals or
     * proceedings within departments
     */

    /*Callum
     * Incomplete or ineffective search results, due to relevant books, journals or
     * proceedings being indexed in other UWON department libraries, or unavailable at
     * UWON.
     */
    @Test
    @DisplayName("Search other UWON departments test")          // Callum
    public void searchDepartment() {
        Library UL = new Library();
        Library UCC = new Library();
        ArrayList<Book> books = new ArrayList<Book>();
        Book floor = new Book("The Maze Runner", "James Dashner", "Action");
        Book ceiling = new Book("The Scorch Trials", "James Dashner", "Action" );
        books.add(floor);
        books.add(ceiling);
        UL.addBook(floor);
        UL.addBook(ceiling);

       Subscription sub = new Subscription("Maze Runner Trilogy", books);
       UL.addSub(sub);


        assertEquals(sub, UL.searchByTitle("the"));



       // UL.addSub(sub);
       // UL.addAggreement(UCC);
       // UL.shareSub(UCC, sub);
       // assertEquals(UL.getSubs(), UCC.getSubs());
    }
    
}
