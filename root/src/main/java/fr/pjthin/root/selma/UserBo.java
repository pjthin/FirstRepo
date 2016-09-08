package fr.pjthin.root.selma;

import java.util.Date;

public class UserBo {
	@Override
	public String toString() {
		return "UserBo [id=" + id + ", login=" + login + ", created=" + created + ", lastConnection=" + lastConnection
				+ "]";
	}

	int id;
	String login;
	Date created;
	Date lastConnection;

	public UserBo() {
	}

	public UserBo(int id, String login, Date created, Date lastConnection) {
		super();
		this.id = id;
		this.login = login;
		this.created = created;
		this.lastConnection = lastConnection;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastConnection() {
		return lastConnection;
	}

	public void setLastConnection(Date lastConnection) {
		this.lastConnection = lastConnection;
	}

}
