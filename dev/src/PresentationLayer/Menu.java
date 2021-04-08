package PresentationLayer;
import BusinessLayer.Facade;
import BusinessLayer.Responses.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


public class Menu {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";


    private static final Scanner scanner = new Scanner(System.in);;
    private static final Facade facade = new Facade();

    private static void testingDataUpload(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //LocalDate localDate = LocalDate.parse(date, formatter);

        facade.login("000000000");
        facade.setDefaultJobsInShift(1 ,"Morning", "Cashier", 2);
        facade.setDefaultJobsInShift(6 ,"Morning", "Cashier", 3);
        facade.setDefaultJobsInShift(1 ,"Morning", "Usher", 3);
        facade.setDefaultJobsInShift(1 ,"Evening", "Usher", 0);
        facade.setDefaultJobsInShift(6 ,"Morning", "Usher", 0);
        facade.addWorker(false, "dan", "000000001", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "avi", "000000002", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "kobi", "000000003", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "moshe", "000000004", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "eli", "000000005", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "moti", "000000006", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "shaol", "000000007", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "ronen", "000000008", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "ronen", "000000009", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "moshe", "000000010", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "dolev", "000000011", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "eliad", "000000012", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "micha", "000000013", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "meni", "000000014", "1", 1, "1", 1, 1 , "01/01/2018");

        facade.addOccupationToWorker("000000001", "HR_Manager");
        facade.addOccupationToWorker("000000002", "Shift_Manager");
        facade.addOccupationToWorker("000000003", "Shift_Manager");
        facade.addOccupationToWorker("000000004", "Shift_Manager");
        facade.addOccupationToWorker("000000005", "Shift_Manager");
        facade.addOccupationToWorker("000000006", "Cashier");
        facade.addOccupationToWorker("000000007", "Cashier");
        facade.addOccupationToWorker("000000008", "Cashier");
        facade.addOccupationToWorker("000000009", "Cashier");
        facade.addOccupationToWorker("000000010", "Cashier");
        facade.addOccupationToWorker("000000014", "Guard");
        facade.addOccupationToWorker("000000013", "Guard");
        facade.addOccupationToWorker("000000012", "Guard");
        facade.addOccupationToWorker("000000008", "Storekeeper");
        facade.addOccupationToWorker("000000009", "Storekeeper");
        facade.addOccupationToWorker("000000010", "Storekeeper");
        facade.addOccupationToWorker("000000011", "Storekeeper");
        facade.addOccupationToWorker("000000009", "Usher");
        facade.addOccupationToWorker("000000010", "Usher");
        facade.addOccupationToWorker("000000011", "Usher");






        facade.addDefaultWorkDay( LocalDate.now().format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(1).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(2).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(3).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(4).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(5).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(6).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(7).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(8).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(9).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(10).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(11).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(12).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(13).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(14).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(15).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(16).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(17).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(18).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(19).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(20).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(21).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(22).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(23).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(24).format(formatter));
        facade.addDefaultWorkDay( LocalDate.now().plusDays(25).format(formatter));
        facade.logout();
        facade.login("222222222");
        facade.addConstraint("01/05/2021","Morning", "Cant");
        facade.logout();
    }

    public static void main(String[] args){
        if (args != null) {
            testingDataUpload();
        }

        System.out.println("Enter ID number for login: ");
        String ID = scanner.next();
        ResponseT<WorkerResponse> worker = facade.login(ID);
        if (worker.ErrorOccurred()){
            System.out.println(ANSI_RED + worker.getErrorMessage() + ANSI_RESET);
            main(null);
        }
        printPrettyConfirm("Hello, "+ worker.value.getName()+ "!");
        if (worker.value.getIsAdmin()){
            AdminMenu();
        }
        else {
            WorkerMenu();
        }
    }

    private static void WorkerMenu() {
        System.out.println("1) View shift arrangement");
        System.out.println("2) View constraints");
        System.out.println("3) Add constraint");
        System.out.println("4) Remove constraint");
        System.out.println("5) Logout");
        System.out.println("6) Exit");
        System.out.print("Option: ");
        int option = getInputInt();
        switch (option){
            case 1:
                nonAdminViewShiftArrangement();
                WorkerMenu();
                break;
            case 2:
                viewLoggedWorkerConstraints();
                WorkerMenu();
                break;
            case 3:
                addConstraint();
                WorkerMenu();
                break;
            case 4:
                removeConstraint();
                WorkerMenu();
                break;
            case 5:
                LogOut();
                main(null);
                break;
            case 6:
                LogOut();
                System.exit(0);
            default:
                System.out.println("No such option");
                WorkerMenu();
        }
    }



