import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

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
        UL.addAgreement(UCC);
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
        UL.addAgreement(UCC);
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
    @DisplayName("Inaccurate search results Test")          // Sophie
    public void searchResultsTest() {
        Library UL = new Library();
        
        Book book = new Book("The Very Hungry Caterpillar", "Eric Carle", "Fiction");
        Book book1 = new Book("The Gruffalo", "Julia Donaldson", "Nature");
        Book book2 = new Book("Diper Överlöde: Diary of a Wimpy Kid", "Jeff Kinney", "Humor");

        UL.addBook(book);
        UL.addBook(book1);
        UL.addBook(book2);

        assertEquals(book, UL.searchByTopic("Fiction").get(0));
    }
    
    /* Unnecessary subscription by several departments to expensive journals that are
     * relevant to more than one department.
     */
    @Test
    @DisplayName("Unnecessary Subscriptions Test")              // Brian
    public void unnecessarySubTest() {
        Library UL = new Library();
        Department CSIS = new Department(UL);
        Department Biology = new Department(UL);
        Department Science = new Department(UL);

        ArrayList<Book> books = new ArrayList<>(Arrays.asList(
                new Book("A Game of Thrones", "George R. R. Martin", "Fantasy"),
                new Book("A clash of Kings", "George R. R. Martin", "Fantasy"),
                new Book("A Storm of Swords", "George R. R. Martin", "Fantasy")
        ));

        Subscription subs = new Subscription("Game of Thrones Trilogy", books);

        UL.addSub(subs);
        //No single department has subs, only UL as a whole, Duplicates not possible.
        assertNotEquals(books, CSIS.getBooks());
        assertNotEquals(books, Biology.getBooks());
        assertNotEquals(books, Science.getBooks());
        assertEquals(0, CSIS.getBooks().size());

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
        assertEquals(book.getID()+1, book2.getID());
        assertEquals(book2.getID()+1, book3.getID());
        assertEquals(book3.getID()+1, book4.getID());
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

    @Test
    @DisplayName("Loan Tests")      // Tadhg
    public void loanTest() {
        Library UL = new Library();
        Department CSIS = new Department(UL);
        User Tadhg = new User();
        User Breny = new User();

        Book book = new Book("Minecraft Handbook", "Notch", "Biblical");
        UL.addBook(book);

        assertTrue(CSIS.loan(book, LocalDate.now(), Tadhg));
        assertFalse(CSIS.loan(book, LocalDate.now(), Breny));
        assertTrue(CSIS.returnLoan(book));
        assertFalse(CSIS.returnLoan(book));
        assertFalse(CSIS.loan(book, LocalDate.now(), Tadhg));
    }

    @Test
    @DisplayName("Library Borrowing Tests")         // Tadhg
    public void borrowingTest() {
        Library DCU = new Library();
        Library UCC = new Library();
        Library NUIG = new Library();

        Book book1 = new Book("Dictionary", "William Shakespeare", "Humour");
        Book book2 = new Book("Thesaurus", "Tadhg Ryan", "Humour");

        UCC.addBook(book1);
        NUIG.addBook(book2);
        NUIG.addAgreement(UCC);
        DCU.addAgreement(NUIG);

        assertFalse(DCU.borrowBook(UCC, book1));
        DCU.addAgreement(UCC);
        assertTrue(DCU.borrowBook(UCC, book1));

        assertFalse(NUIG.borrowBook(UCC, book1));

        assertTrue(DCU.returnBook(UCC, book1));
        assertFalse(DCU.returnBook(NUIG, book2));
    }
    
    @Test
    @DisplayName("Search by Title Test")            // Tadhg
    public void searchByTitleTest() {
        Library LIT = new Library();
        Book book1 = new Book("Beast Quest", "Adam Blake", "Fantasy");
        Book book2 = new Book("Gardening 101", "Martha Stewart", "DIY");
        Book book3 = new Book("100 Tips for Hoteliers", "Peter Venison", "Life Style");
        LIT.addBook(book1);
        LIT.addBook(book2);
        LIT.addBook(book3);
        
        assertEquals(book1, LIT.searchByTitle("Beast").get(0));
        assertEquals(new ArrayList<Book>(), LIT.searchByTitle("This will return an empty set"));
    }

    @Test
    @DisplayName("Subscriptions Test")          // Tadhg
    public void subTest() {
        ArrayList<Book> comics = new ArrayList<>();
        comics.add(new Book("Superman", "Clark Kent", "Action"));
        comics.add(new Book("The Flash", "Barry Allen", "Speed"));

        Subscription sub1 = new Subscription("Milan's Comics", comics);

        assertEquals(sub1.getBooks(), comics);
        assertEquals(sub1.getName(), "Milan's Comics");
    }
    
    @Test
    @DisplayName("Search other UWON departments test")          // Callum
    public void searchDepartment() {
        Library UL = new Library();
        Library UCC = new Library();

        Book floor = new Book("The Maze Runner", "James Dashner", "Action");
        Book ceiling = new Book("The Scorch Trials", "James Dashner", "Action" );

        UL.addBook(floor);
        UL.addBook(ceiling);

        UCC.addBook(floor);
        UCC.addBook(ceiling);

        //Both libraries have 2 search results as they both have the same books.
        assertEquals(UL.searchByTitle("The").length, UCC.searchByTitle("the").length);

        Book wall = new Book("The Death Cure", "James Dashner", "Action");

        UCC.addBook(wall);

        //UL has 2 results while UCC has 3 since Ul does not have the third book.
        assertNotEquals(UL.searchByTitle("The").length, UCC.searchByTitle("the").length);

    }
}
