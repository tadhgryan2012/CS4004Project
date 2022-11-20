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

    /* Acquisition of books or proceedings of marginal interest to the university, which
     * could be borrowed from other universities with which UWON has an agreement
     */
    @Test
    @DisplayName("University Sharing Books Test")           // Tadhg
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
    @DisplayName("Share Subscriptions Test")                // Tadhg
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

    /* 
     * Unnecessary duplicate acquisition, by several departments, of infrequently accessed
     * copies of books or proceedings that are relevant to more than one department.
     */
    @Test
    @DisplayName("Unnecessary Duplicates Test")                 // Sophie
    public void unnecessaryDupTest() {
        Library UL = new Library(); 
        Department CSIS = new Department(UL);
        Department Kemmy = new Department(UL);
        Book book = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction");

        UL.addBook(book);    
        assertEquals(CSIS.getBooks(), Kemmy.getBooks());
    }

    /* Unavailability of requested books, for a variety of reasons such as department
     * budget restrictions, excessive borrowing by the same user, lack of enforcement of
     * rules limiting loan periods, loss or stealing of book copies and so on.
     */
    @Test
    @DisplayName("Unavailability of books Test")        // Sophie
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
    
    /* Inaccurate search results, due to poor classification of books, journals or
     * proceedings within departments
     */
    @Test
    @DisplayName("Inaccurate search resutls Test")          // Sophie
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
    
    /* Unnecessary subscription by several departments to expensive journals that are
     * relevant to more than one department.
     */
    @Test
    @DisplayName("Unnecessary Subscriptions Test")              // Brian
    public void unnecessarySubTest() {
        Library UL = new Library();
        Library UCC = new Library();
        Library Trinity = new Library();

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
        UL.addAggreement(Trinity);
        UL.shareSub(Trinity, subs);
        assertEquals(UL.getSubs(), UCC.getSubs());
        assertEquals(UCC.getSubs(), Trinity.getSubs());
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
        assertEquals(0, book.getId());
        assertEquals(1, book2.getId());
        assertEquals(2, book3.getId());
        assertEquals(3, book4.getId());
        //Since its taking the time now we can minus days for the loan so the loan is from X days ago, and use plusDays when returning.
        Main.loan(book, LocalDate.now().minusDays(2), john);
        Main.loan(book2, LocalDate.now().minusDays(3), john);
        //Book not available therefore it should not allow Milan to loan it.
        assertFalse(Main.loan(book, LocalDate.now().minusDays(4), milan));
        //Same user tries loan the book twice returns false.
        assertFalse(Main.loan(book2, LocalDate.now().minusDays(5), john));
    }
    
    /* Unavailability of journal issues while they are being bound into yearly volumes
     */
    @Test
    @DisplayName("Unavailability of monthly journal issues")    //Brian
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

        UL.addBook(book);
        UL.addBook(book2);
        UL.addBook(book3);
        UL.addBook(book4);
        /* This is basically our yearly volume collection but we add them into it one by one when we get the new volumes.
        Next year you can create Exploring the World 2023 subscription.
        Through this subscription you get access to every volume at all times and its organised under one subscription so its easy to track volumes */
        Subscription sub = new Subscription("Exploring the World", books);
        //UL has the subscription and has the choice to share it with other partnered Libraries.
        UL.addSub(sub);
        //Chuck loans one volume only from the journal
        assertEquals(books, UL.getBooks());
        //When the yearly volume is completed you just simply add it as its own book
        Book book5 = new Book("Exploring the World 2022 All Volumes", "John Doe", "Educational");
    }
    
    /* Bibliographical search restricted to library opening hours. Slow, tedious
     * bibliographical search due to manipulation of card indexes.
     */
        
    /* Incomplete or ineffective search results, due to relevant books, journals or
     * proceedings being indexed in other UWON department libraries, or unavailable at
     * UWON.
     */
}
