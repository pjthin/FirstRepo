package fr.pjthin.root.selma;

import java.text.DateFormat;
import java.util.Date;

import fr.pjthin.root.selma.UserMapper.DateToStringMapper;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

@Mapper(withIgnoreMissing = IgnoreMissing.DESTINATION, withCustom = DateToStringMapper.class)
public interface UserMapper {

	UserDto asDto(UserBo bo);

	// UserVo asVo(UserDto dto);

	static class DateToStringMapper {
		public String asString(Date date) {
			return "date=" + DateFormat.getInstance().format(date);
		}
	}

}