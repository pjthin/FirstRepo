package fr.pjthin.root.jdbi;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private MyService myService;

    private DBI dbi;

    public Main(DBI dbi, MyService myService) {
        this.dbi = dbi;
        this.myService = myService;
    }

    public void start() {
        // initialisation des tables
        dbi.useHandle(h -> {
            h.execute("create table car (speed int, name varchar(100))");
            h.execute("create table driver (driver_name varchar(100), car_name varchar(100))");
        });
        Car clio = new Car("Clio", 50);
        Car megane = new Car("Megane", 100);
        myService.insertCar(clio);
        myService.insertCar(megane);
        logCars();
        myService.insertDriver(new Driver("John Doe", clio));
        logDrivers();
        Car ferrarie = new Car("Ferrarie", 300);
        myService.insertDriverAndCar(new Driver("James Bond", ferrarie));
        logDrivers();
        Car badCar = new Car("badCar", -10);
        try {
            myService.failedInsertDriverAndCar(new Driver("Bad driver", badCar));
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
        }
        logCars();
        logDrivers();

    }

    private void logDrivers() {
        LOGGER.info("List of driver : {}", myService.getAllDrivers().size());
        myService.getAllDrivers().stream().map(car -> car.toString()).forEach(LOGGER::info);
    }

    private void logCars() {
        LOGGER.info("List of car : {}", myService.getAllCars().size());
        myService.getAllCars().stream().map(car -> car.toString()).forEach(LOGGER::info);
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
        Main main = context.getBean(Main.class);
        main.start();
    }

}
