package fr.pjthin.root.jdbi;

public class Driver {

    private String name;
    private Car car;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Driver(String name, Car car) {
        this.name = name;
        this.car = car;
    }

    @Override
    public String toString() {
        return "Driver[" + name + ", " + car + "]";
    }
}
