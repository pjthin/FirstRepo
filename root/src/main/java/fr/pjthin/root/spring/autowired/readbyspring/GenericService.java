package fr.pjthin.root.spring.autowired.readbyspring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Say to Spring that it's a component (may use an @service extends @Component) <=> creating <bean class="fr.pjthin.root.autowired.readbyspring.GenericService" />
@Component
public class GenericService {

    @Autowired
    private GenericDao dao;

    public List<String> getUsers() {
        return dao.getUsers();
    }
}
