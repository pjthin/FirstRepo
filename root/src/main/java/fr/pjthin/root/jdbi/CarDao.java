package fr.pjthin.root.jdbi;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;


@RegisterMapper(CarMapper.class)
public interface CarDao {
    
    @SqlUpdate("insert into car (name, speed) values (:c.name, :c.speed)")
    void insert(@BindBean("c") Car car);
    
    @SqlQuery("select * from car")
    List<Car> getAll();

}
