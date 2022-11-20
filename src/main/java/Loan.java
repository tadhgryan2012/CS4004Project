import java.time.LocalDate;

public class Loan {
	private Book book;
	private LocalDate dateOfLoan;
	private User user;
	
	
	/**
	 * Makes a new loan object
	 * @param book
	 * @param dateOfLoan
	 * @param user
	 */
	public Loan(Book book, LocalDate dateOfLoan, User user) {
		this.book = book;
		this.dateOfLoan = dateOfLoan;
		this.user = user;
	}
	
	/** 
	 * Gets the User of the person who made the loan
	 * @return User
	 */
	public User getUser() {
		return user;
	}
	
	/** 
	 * Gets the date of the loan
	 * @return LocalDate
	 */
	public LocalDate getDateOfLoan() {
		return dateOfLoan;
	}
	
	@Override
	public String toString() {
		return String.format("%s was taken by %s at %s", book, user, dateOfLoan);
	}
}
