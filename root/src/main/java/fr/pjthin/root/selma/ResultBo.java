package fr.pjthin.root.selma;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultBo {
    
    private String login;
    private ComponentBo commission;
    private ComponentBo fees;
    private ComponentBo discount;

}
