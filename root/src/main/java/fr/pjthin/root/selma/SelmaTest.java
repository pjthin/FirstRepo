package fr.pjthin.root.selma;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import fr.xebia.extras.selma.Selma;

public class SelmaTest {

	public static void main(String[] args) {
		UserMapper mapper = Selma.builder(UserMapper.class).build();
		UserBo bo = new UserBo(1, "user1", Date.from(LocalDateTime.now().minusYears(1).minusHours(5)
				.toInstant(ZoneOffset.UTC)), new Date());
		System.out.println(bo);
		UserDto dto = mapper.asDto(bo);
		System.out.println(dto);
	}

}
