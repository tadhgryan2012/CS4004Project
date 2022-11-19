package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Department {
	private Library library;
	private HashMap<Book, Loan> loans;
	private ArrayList<User> users;
	private ArrayList<Staff> staff;

	public Department(Library library) {
		this.library = library;
		loans = new HashMap<>();
		users = new ArrayList<>();
		staff = new ArrayList<>();
	}

	public boolean loan(Book book, LocalDate date, User user) {
		Loan loan = new Loan(book, date, user);
		if (loans.containsKey(book)) return false;
		if (!library.removeBook(book)) return false;
		book.loan(loan);
		loans.put(book, loan);
		return true;
	}
	public boolean returnLoan(Book book) {
		if (loans.remove(book) == null) return false;
		library.addBook(book);
		return true;
	}
	
	public Library getLibrary() {
		return library;
	}

	public ArrayList<Loan> getHistoryOfBook(Book book) {
		return book.getHistory();
	}

	public HashMap<Book, Loan> getLoans() {
		return loans;
	}

	public void setLoans(HashMap<Book, Loan> loans) {
		this.loans = loans;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public ArrayList<Staff> getStaff() {
		return staff;
	}

	public void setStaff(ArrayList<Staff> staff) {
		this.staff = staff;
	}

    public ArrayList<Book> getBooks() {
        return library.getBooks();
    }
}