    private static void LogOut() {
        Response logout = facade.logout();
        if (logout.ErrorOccurred())
            printPrettyError(logout.getErrorMessage());
        else {
            printPrettyConfirm("Logout succeed");
        }
    }


    private static void adminViewShiftArrangement() {
        String date = getInputDate();
        ResponseT<WorkDayResponse> workDay = facade.viewShiftArrangement(date);
        if (workDay.ErrorOccurred()){
            printPrettyError(workDay.getErrorMessage());
        }
        else {
            printPrettyConfirm(workDay.value.toString());
        }
    }

    private static void nonAdminViewShiftArrangement() {
        String date = getInputDate();
        ResponseT<WorkDayResponse> workDay = facade.viewShiftArrangement(date);
        if (workDay.ErrorOccurred()){
            printPrettyError(workDay.getErrorMessage());
        }
        else {
            printPrettyConfirm(workDay.value.approvedToString());
        }
    }


    private static void removeConstraint() {
        String date = getInputDate();
        String shiftType = getInputShiftType();
        ResponseT<ConstraintResponse> constraint = facade.removeConstraint(date, shiftType);
        if (constraint.ErrorOccurred()){
            printPrettyError(constraint.getErrorMessage());
        }
        else {
            printPrettyConfirm("Constraint removed successfully, details: ");
            printPrettyConfirm(constraint.value.toString());
        }
    }


    private static void addConstraint() {
        String date = getInputDate();
        String shiftType = getInputShiftType();
        String constraintType = getInputConstraintType();
        ResponseT<ConstraintResponse> constraint = facade.addConstraint(date, shiftType, constraintType);
        if (constraint.ErrorOccurred()){
            printPrettyError(constraint.getErrorMessage());
        }
        else {
            printPrettyConfirm("Constraint added successfully, details: ");
            printPrettyConfirm(constraint.value.toString());
        }
    }




    private static void viewLoggedWorkerConstraints() {
        ResponseT<WorkerResponse> worker = facade.getLoggedWorker();
        if (worker.ErrorOccurred()){
            printPrettyError(worker.getErrorMessage());
        }
        else {
            List<ConstraintResponse> constraintResponseList = worker.value.getConstraints();
            for (ConstraintResponse constraint: constraintResponseList){
                printPrettyConfirm(constraint.toString());
            }
        }
    }



    private static void AdminMenu() {
        System.out.println("1) Manage Workers");
        System.out.println("2) Manage Shifts");
        System.out.println("3) Logout");
        System.out.println("4) Exit");
        System.out.print("Option: ");
        int option = getInputInt();
        switch (option){
            case 1:
                WorkersManageMenu();
                break;
            case 2:
                ShiftsManageMenu();
                break;
            case 3:
                LogOut();
                main(null);
                break;
            case 4:
                LogOut();
                System.exit(0);
            default:
                System.out.println("No such option");
                AdminMenu();
        }
    }



    private static void ShiftsManageMenu() {
        System.out.println("1) View shift arrangement");
        System.out.println("2) Edit shift");
        System.out.println("3) Add new shifts");
        System.out.println("4) Remove shift");
        System.out.println("5) Edit default shift/workday");
        System.out.println("6) Previous");
        System.out.println("7) Exit");
        System.out.print("Option: ");
        int option = getInputInt();
        switch (option){
            case 1:
                adminViewShiftArrangement();
                ShiftsManageMenu();
                break;
            case 2:
                EditShift();
                break;
            case 3:
                AddShiftsMenu();
                break;
            case 4:
                RemoveShift();
                ShiftsManageMenu();
                break;
            case 5:
                EditDefaultWorkDayShiftMenu();
            case 6:
                AdminMenu();
            case 7:
                System.exit(0);
            default:
                System.out.println("No such option");
                ShiftsManageMenu();
        }
    }

    private static void EditDefaultWorkDayShiftMenu() {
        System.out.println("1) Get shift default settings");
        System.out.println("2) Edit shift default");
        System.out.println("3) Get workday default settings");
        System.out.println("4) Edit workday default");
        System.out.println("5) Previous");
        System.out.println("6) Exit");
        System.out.print("Option: ");
        int option = getInputInt();
        switch (option) {
            case 1:
                getDefaultShift();
                EditDefaultWorkDayShiftMenu();
            case 2:
                setDefaultShift();
                EditDefaultWorkDayShiftMenu();
                break;
            case 3:
                getDefaultWorkDay();
                EditDefaultWorkDayShiftMenu();
            case 4:
                setDefaultWorkDay();
                EditDefaultWorkDayShiftMenu();
                break;
            case 5:
                ShiftsManageMenu();
                break;
            case 6:
                LogOut();
                System.exit(0);
            default:
                System.out.println("No such option");
                EditDefaultWorkDayShiftMenu();
        }
    }

