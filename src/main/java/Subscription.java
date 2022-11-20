import java.util.ArrayList;

public class Subscription {
	private String name;
	private ArrayList<Book> books;
	
	public Subscription(String name, ArrayList<Book> books) {
		this.name = name;
		this.books = books;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Book> getBooks() {
		return books;
	}

	public void setBooks(ArrayList<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		StringBuilder returnString = new StringBuilder(name + ": ");
		books.forEach(book -> {
			returnString.append(book + ", ");
		});
		return returnString.delete(returnString.length()-2, returnString.length()).toString();
	}
}
