package fr.pjthin.root.jdbi;

import java.util.List;

import org.skife.jdbi.v2.exceptions.DBIException;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(DriverMapper.class)
public interface DriverDao {

    @SqlUpdate("insert into driver (driver_name, car_name) values (:d.name, :c.name)")
    void insert(@BindBean("d") Driver driver, @BindBean("c") Car car);

    @SqlQuery("select * from driver join car on driver.car_name = car.name")
    List<Driver> getAll();

    default void insertFailed(Driver driver, Car car) {
        throw new DBIException("An error occurred") {
            private static final long serialVersionUID = 1L;
        };
    }

}