    private static void getDefaultWorkDay() {
        int day = getInputDay();
        ResponseT<WorkDayResponse> workDayResponse = facade.getDefaultShiftInDay(day);
        if (workDayResponse.ErrorOccurred()){
            printPrettyError(workDayResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm(workDayResponse.value.Settings());
        }
    }

    private static void setDefaultWorkDay() {
        int day = getInputDay();
        System.out.println("Do you want morning shift?");
        boolean hasMorning = getInputYesNo();
        System.out.println("Do you want morning shift? ");
        boolean hasEvening = getInputYesNo();
        Response m_response = facade.setDefaultShiftInDay(day, "Morning", hasMorning);
        Response e_response = facade.setDefaultShiftInDay(day, "Evening", hasEvening);
        if (m_response.ErrorOccurred()){
            printPrettyError(m_response.getErrorMessage());
        }
        if (e_response.ErrorOccurred())
            printPrettyError(e_response.getErrorMessage());
        if (!m_response.ErrorOccurred() & !e_response.ErrorOccurred()){
            printPrettyConfirm("New workday setting updated successfully");
        }
    }




    private static void getDefaultShift(){
        int day = getInputDayType();
        String shiftType = getInputShiftType();
        ResponseT<ShiftResponse> response = facade.getDefaultJobsInShift(day, shiftType);
        if (response.ErrorOccurred()){
            printPrettyError(response.getErrorMessage());
        }
        else {
            printPrettyConfirm(response.value.Settings());
        }
    }

    private static void setDefaultShift() {
        int day = getInputDayType();
        String shiftType = getInputShiftType();
        String role = getInputJob();
        System.out.println("Enter new amount required: ");
        int amountRequired = scanner.nextInt();
        Response response = facade.setDefaultJobsInShift(day, shiftType, role, amountRequired);
        if (response.ErrorOccurred()){
            printPrettyError(response.getErrorMessage());
        }
        else {
            printPrettyConfirm("New shift default updated successfully");
        }
    }



    private static void AddShiftsMenu() {
        System.out.println("1) Add default shift");
        System.out.println("2) Add default workday");
        System.out.println("3) View default shift settings");
        System.out.println("4) View default workday settings");
        System.out.println("5) Previous");
        System.out.println("6) Exit");
        System.out.print("Option: ");
        int option = getInputInt();
        switch (option) {
            case 1:
                addDefaultShift();
                AddShiftsMenu();
                break;
            case 2:
                addDefaultWorkDay();
                AddShiftsMenu();
                break;
            case 3:
                getDefaultShift();
                AddShiftsMenu();
            case 4:
                getDefaultWorkDay();
                AddShiftsMenu();
            case 5:
                ShiftsManageMenu();
                break;
            case 6:
                LogOut();
                System.exit(0);
            default:
                System.out.println("No such option");
                AddShiftsMenu();
        }
    }

    private static void addDefaultShift() {
        String date = getInputDate();
        String shiftType = getInputShiftType();
        ResponseT<WorkDayResponse> workDayResponse = facade.addDefaultShift(date, shiftType);
        if (workDayResponse.ErrorOccurred()){
            printPrettyError(workDayResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm(shiftType + " shift added successfully to workday at " + workDayResponse.value.getDate());
        }
    }


    private static void addDefaultWorkDay() {
        String date = getInputDate();
        ResponseT<WorkDayResponse> workDayResponse = facade.addDefaultWorkDay(date);
        if (workDayResponse.ErrorOccurred()){
            printPrettyError(workDayResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("A workday added successfully at " + workDayResponse.value.getDate());
        }
    }

    private static void RemoveShift(){
        String date = getInputDate();
        String shiftType = getInputShiftType();
        ResponseT<ShiftResponse> shiftResponse = facade.removeShift(date, shiftType);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm(shiftType + " Shift removed successfully at date: " + date);
        }
    }

    private static void EditShiftMenu() {
        System.out.println("1) View current arrangement");
        System.out.println("2) Add worker to shift");
        System.out.println("3) Remove worker from shift");
        System.out.println("4) Add required job");
        System.out.println("5) Edit job required amount");
        System.out.println("6) Get available workers");
        System.out.println("7) Approve shift");
        System.out.println("8) Previous");
        System.out.print("Option: ");
        int option = getInputInt();
        switch (option){
            case 1:
                viewCurrentArrangement();
                EditShiftMenu();
                break;
            case 2:
                assignWorker();
                EditShiftMenu();
                break;
            case 3:
                removeWorker();
                EditShiftMenu();
                break;
            case 4:
                AddRequiredJob();
                EditShiftMenu();
                break;
            case 5:
                SetRequiredAmount();
                EditShiftMenu();
            case 6:
                GetAvailableWorkers();
                EditShiftMenu();
            case 7:
                ApproveShift();
                EditShiftMenu();
            case 8:
                ExitEditShiftMenu();
                ShiftsManageMenu();
                break;
            default:
                System.out.println("No such option");
                EditShiftMenu();
        }
    }

    private static void ApproveShift() {
        ResponseT<ShiftResponse> shiftResponse = facade.approveShift();
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else{
            if (shiftResponse.value.isFullyOccupied())
                printPrettyConfirm("Shift approved successfully");
            else{
                printPrettyConfirm("Shift approved successfully.");
                printPrettyError("BEWARE: Not all roles are fully occupied.");
            }
        }

    }

    private static void GetAvailableWorkers() {
        String role = getInputJob();
        ResponseT<List<WorkerResponse>> availableWorkers = facade.getAvailableWorkers(role);
        if (availableWorkers.ErrorOccurred()){
            printPrettyError(availableWorkers.getErrorMessage());
        }
        else {
            if (availableWorkers.value.isEmpty()){
                printPrettyConfirm("No available workers to work as " + role +" at current shift.");
            }
            else {
                for (WorkerResponse workerRes : availableWorkers.value) {
                    printPrettyConfirm(workerRes.getNameID());
                }
            }
        }
    }

    private static void SetRequiredAmount() {
        String role = getInputJob();
        System.out.print("Amount required: ");
        int required = scanner.nextInt();
        ResponseT<ShiftResponse> shiftResponse = facade.setAmountRequired(role, required);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("The amount of workers required for role " + role + " has updated successfully to "+ required);
        }
    }

    private static void ExitEditShiftMenu() {
        Response response = facade.exitShift();
        if (response.ErrorOccurred())
            printPrettyError(response.getErrorMessage());
        else {
            printPrettyConfirm("Exited from shift successfully");
        }
    }

    private static void AddRequiredJob() {
        String role = getInputJob();
        System.out.print("Amount required: ");
        int required = scanner.nextInt();
        ResponseT<ShiftResponse> shiftResponse = facade.addRequiredJob(role, required);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("Job "+ role + " added successfully to the shift.");
        }
    }

