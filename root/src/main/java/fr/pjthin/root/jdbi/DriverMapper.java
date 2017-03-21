package fr.pjthin.root.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class DriverMapper implements ResultSetMapper<Driver> {

    private CarMapper carMapper = new CarMapper();

    @Override
    public Driver map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Driver(r.getString("driver_name"), carMapper.map(index, r, ctx));
    }

}
