import java.util.ArrayList;

public class Book {
	private static int idIndicator;
	private int id;
	private String name;
	private String author;
	private String topic;
	private ArrayList<Loan> historyOfLoans;
	
	public Book(String name, String author, String topic) {
		this.id = idIndicator++;
		this.name = name;
		this.author = author;
		this.topic = topic;
		historyOfLoans = new ArrayList<>();
	}

	public void loan(Loan loan) {
		historyOfLoans.add(loan);
	}
	public ArrayList<Loan> getHistory() {
		return historyOfLoans;
	}

//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
	public String getName() {
		return name;
	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getAuthor() {
//		return author;
//	}
//
//	public void setAuthor(String author) {
//		this.author = author;
//	}

	public String getTopic() {
		return topic;
	}

//	public void setTopic(String topic) {
//		this.topic = topic;
//	}

	@Override
	public String toString() {
		return String.format("%s by %s on %s", name, author, topic);
	}
}
