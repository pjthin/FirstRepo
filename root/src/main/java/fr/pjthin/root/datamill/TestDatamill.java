package fr.pjthin.root.datamill;

import java.math.BigDecimal;

public class TestDatamill {

	static class MonObjet {
		private int id;
		private String name;
		private String description;
		private BigDecimal price;

		public int getId() {
			return id;
		}

		public MonObjet setId(int id) {
			this.id = id;
			return this;
		}

		public String getName() {
			return name;
		}

		public MonObjet setName(String name) {
			this.name = name;
			return this;
		}

		public String getDescription() {
			return description;
		}

		public MonObjet setDescription(String description) {
			this.description = description;
			return this;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public MonObjet setPrice(BigDecimal price) {
			this.price = price;
			return this;
		}

	}

	public class SystemUser {
		private String firstName;

		public String getFirstName() {
			return firstName;
		}
	}

	public static void main(String[] arguments) {
//		Outline<SystemUser> userOutline = new OutlineBuilder(SystemUser.class).defaultSnakeCased().build();
//		String entityName = userOutline.name(); // returns "system_user"
//		String propertyName = userOutline.name(userOutline.members().getFirstName()); // returns
//																						// "first_name"
	}

}
