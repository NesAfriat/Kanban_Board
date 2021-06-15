package BusinessLayer.Transport_BusinessLayer.Drives;

import java.util.LinkedList;
import java.util.List;

public class TruckType {
    private final String Name;
    private final int maxWeight;
    private final int freeWeight;
    private final List<License> licensesForTruck;

    public TruckType(String name, int maxWeight, int freeWeight, List<License> licensesForTruck) {
        Name = name;
        this.maxWeight = maxWeight;
        this.freeWeight = freeWeight;
        this.licensesForTruck = licensesForTruck;
    }

    public TruckType(String name, int maxWeight, int freeWeight) {
        Name = name;
        this.maxWeight = maxWeight;
        this.freeWeight = freeWeight;
        this.licensesForTruck = new LinkedList<>();
    }

    public void AddLicense(License lic) {
        licensesForTruck.add(lic);
    }

    public String getName() {
        return Name;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getFreeWeight() {
        return freeWeight;
    }

    public List<License> getLicensesForTruck() {
        return licensesForTruck;
    }

    public List<License> CopyOfLicenses() {//return copy list with final objects
        final List<License> finalCopy = new LinkedList<>();
        for (License el : licensesForTruck) {
            final License clel = el;
            finalCopy.add(clel);
        }
        return finalCopy;
    }
}
