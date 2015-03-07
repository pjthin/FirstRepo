package fr.pjthin.root.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/db/database.xml", "classpath:spring/db/dbConnectionTest.xml" })
public class DBTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void testDbConnexion() {
        // It works like this
        // ApplicationContext context;
        // context = new ClassPathXmlApplicationContext("spring/db/database.xml");
        // Assert.isTrue(context.containsBean("jdbcTemplate"));
        // jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);

        // or like that
        List<String> result = jdbcTemplate.query("SELECT NOM FROM USER_TEST", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("NOM");
            }
        });

        System.out.println(StringUtils.join(result, ","));

        Assert.assertTrue(CollectionUtils.isNotEmpty(result));

    }
}
