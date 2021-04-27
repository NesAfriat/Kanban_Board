package BusinessLayer;

import BusinessLayer.Responses.ResponseT;
import BusinessLayer.Responses.WorkerResponse;

import java.util.List;

public interface Workers_Integration {
    public ResponseT<List<WorkerResponse>> getWorkersInShiftByJob(String date, String shiftType, String Job);
}
