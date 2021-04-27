package BusinessLayer;

import BusinessLayer.Workers_BusinessLayer.Responses.ResponseT;
import BusinessLayer.Workers_BusinessLayer.Responses.WorkerResponse;

import java.util.List;

public interface Workers_Integration {
    public ResponseT<List<WorkerResponse>> getAllWorkers(String date, String shiftType, String Job);
}
