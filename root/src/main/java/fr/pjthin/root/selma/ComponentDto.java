package fr.pjthin.root.selma;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentDto {

    private String name;
    private String currency;
    private BigDecimal value;
    private ExpressionTypeDto type;

}
