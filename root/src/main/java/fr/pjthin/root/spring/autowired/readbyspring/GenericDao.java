package fr.pjthin.root.spring.autowired.readbyspring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

// Say to spring that it's dao <=> creating <bean class="fr.pjthin.root.autowired.readbyspring.GenericDao" />
@Repository
public class GenericDao {

    @Autowired
    @Qualifier(value = "jdbcTemplate")
    protected JdbcTemplate template;

    public GenericDao() {
    }

    public List<String> getUsers() {
        List<String> result = template.query("SELECT NOM FROM USER_TEST", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("NOM");
            }
        });

        return result;
    }

}