    private static void removeWorker() {
        String ID = getInputWorkerID();
        String role = getInputJob();
        ResponseT<ShiftResponse> shiftResponse = facade.removeWorkerFromCurrentShift(ID, role);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("Worker removed successfully from the shift.");
        }
    }

    private static void viewCurrentArrangement() {
        ResponseT<ShiftResponse> shiftResponse = facade.viewCurrentArrangement();
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm(shiftResponse.value.toString());
        }
    }

    private static void assignWorker() {
        String ID = getInputWorkerID();
        String role = getInputJob();
        ResponseT<ShiftResponse> shiftResponse = facade.addWorkerToCurrentShift(ID, role);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("Worker added successfully");
        }
    }


    private static void EditShift() {
        String date = getInputDate();
        String shiftType = getInputShiftType();

        ResponseT<ShiftResponse> shiftResponse = facade.chooseShift(date, shiftType);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
            ShiftsManageMenu();
        }
        else {
            printPrettyConfirm("Shift selected successfully");
            EditShiftMenu();
        }
    }



    private static void WorkersManageMenu() {
        System.out.println("1) Add worker");
        System.out.println("2) Fire worker");
        System.out.println("3) Get worker details");
        System.out.println("4) Add worker occupation");
        System.out.println("5) Remove worker occupation");
        System.out.println("6) View worker constraints");
        System.out.println("7) Previous");
        System.out.print("Option: ");
        int option = getInputInt();
        switch (option){
            case 1:
                AddWorker();
                WorkersManageMenu();
                break;
            case 2:
                FireWorker();
                WorkersManageMenu();
                break;
            case 3:
                GetWorker();
                WorkersManageMenu();
                break;
            case 4:
                AddWorkerOccupation();
                WorkersManageMenu();
            case 5:
                RemoveWorkerOccupation();
                WorkersManageMenu();
            case 6:
                ViewWorkerConstraints();
                WorkersManageMenu();
            case 7:
                AdminMenu();

            default:
                System.out.println("No such option");
                WorkersManageMenu();
        }

    }

    private static void ViewWorkerConstraints() {
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

    private static void RemoveWorkerOccupation() {
        String ID = getInputWorkerID();
        String job = getInputJob();
        ResponseT<WorkerResponse> workerResponse = facade.removeOccupationToWorker(ID, job);
        if (workerResponse.ErrorOccurred())
            printPrettyError(workerResponse.getErrorMessage());
        else{
            printPrettyConfirm("Removed role " + "("+job+")" + " from " + workerResponse.value.getNameID() + " successfully");
        }
    }

    private static void AddWorkerOccupation() {
        String ID = getInputWorkerID();
        String job = getInputJob();
        ResponseT<WorkerResponse> workerResponse = facade.addOccupationToWorker(ID, job);
        if (workerResponse.ErrorOccurred())
            printPrettyError(workerResponse.getErrorMessage());
        else{
            printPrettyConfirm("Added new role " + "("+job+")" + " for " + workerResponse.value.getNameID() + " successfully");
        }
    }

    private static void GetWorker() {
        String ID = getInputWorkerID();
        ResponseT<WorkerResponse> workerResponse = facade.getWorker(ID);
        if (workerResponse.ErrorOccurred()) {
            printPrettyError(workerResponse.getErrorMessage());
        } else {
            printPrettyConfirm(workerResponse.value.toString());
        }
    }

    private static void FireWorker(){
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


    private static void AddWorker() {
        String ID = getInputWorkerID();
        System.out.println("Name:");
        String name = scanner.next();
        System.out.println("Bank Account:");
        String bankAccount = scanner.next();
        System.out.println("Salary: ");
        double salary = getInputDouble();
        System.out.println("Education Fund:");
        String educationFund = scanner.next();
        System.out.println("Vacation Days Per Month:");
        int vacationDaysPerMonth = getInputInt();
        System.out.println("Sick Days Per Month:");
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



    private static void printPrettyConfirm(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    private static void printPrettyError(String errorMessage) {
        System.out.println(ANSI_RED + errorMessage + ANSI_RESET);
    }


    private static String getInputDate(){
        System.out.println("Enter Date <DD/MM/YYYY>: ");
        return scanner.next();
    }

    private static String getInputShiftType(){
        System.out.println("Choose shift:");
        System.out.println("1) Morning");
        System.out.println("2) Evening");
        int option = getInputInt();
        if (option == 1)
            return "Morning";
        else if (option == 2)
            return "Evening";
        else {
            printPrettyError("There's no such option.");
            return getInputShiftType();
        }
    }

    private static String getInputJob(){
        System.out.print("Enter job name: ");
        return scanner.next();
    }


    private static String getInputWorkerID(){
        System.out.print("Enter worker id: ");
        return scanner.next();
    }


    private static int getInputDayType() {
        System.out.println("Choose day type:");
        System.out.println("1) Weekday");
        System.out.println("2) Friday");
        System.out.println("3) Saturday");
        int day = getInputInt();
        if (day == 2) day = 6;
        else if (day == 3) day = 7;
        else if (day == 1) day = 1;
        else {
            printPrettyError("There's no such option");
            return getInputDayType();
        }
        return day;
    }
    private static int getInputDay() {
        System.out.println("Choose day:");
        System.out.println("1) Sunday");
        System.out.println("2) Monday");
        System.out.println("3) Tuesday");
        System.out.println("4) Wednesday");
        System.out.println("5) Thursday");
        System.out.println("6) Friday");
        System.out.println("7) Saturday");
        int day = getInputInt();
        if (day < 1 | day > 7){
            printPrettyError("There's no such option");
            return getInputDay();
        }
        return day;
    }

    private static boolean getInputYesNo() {
        System.out.println("1) Yes");
        System.out.println("2) No");
        System.out.print("Option: ");
        int option = getInputInt();
        if (option == 1)
            return true;
        else if (option == 2)
            return false;
        else{
            printPrettyError("There's no such option");
            return getInputYesNo();
        }
    }

    private static int getInputInt() {
        while (!scanner.hasNextInt()){
            printPrettyError("Please enter a number");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double getInputDouble() {
        while (!scanner.hasNextDouble()){
            printPrettyError("Please enter a number");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    private static String getInputConstraintType() {
        System.out.println("Choose constraint type: ");
        System.out.println("1) Can't");
        System.out.println("2) Want");
        int option = getInputInt();
        if (option == 1)
            return "Cant";
        else if (option == 2)
            return "Want";
        else {
            printPrettyError("There's no such option");
            return getInputConstraintType();
        }
    }

}
