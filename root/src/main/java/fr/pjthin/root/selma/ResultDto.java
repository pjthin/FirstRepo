package fr.pjthin.root.selma;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultDto {
    
    private String login;
    private List<ComponentDto> components;

}
