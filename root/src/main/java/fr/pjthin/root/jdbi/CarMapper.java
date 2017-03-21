package fr.pjthin.root.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class CarMapper implements ResultSetMapper<Car> {

    @Override
    public Car map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Car(r.getString("name"), r.getInt("speed"));
    }

}
