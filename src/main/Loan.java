package main;

import java.time.LocalDate;

public class Loan {
	private Book book;
	private LocalDate dateOfLoan;
	private LocalDate dateOfReturn;
	private User user;
	
	
	public Loan(Book book, LocalDate dateOfLoan, User user) {
		this.book = book;
		this.dateOfLoan = dateOfLoan;
		this.user = user;
	}
	public Loan(Book book, LocalDate dateOfLoan, LocalDate dateOfReturn, User user) {
		this.book = book;
		this.dateOfLoan = dateOfLoan;
		this.dateOfReturn = dateOfReturn;
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LocalDate getDateOfLoan() {
		return dateOfLoan;
	}

	public void setDateOfLoan(LocalDate dateOfLoan) {
		this.dateOfLoan = dateOfLoan;
	}

	public LocalDate getDateOfReturn() {
		return dateOfReturn;
	}
	public void setDateOfReturn(LocalDate dateOfReturn) {
		this.dateOfReturn = dateOfReturn;
	}

	@Override
	public String toString() {
		return String.format("%s was taken by %s at %s", book, user, dateOfLoan);
	}
}
