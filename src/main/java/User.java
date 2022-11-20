public class User {
	private static int idIdentifier;
	private int id;
	
	/**
	 * Makes a new User
	 */
	public User() {
		this.id = idIdentifier++;
	}

	@Override
	public String toString() {
		return String.format("%d", id);
	}
}
