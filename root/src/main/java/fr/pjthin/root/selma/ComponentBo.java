package fr.pjthin.root.selma;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComponentBo {
    
    private String currency;
    private BigDecimal value;
    private ExpressionTypeBo type;

}
