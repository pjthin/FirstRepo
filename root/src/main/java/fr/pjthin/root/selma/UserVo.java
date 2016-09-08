package fr.pjthin.root.selma;

public class UserVo {

	@Override
	public String toString() {
		return "UserVo [username=" + username + ", lastConnection=" + lastConnection + "]";
	}

	String username;
	String lastConnection;

	public UserVo() {
	}

	public UserVo(String username, String lastConnection) {
		super();
		this.username = username;
		this.lastConnection = lastConnection;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLastConnection() {
		return lastConnection;
	}

	public void setLastConnection(String lastConnection) {
		this.lastConnection = lastConnection;
	}
}