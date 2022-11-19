package main;

import java.util.ArrayList;

public class Library {
	private ArrayList<Department> deps;
	private ArrayList<Subscription> subs;
	private ArrayList<Book> books;
	private ArrayList<Library> aggreements;


	public Library() {
		deps = new ArrayList<>();
		subs = new ArrayList<>();
		aggreements = new ArrayList<>();
		books = new ArrayList<>();
	}

	public ArrayList<Book> getBooks() {
		return books;
	}
	public void setBooks(ArrayList<Book> books) {
		this.books = books;
	}
	public void addBook(Book book) {
		books.add(book);
	}
	public boolean removeBook(Book book) {
		return books.remove(book);
	}

	public ArrayList<Library> getAggreements() {
		return aggreements;
	}
	public void setAggreements(ArrayList<Library> aggreements) {
		this.aggreements = aggreements;
	}
	public void addAggreement(Library lib) {
		aggreements.add(lib);
		lib.aggreements.add(this);
	}
	public void shareSub(Library lib, Subscription sub) {
		lib.addSub(sub);
	}
	public void borrowBook(Library lib, Book book) {
		books.add(book);
		lib.books.remove(book);
	}
	public void returnBook(Library lib, Book book) {
		books.remove(book);
		lib.books.add(book);
	}

	public ArrayList<Subscription> getSubs() {
		return subs;
	}
	public void setSubs(ArrayList<Subscription> subs) {
		this.subs = subs;
	}
	public void addSub(Subscription sub) {
		subs.add(sub);
	}

	public ArrayList<Department> getDeps() {
		return deps;
	}
	public void setDeps(ArrayList<Department> deps) {
		this.deps = deps;
	}
	public void addDepartment(Department dep) {
		deps.add(dep);
	}
	public Book[] searchByTitle(String title){
		title = title.toLowerCase() ;

		int numberOfBooks = 0;

		for (int i = 0; i < books.size(); i++){
			if (books.get(i).getName().toLowerCase().contains(title)){
				numberOfBooks++ ;

			}
		}
		Book[] results = new Book[numberOfBooks];
		int count = 0 ;
		for(int i = 0; i < books.size(); i++){
			if (books.get(i).getName().toLowerCase().contains(title)){
				results[count] = books.get(i);
						count++;
			}


		}
		return results ;



	}
}
