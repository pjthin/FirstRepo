package fr.pjthin.root.selma;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import fr.xebia.extras.selma.Selma;

public class SelmaTest {

    public static void main(String[] args) {
        ResultBo result = ResultBo.builder()
                .commission(ComponentBo.builder().currency("EUR").type(ExpressionTypeBo.AM).value(new BigDecimal("15")).build())
                // .discount(ComponentBo.builder().currency(null).type(ExpressionTypeBo.PE).value(new BigDecimal("0.5")).build())
                .fees(ComponentBo.builder().currency("EUR").type(ExpressionTypeBo.AM).value(new BigDecimal("789")).build())
                .login("userTest").build();
        ResultMapper m = Selma.builder(ResultMapper.class).build();
        ResultDto resultDto = m.map(result);
        System.out.println(result);
        System.out.println(resultDto);

        UserMapper mapper = Selma.builder(UserMapper.class).build();
        UserBo bo = new UserBo(1, "user1", Date.from(LocalDateTime.now().minusYears(1).minusHours(5)
                .toInstant(ZoneOffset.UTC)), new Date());
        System.out.println(bo);
        UserDto dto = mapper.asDto(bo);
        System.out.println(dto);
    }

}
