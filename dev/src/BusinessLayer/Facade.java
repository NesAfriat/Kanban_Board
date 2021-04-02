package BusinessLayer;

import BusinessLayer.Controllers.ShiftController;
import BusinessLayer.Controllers.WorkerController;
import BusinessLayer.Responses.ResponseT;
import BusinessLayer.Responses.WorkerResponse;
import BusinessLayer.Workers.Worker;

public class Facade {
    private WorkerController workerController;
    private ShiftController shiftController;
    public ResponseT<WorkerResponse> login(String id){
        try {
            Worker loggedid = workerController.login(id);
            shiftController.login(loggedid.getIsAdmin());
            return new ResponseT<>(new WorkerResponse(loggedid));
        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }
}
