package BusinessLayer;

import BusinessLayer.Transport_BusinessLayer.Drives.Driver;
import BusinessLayer.Transport_BusinessLayer.Drives.License;
import BusinessLayer.Workers_BusinessLayer.Responses.ResponseT;
import BusinessLayer.Workers_BusinessLayer.Responses.WorkerResponse;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;

import javax.lang.model.UnknownEntityException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DriversFactory {
    public void setWorkers_integration(Workers_Integration workers_integration) {
        this.workers_integration = workers_integration;
    }

    private Workers_Integration workers_integration;


    public List<Driver> getDrivers(Date date,License license){
        //todo parse date properly
        ResponseT<List<WorkerResponse>> responseT= workers_integration.getWorkersInShiftByJob(date.toString(), "Morning", LicenseToString(license));

        List<Driver> driverList=responseT.value.stream().map(res->
                new Driver(res.getName(), Integer.parseInt(res.getId()), getLicenseFromWorker(res.getOccupations()))
        ).collect(Collectors.toList());
        return driverList;

    }
    public List<Driver> getDrivers(Date date,List<License> license){
        List<Driver> output=new LinkedList<>();
        for (License l:license) {
            output=Stream.concat(output.stream(), getDrivers(date,l).stream()).collect(Collectors.toList());
        }
        return output;
    }
    public List<Driver> getAllDrivers(Date date){
        List<License> allLicenses=new LinkedList<>();
        allLicenses.add(License.typeA);allLicenses.add(License.typeB);allLicenses.add(License.typeB);
        return getDrivers(date, allLicenses) ;
    }


    private String LicenseToString(License license) {
        String output = "error";

        switch (license) {
            case typeA:
                output = "DriverA";
                break;
            case typeB:
                output = "DriverB";
                break;
            case typeC:
                output = "DriverC";
                break;
            case typeD:
                output = "DriverD";
                break;
        }

        return output;
    }
    private License JobToLicense(Job license)  {
        switch (license) {
            case DriverA:
                return License.typeA;

            case DriverB:
                return License.typeB;

            case DriverC:
                return License.typeC;


        }

        return null;
    }
    private License getLicenseFromWorker(List<Job> l)  {
        return JobToLicense(l.stream().filter(x->
                (x==Job.DriverA)||(x==Job.DriverB)||(x==Job.DriverC)
        ).findFirst().get());
    }
}
