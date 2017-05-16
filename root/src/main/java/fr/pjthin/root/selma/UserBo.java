package fr.pjthin.root.selma;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBo {

	private int id;
	private String login;
	private Date created;
	private Date lastConnection;

}
