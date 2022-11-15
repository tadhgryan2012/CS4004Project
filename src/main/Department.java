package main;

import java.time.LocalDate;
import java.util.ArrayList;

public class Department {
	private ArrayList<Loan> loans;
	private ArrayList<User> users;
	private ArrayList<Staff> staff;

	public Department() {
		loans = new ArrayList<>();
		users = new ArrayList<>();
		staff = new ArrayList<>();
	}

	public boolean loan(Book book, LocalDate date, User user) {
		Loan loan = new Loan(book, date, user);
		if (loans.contains(loan)) return false;
		book.loan(loan);
		loans.add(loan);
		return true;
   	}
	
	public boolean returnLoan(Book book, LocalDate date, User user) {
		return loans.remove(new Loan(book, date, user));
	}
	
	public ArrayList<Loan> getHistoryOfBook(Book book) {
		return book.getHistory();
	}

	public ArrayList<Loan> getLoans() {
		return loans;
	}

	public void setLoans(ArrayList<Loan> loans) {
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
}
