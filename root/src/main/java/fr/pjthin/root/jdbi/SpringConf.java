package fr.pjthin.root.jdbi;

import javax.sql.DataSource;

import org.skife.jdbi.v2.DBI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class SpringConf {

    @Bean
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    DBI dbi(DataSource dataSource) {
        return new DBI(dataSource);
    }

    @Bean
    MyService myService(DBI dbi) {
        return dbi.onDemand(MyService.class);
    }

    @Bean
    Main main(MyService myService, DBI dbi) {
        return new Main(dbi, myService);
    }

}
