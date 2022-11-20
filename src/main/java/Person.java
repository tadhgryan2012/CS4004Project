public class Person {
	private static int idIdentifier;
	private int id;
	
	public Person() {
		this.id = idIdentifier++;
	}

//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

	@Override
	public String toString() {
		return String.format("%d", id);
	}
}