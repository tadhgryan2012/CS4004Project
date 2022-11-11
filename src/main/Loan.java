package main;

import java.time.LocalDate;

public class Loan {
	private Book book;
	private LocalDate dateOfLoan;
	
	public Loan(Book book, LocalDate dateOfLoan) {
		this.book = book;
		this.dateOfLoan = dateOfLoan;
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
}
