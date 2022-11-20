import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Department {
	private Library library;
	private HashMap<Book, Loan> loans;
	private ArrayList<User> users;


	/**
	 * Makes a department using a Library object that the department is under
	 * @param library
	 */
	public Department(Library library) {
		this.library = library;
		loans = new HashMap<>();
		users = new ArrayList<>();
	}

	/** 
	 * Loans a book to a user. Will return false if cannot loan the book. 
	 * <p> Won't be able to loan if the user has loaned the book in the last 6 months already or if 
	 * the book is on loan already or the library doesn't have the book. </p>
	 * @param book
	 * @param date
	 * @param user
	 * @return boolean
	 */
	public boolean loan(Book book, LocalDate date, User user) {
		for(Loan loan : book.getHistory()) {
			if (!loan.getUser().equals(user)){
				continue;
			}
			if (loan.getDateOfLoan().isAfter(date.minusMonths(6))){
				return false;
			}
		}
		Loan loan = new Loan(book, date, user);
		if (loans.containsKey(book)) return false;
		if (!library.removeBook(book)) return false;
		book.loan(loan);
		loans.put(book, loan);
		return true;	
	}
	
	/** 
	 * Returns the book to the library. Returns false if the book isnt loaned out.
	 * @param book
	 * @return boolean
	 */
	public boolean returnLoan(Book book) {
		if (loans.remove(book) == null) return false;
		library.addBook(book);
		return true;
	}

	
	/** 
	 * Gets the history of loans on the book
	 * @param book
	 * @return ArrayList<Loan>
	 */
	public ArrayList<Loan> getHistoryOfBook(Book book) {
		return book.getHistory();
	}

    
	/** 
	 * Gets the books that the library the department is attached to has
	 * @return ArrayList<Book>
	 */
	public ArrayList<Book> getBooks() {
        return library.getBooks();
    }
}
