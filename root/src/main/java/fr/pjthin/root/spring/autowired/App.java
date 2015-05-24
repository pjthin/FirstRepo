package fr.pjthin.root.spring.autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import fr.pjthin.root.spring.autowired.readbyspring.GenericService;

/**
 * Hello world!
 */
public class App {

    @Autowired
    @Qualifier(value = "jdbcTemplate")
    private JdbcTemplate template;

    @Autowired
    private GenericService service;

    public App() {
    }

    public void runSqlOnClass() {
        // direct use of jdbcTemplate
        List<String> result = template.query("SELECT NOM FROM USER_TEST", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("NOM");
            }
        });

        System.out.println("Using jdbcTemplate : " + StringUtils.join(result, ","));
    }

    public void runSqlOnOtherPackage() {
        // Use of service which use dao which use jdbcTemplate
        System.out.println("Using service : " + StringUtils.join(service.getUsers(), ","));
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println("This is my first code line. :-) ");

        // Loading context once
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/app.xml");

        // Trying get string application name
        System.out.println("Application Name : "
                + context.getAutowireCapableBeanFactory().getBean("appName", String.class));

        // Creating object
        App app = new App();

        // Forcing autowired on the object
        context.getAutowireCapableBeanFactory().autowireBean(app);

        // Autowired on class object (use of autowireBean)
        app.runSqlOnClass();

        // Autowired on package scanned (use of context:component-scan)
        app.runSqlOnOtherPackage();
    }

}
