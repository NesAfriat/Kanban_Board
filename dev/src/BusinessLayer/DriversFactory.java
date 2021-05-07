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
    public DriversFactory (Workers_Integration workers_integration) {
        this.workers_integration = workers_integration;
    }

    private Workers_Integration workers_integration;

//todo license properly
    public List<Driver> getDriversPerJob(String date,int shift,License license){
        //todo parse shift properly
        ResponseT<List<WorkerResponse>> responseT;
        if (shift ==1)
            responseT= workers_integration.getWorkersInShiftByJob(date, "Morning", LicenseToString(license));
        else
            responseT= workers_integration.getWorkersInShiftByJob(date, "Evening", LicenseToString(license));

        List<Driver> driverList=responseT.value.stream().map(res->
                new Driver(res.getName(), Integer.parseInt(res.getId()), getLicenseFromWorker(res.getOccupations()))
        ).collect(Collectors.toList());
        return driverList;

    }


    public List<Driver> getDrivers(String date,int shift,List<License> license){
        List<Driver> output=new LinkedList<>();
        for (License l:license) {
            output=Stream.concat(output.stream(), getDriversPerJob(date,shift,l).stream()).collect(Collectors.toList());
        }
        return output;
    }

    public List<License> returnLicenseList(License lcs){
        List<License> Licenses=new LinkedList<>();
        switch(lcs) {
            case typeA:
                Licenses.add(License.typeA);
                return Licenses;
            case typeB:
                Licenses.add(License.typeA);
                Licenses.add(License.typeB);
                return Licenses;
            case typeC:
                Licenses.add(License.typeA);
                Licenses.add(License.typeB);
                Licenses.add(License.typeC);
                return Licenses;
            case typeD:
                Licenses.add(License.typeA);
                Licenses.add(License.typeB);
                Licenses.add(License.typeC);
                Licenses.add(License.typeD);
                return Licenses;

        }
        return null;
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
