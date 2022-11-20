import java.util.ArrayList;

public class Library {
	private ArrayList<Subscription> subs;
	private ArrayList<Book> books;
	private ArrayList<Library> agreements;


	/**
	 * Makes a library object
	 */
	public Library() {
		subs = new ArrayList<>();
		agreements = new ArrayList<>();
		books = new ArrayList<>();
	}

	
	/** 
	 * Gets the books that the library has access to
	 * @return ArrayList<Book>
	 */
	public ArrayList<Book> getBooks() {
		return books;
	}
	
	/** 
	 * Adds a book to the library's collection
	 * @param book
	 */
	public void addBook(Book book) {
		books.add(book);
	}
	
	/** 
	 * Removes a book from the library. Returns false if the library does not contain the book.
	 * @param book
	 * @return boolean
	 */
	public boolean removeBook(Book book) {
		return books.remove(book);
	}
	
	/** 
	 * Adds an agreement between libraries so that they can borrow books and share subscriptions
	 * @param lib
	 */
	public void addAgreement(Library lib) {
		agreements.add(lib);
		lib.agreements.add(this);
	}
	
	/** 
	 * Adds a subscription to the library passed. If this library doesn't have the subscription, it will return false otherwise true.
	 * @param lib
	 * @param sub
	 */
	public boolean shareSub(Library lib, Subscription sub) {
		if (!subs.contains(sub)) return false;
		lib.addSub(sub);
		return true;
	}
	
	/** 
	 * Removes the book from passed library and adds it to this library.
	 * <p> Returns false if libraries don't have an agreement or passed library does not contain book. </p>
	 * @param lib
	 * @param book
	 * @return boolean
	 */
	public boolean borrowBook(Library lib, Book book) {
		if (!agreements.contains(lib)) return false;
		if (!lib.books.remove(book)) return false;
		books.add(book);
		return true;
	}
	
	/** 
	 * Returns the book to the library passed by removing it from this library and adding it to library passed.
	 * <p> Returns false if this library does not contain book. </p>
	 * @param lib
	 * @param book
	 * @return boolean
	 */
	public boolean returnBook(Library lib, Book book) {
		if (!books.remove(book)) return false;
		lib.books.add(book);
		return true;
	}

	
	/** 
	 * Gets the list of subscriptions
	 * @return ArrayList<Subscription>
	 */
	public ArrayList<Subscription> getSubs() {
		return subs;
	}
	
	/** 
	 * Adds a subscription
	 * @param sub
	 */
	public void addSub(Subscription sub) {
		subs.add(sub);
	}

	
	/** 
	 * Returns a list of Book that contain the parameter, not case sensitive, in the title using a linear search method.
	 * @param title
	 * @return ArrayList<Book>
	 */
	public ArrayList<Book> searchByTitle(String title) {
		ArrayList<Book> tempBooks = new ArrayList<>();
		for (Book book : books) {
			if (book.getName().toLowerCase().contains(title.toLowerCase())) {
				tempBooks.add(book);
			}
		}
		return tempBooks;
	}

    
	/** 
	 * Returns a list of Book that contain the parameter, not case sensitive, in the topic using a linear search method.
	 * @param topic
	 * @return ArrayList<Book>
	 */
	public ArrayList<Book> searchByTopic(String topic) {
		ArrayList<Book> tempBooks = new ArrayList<>();
		for (Book book : books){
			if (topic.toLowerCase().contains(book.getTopic().toLowerCase())) {
				tempBooks.add(book);
			}
		}
        return tempBooks;
    }
}
