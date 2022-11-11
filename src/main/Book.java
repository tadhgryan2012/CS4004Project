package main;

public class Book {
	private int id;
	private String name;
	private String author;
	private String topic;
	
	public Book(int id, String name, String author, String topic) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.topic = topic;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
}
