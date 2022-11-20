public class User {
	private static int idIdentifier;
	private int id;
	
	public User() {
		this.id = idIdentifier++;
	}
	
	@Override
	public String toString() {
		return String.format("%d", id);
	}
}