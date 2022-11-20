import java.time.LocalDate;

public class Loan {
	private Book book;
	private LocalDate dateOfLoan;
	private User user;
	
	
	public Loan(Book book, LocalDate dateOfLoan, User user) {
		this.book = book;
		this.dateOfLoan = dateOfLoan;
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

	public LocalDate getDateOfLoan() {
		return dateOfLoan;
	}

	@Override
	public String toString() {
		return String.format("%s was taken by %s at %s", book, user, dateOfLoan);
	}
}
