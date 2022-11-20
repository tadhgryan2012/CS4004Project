import java.util.ArrayList;

public class Library {
	private ArrayList<Subscription> subs;
	private ArrayList<Book> books;
	private ArrayList<Library> agreements;


	public Library() {
		subs = new ArrayList<>();
		agreements = new ArrayList<>();
		books = new ArrayList<>();
	}

	public ArrayList<Book> getBooks() {
		return books;
	}
	public void addBook(Book book) {
		books.add(book);
	}
	public boolean removeBook(Book book) {
		return books.remove(book);
	}

	public void addAgreement(Library lib) {
		agreements.add(lib);
		lib.agreements.add(this);
	}
	public void shareSub(Library lib, Subscription sub) {
		lib.addSub(sub);
	}
	public boolean borrowBook(Library lib, Book book) {
		if (!agreements.contains(lib)) return false;
		if (!lib.books.remove(book)) return false;
		books.add(book);
		return true;
	}
	public boolean returnBook(Library lib, Book book) {
		if (!books.remove(book)) return false;
		lib.books.add(book);
		return true;
	}

	public ArrayList<Subscription> getSubs() {
		return subs;
	}
	public void addSub(Subscription sub) {
		subs.add(sub);
	}

	public ArrayList<Book> searchByTitle(String title) {
		ArrayList<Book> tempBooks = new ArrayList<>();
		for (Book book : books) {
			if (book.getName().toLowerCase().contains(title.toLowerCase())) {
				tempBooks.add(book);
			}
		}
		return tempBooks;
	}

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
