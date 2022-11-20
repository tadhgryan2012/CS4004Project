import java.util.ArrayList;

public class Subscription {
	private String name;
	private ArrayList<Book> books;
	
	/**
	 * Makes a new Subscription
	 * @param name
	 * @param books
	 */
	public Subscription(String name, ArrayList<Book> books) {
		this.name = name;
		this.books = books;
	}
	
	/** 
	 * Gets the name
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/** 
	 * Gets the list of books in the subscription
	 * @return ArrayList<Book>
	 */
	public ArrayList<Book> getBooks() {
		return books;
	}
}
