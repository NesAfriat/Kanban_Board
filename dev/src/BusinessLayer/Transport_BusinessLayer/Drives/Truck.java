package BusinessLayer.Transport_BusinessLayer.Drives;

public class Truck {
    private final String name;
    private final int licensePlate;
    private final TruckType truckType;

    public Truck(String name, int licensePlate, TruckType truckType) {
        this.name = name;
        this.licensePlate = licensePlate;
        this.truckType = truckType;
    }

    public String toString() {
        return "Truck licensePlate: " + licensePlate + ", " + name + ", " + truckType.getName() + "\n ";
    }

    public String getName() {
        return name;
    }

    public int getLicensePlate() {
        return licensePlate;
    }

    public TruckType getTruckType() {
        return truckType;
    }
}
