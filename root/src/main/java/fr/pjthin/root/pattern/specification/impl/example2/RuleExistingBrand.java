package fr.pjthin.root.pattern.specification.impl.example2;

import java.util.List;

import fr.pjthin.root.pattern.specification.impl.AbstractRules;

public class RuleExistingBrand extends AbstractRules<MyCarPOJO> {

    @Override
    protected Class<MyCarPOJO> getElementClass() {
        return MyCarPOJO.class;
    }

    @Override
    protected boolean isRuleSatisfiedBy(List<MyCarPOJO> list) {
        MyCarPOJO c = list.get(0);
        boolean exist = false;
        switch (c.getCarBrand()) {
        case "FIAT":
            exist = true;
            break;

        case "PEUGEOT":
            exist = true;
            break;

        case "FERRARI":
            exist = true;
            break;

        case "MERCEDES":
            exist = true;
            break;

        default:
            break;
        }

        return exist;
    }

}
