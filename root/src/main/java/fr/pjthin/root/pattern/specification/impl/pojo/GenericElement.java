package fr.pjthin.root.pattern.specification.impl.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericElement {

    private Map<Class<?>, List<Object>> elements;

    public GenericElement() {
        elements = new HashMap<>();
    }

    public void put(Object element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        if (elements.get(element.getClass()) == null) {
            elements.put(element.getClass(), new ArrayList<>());
        }
        elements.get(element.getClass()).add(element);
    }

    public List<Object> get(Class<?> klass) {
        return elements.get(klass);
    }

}
