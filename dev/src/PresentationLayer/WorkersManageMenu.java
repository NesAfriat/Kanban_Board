package PresentationLayer;

import BusinessLayer.Responses.ConstraintResponse;
import BusinessLayer.Responses.ResponseT;
import BusinessLayer.Responses.WorkerResponse;

import java.util.List;

class WorkersManageMenu extends HRManagerMenu {

    void run() {
        boolean prev = false;
        while (!prev) {
            printPrettyHeadline("\n\nWorkers Manage Menu");
            System.out.println("1) Add worker");
            System.out.println("2) Fire worker");
            System.out.println("3) Get worker details");
            System.out.println("4) Add worker occupation");
            System.out.println("5) Remove worker occupation");
            System.out.println("6) View worker constraints");
            System.out.println("7) Previous");
            System.out.print("Option: ");
            int option = getInputInt();
            switch (option) {
                case 1:
                    AddWorker();
                    break;
                case 2:
                    FireWorker();
                    break;
                case 3:
                    GetWorker();
                    break;
                case 4:
                    AddWorkerOccupation();
                    break;
                case 5:
                    RemoveWorkerOccupation();
                    break;
                case 6:
                    ViewWorkerConstraints();
                    break;
                case 7:
                    prev = true;
                    break;
                default:
                    printPrettyError("No such option");
            }
        }
    }

    private void AddWorker() {
        String ID = getInputWorkerID();
        System.out.println("Name: ");
        String name = scanner.next();
        System.out.println("Bank Account: ");
        String bankAccount = scanner.next();
        System.out.println("Salary: ");
        double salary = getInputDouble();
        System.out.println("Education Fund: ");
        String educationFund = scanner.next();
        System.out.println("Vacation Days Per Month: ");
        int vacationDaysPerMonth = getInputInt();
        System.out.println("Sick Days Per Month: ");
        int sickDaysPerMonth = getInputInt();
        System.out.println("Enter Start Working Date <DD/MM/YYYY>: ");
        String date = scanner.next();
        ResponseT<WorkerResponse> workerResponse = facade.addWorker(false, name, ID, bankAccount, salary, educationFund, vacationDaysPerMonth, sickDaysPerMonth, date);
        if (workerResponse.ErrorOccurred()){
            printPrettyError(workerResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("Worker added successfully to the system, details: " + workerResponse.value.getNameID());
        }
    }

    private void ViewWorkerConstraints() {
        String ID = getInputWorkerID();
        ResponseT<WorkerResponse> worker = facade.getWorker(ID);
        if (worker.ErrorOccurred()){
            printPrettyError(worker.getErrorMessage());
        }
        else {
            printPrettyConfirm(worker.value.getNameID() +" Constraints: \n");
            List<ConstraintResponse> constraintResponseList = worker.value.getConstraints();
            for (ConstraintResponse constraint: constraintResponseList){
                printPrettyConfirm(constraint.toString());
            }
        }
    }

    private void RemoveWorkerOccupation() {
        String ID = getInputWorkerID();
        String job = getInputJob();
        ResponseT<WorkerResponse> workerResponse = facade.removeOccupationToWorker(ID, job);
        if (workerResponse.ErrorOccurred())
            printPrettyError(workerResponse.getErrorMessage());
        else{
            printPrettyConfirm("Removed role " + "("+job+")" + " from " + workerResponse.value.getNameID() + " successfully");
        }
    }

    private void AddWorkerOccupation() {
        String ID = getInputWorkerID();
        String job = getInputJob();
        ResponseT<WorkerResponse> workerResponse = facade.addOccupationToWorker(ID, job);
        if (workerResponse.ErrorOccurred())
            printPrettyError(workerResponse.getErrorMessage());
        else{
            printPrettyConfirm("Added new role " + "("+job+")" + " for " + workerResponse.value.getNameID() + " successfully");
        }
    }

    private void GetWorker() {
        String ID = getInputWorkerID();
        ResponseT<WorkerResponse> workerResponse = facade.getWorker(ID);
        if (workerResponse.ErrorOccurred()) {
            printPrettyError(workerResponse.getErrorMessage());
        } else {
            printPrettyConfirm(workerResponse.value.toString());
        }
    }

    private void FireWorker(){
        String ID = getInputWorkerID();
        System.out.print("Enter end working date <DD/MM/YYYY>: ");
        String date = scanner.next();
        ResponseT<WorkerResponse> workerResponse = facade.fireWorker(ID, date);
        if (workerResponse.ErrorOccurred()){
            printPrettyError(workerResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("Worker fired successfully, details: " + workerResponse.value.getNameID());
        }
    }
}
