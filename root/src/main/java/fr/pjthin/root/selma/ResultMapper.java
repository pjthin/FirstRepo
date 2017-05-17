package fr.pjthin.root.selma;

import java.util.ArrayList;
import java.util.List;

import fr.xebia.extras.selma.EnumMapper;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;
import fr.xebia.extras.selma.Maps;

@Mapper(withIgnoreMissing = IgnoreMissing.DESTINATION)
public abstract class ResultMapper {

    @Maps(withIgnoreFields = "name", withEnums = @EnumMapper(from = ExpressionTypeBo.class, to = ExpressionTypeDto.class))
    public abstract ComponentDto map(ComponentBo comp);

    @Maps(withIgnoreFields = { "components" })
    public abstract ResultDto map(ResultBo result);

    public void intercept(ResultBo in, ResultDto out) {
        List<ComponentDto> components = new ArrayList<>();
        if (in.getCommission() != null) {
            ComponentDto map = map(in.getCommission());
            map.setName("commission");
            components.add(map);
        }
        if (in.getDiscount() != null) {
            ComponentDto map = map(in.getDiscount());
            map.setName("discount");
            components.add(map);
        }
        if (in.getFees() != null) {
            ComponentDto map = map(in.getFees());
            map.setName("fees");
            components.add(map);
        }
        out.setComponents(components);
    }
}
