package fr.pjthin.root.selma;

import java.util.Date;

public class UserDto {
	@Override
	public String toString() {
		return "UserDto [login=" + login + ", lastConnection=" + lastConnection + "]";
	}

	String login;
	Date lastConnection;

	public UserDto() {
	}

	public UserDto(String login, Date lastConnection) {
		super();
		this.login = login;
		this.lastConnection = lastConnection;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Date getLastConnection() {
		return lastConnection;
	}

	public void setLastConnection(Date lastConnection) {
		this.lastConnection = lastConnection;
	}
}