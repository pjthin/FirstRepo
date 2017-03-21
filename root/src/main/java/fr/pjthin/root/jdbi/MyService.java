package fr.pjthin.root.jdbi;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;

public abstract class MyService implements GetHandle {

    @CreateSqlObject
    abstract CarDao carDao();

    @CreateSqlObject
    abstract DriverDao driverDao();

    public List<Car> getAllCars() {
        return carDao().getAll();
    }

    public void insertCar(Car car) {
        carDao().insert(car);
    }

    public List<Driver> getAllDrivers() {
        return driverDao().getAll();
    }

    public void insertDriver(Driver driver) {
        driverDao().insert(driver, driver.getCar());
    }

    @Transaction
    public void insertDriverAndCar(Driver driver) {
        carDao().insert(driver.getCar());
        driverDao().insert(driver, driver.getCar());
    }
    
    @Transaction
    public void failedInsertDriverAndCar(Driver driver) {
        carDao().insert(driver.getCar());
        driverDao().insertFailed(driver, driver.getCar());
    }

}
