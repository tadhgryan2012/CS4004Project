public class Person {
	private static int idIdentifier;
	private int id;
	
	public Person() {
		this.id = idIdentifier++;
	}
	
	@Override
	public String toString() {
		return String.format("%d", id);
	}
}