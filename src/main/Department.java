package main;

import java.util.ArrayList;

public class Department {
	private ArrayList<Book> books;
	private ArrayList<Loan> loans;
	private ArrayList<User> users;
	private ArrayList<Staff> staff;

	public Department() {
		this.books = new ArrayList<>();
		this.loans = new ArrayList<>();
		this.users = new ArrayList<>();
		this.staff = new ArrayList<>();
	}

	public ArrayList<Book> getBooks() {
		return books;
	}

	public void setBooks(ArrayList<Book> books) {
		this.books = books;
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
	
	public void borrow(Department lib, Book book) {

	}
}
