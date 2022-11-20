import java.util.ArrayList;

public class Book {
	private static int idIndicator;
	private int id;
	private String name;
	private String author;
	private String topic;
	private ArrayList<Loan> historyOfLoans;
	
	/**
	 * Makes a book object
	 * @param name
	 * @param author
	 * @param topic
	 */
	public Book(String name, String author, String topic) {
		this.id = idIndicator++;
		this.name = name;
		this.author = author;
		this.topic = topic;
		historyOfLoans = new ArrayList<>();
	}
	
	/** 
	 * Adds loan to the history of loans on this book
	 * @param loan
	 */
	public void loan(Loan loan) {
		historyOfLoans.add(loan);
	}
	
	/** 
	 * Gets the history of loans
	 * @return ArrayList<Loan>
	 */
	public ArrayList<Loan> getHistory() {
		return historyOfLoans;
	}
	
	/** 
	 * Gets the name of the book
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/** 
	 * Gets the topic of the book
	 * @return String
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Gets ID
	 * @return
	 */
	public int getID() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("%s by %s on %s", name, author, topic);
	}
}
