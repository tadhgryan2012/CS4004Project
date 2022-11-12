package test;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import main.Book;
import main.Department;
import main.Library;
import main.Loan;
import main.Subscription;
import main.User;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class LibraryTest {
	/*
	 * Unnecessary duplicate acquisition, by several departments, of infrequently accessed
	 * copies of books or proceedings that are relevant to more than one department.
	 */

	/* Unnecessary subscription by several departments to expensive journals that are
	 * relevant to more than one department.
	 */

	/* Acquisition of books or proceedings of marginal interest to the university, which
	 * could be borrowed from other universities with which UWON has an agreement.
	 */
	@Test
	@DisplayName("University sharing books Test")
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

	/* Unavailability of journal issues while they are being bound into yearly volumes.
	 */

	/* Lack of traceability to previous borrowers when books, proceedings or journal
	 * volumes are found to be damaged.
	 */
	@Test
	@DisplayName("Show History of Ownership Test")
	public void traceabilityTest() {
		Department CSIS = new Department();

		User john = new User();
		User milan = new User();
		Book book = new Book("Mein Kampf", "Adolf Hitler", "Gospel");

		CSIS.loan(book, LocalDate.now().minusDays(15), john);
		CSIS.returnLoan(book, LocalDate.now().minusDays(10), john);

		CSIS.loan(book, LocalDate.now().minusDays(5), milan);
		CSIS.returnLoan(book, LocalDate.now(), milan);

		ArrayList<Loan> expectedResults = new ArrayList<>(Arrays.asList(
			new Loan(book, LocalDate.now().minusDays(15), john), 
			new Loan(book, LocalDate.now().minusDays(5), milan)
		));

		assertTrue(expectedResults.toString().equals(CSIS.getHistoryOfBook(book).toString()));		
	}

	/* Inaccuracy of card indexes, e.g. a book is stated as being available whereas it is not
	 * found at the appropriate place on the shelves.
 	 */

	/* Bibliographical search restricted to library opening hours. Slow, tedious
	 * bibliographical search due to manipulation of card indexes.
	 */
	
	/* Inaccurate search results, due to poor classification of books, journals or
	 * proceedings within departments.
	 */

	/* Incomplete or ineffective search results, due to relevant books, journals or
	 * proceedings being indexed in other UWON department libraries, or unavailable at
	 * UWON.
	 */
	
}
