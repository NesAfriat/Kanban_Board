package PresentationLayer;
import BusinessLayer.Facade;
import BusinessLayer.Responses.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Menu {
    private boolean firstRun = true;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    protected static final Scanner scanner = new Scanner(System.in);
    protected static final Facade facade = new Facade();

    private void testingDataUpload(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
        facade.addOccupationToWorker("000000012", "Usher");


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

        facade.chooseShift(LocalDate.now().format(formatter), "Morning");
        facade.addWorkerToCurrentShift("000000002", "Shift_Manager");
        facade.addWorkerToCurrentShift("000000006", "Cashier");
        facade.addWorkerToCurrentShift("000000007", "Cashier");
        facade.addWorkerToCurrentShift("000000014", "Guard");
        facade.addWorkerToCurrentShift("000000010", "Storekeeper");
        facade.addWorkerToCurrentShift("0000000011", "Usher");

        facade.chooseShift(LocalDate.now().format(formatter), "Evening");
        facade.addWorkerToCurrentShift("000000003", "Shift_Manager");
        facade.addWorkerToCurrentShift("000000008", "Cashier");
        facade.addWorkerToCurrentShift("000000009", "Cashier");
        facade.addWorkerToCurrentShift("000000013", "Guard");
        facade.addWorkerToCurrentShift("000000010", "Storekeeper");
        facade.addWorkerToCurrentShift("0000000012", "Usher");

        facade.chooseShift(LocalDate.now().plusDays(1).format(formatter), "Morning");
        facade.addWorkerToCurrentShift("000000004", "Shift_Manager");
        facade.addWorkerToCurrentShift("000000006", "Cashier");
        facade.addWorkerToCurrentShift("000000007", "Cashier");
        facade.addWorkerToCurrentShift("000000014", "Guard");
        facade.addWorkerToCurrentShift("000000010", "Storekeeper");
        facade.addWorkerToCurrentShift("0000000011", "Usher");

        facade.chooseShift(LocalDate.now().plusDays(1).format(formatter), "Evening");
        facade.addWorkerToCurrentShift("000000005", "Shift_Manager");
        facade.addWorkerToCurrentShift("000000008", "Cashier");
        facade.addWorkerToCurrentShift("000000009", "Cashier");
        facade.addWorkerToCurrentShift("000000013", "Guard");
        facade.addWorkerToCurrentShift("000000010", "Storekeeper");
        facade.addWorkerToCurrentShift("0000000012", "Usher");

        facade.chooseShift(LocalDate.now().plusDays(2).format(formatter), "Morning");
        facade.addWorkerToCurrentShift("000000002", "Shift_Manager");
        facade.addWorkerToCurrentShift("000000006", "Cashier");
        facade.addWorkerToCurrentShift("000000007", "Cashier");
        facade.addWorkerToCurrentShift("000000014", "Guard");
        facade.addWorkerToCurrentShift("000000010", "Storekeeper");
        facade.addWorkerToCurrentShift("0000000011", "Usher");

        facade.chooseShift(LocalDate.now().plusDays(2).format(formatter), "Evening");
        facade.addWorkerToCurrentShift("000000003", "Shift_Manager");
        facade.addWorkerToCurrentShift("000000008", "Cashier");
        facade.addWorkerToCurrentShift("000000009", "Cashier");
        facade.addWorkerToCurrentShift("000000013", "Guard");
        facade.addWorkerToCurrentShift("000000010", "Storekeeper");
        facade.addWorkerToCurrentShift("0000000012", "Usher");

        facade.chooseShift(LocalDate.now().plusDays(3).format(formatter), "Morning");
        facade.addWorkerToCurrentShift("000000004", "Shift_Manager");
        facade.addWorkerToCurrentShift("000000006", "Cashier");
        facade.addWorkerToCurrentShift("000000007", "Cashier");
        facade.addWorkerToCurrentShift("000000014", "Guard");
        facade.addWorkerToCurrentShift("000000010", "Storekeeper");
        facade.addWorkerToCurrentShift("0000000011", "Usher");

        facade.chooseShift(LocalDate.now().plusDays(3).format(formatter), "Evening");
        facade.addWorkerToCurrentShift("000000005", "Shift_Manager");
        facade.addWorkerToCurrentShift("000000008", "Cashier");
        facade.addWorkerToCurrentShift("000000009", "Cashier");
        facade.addWorkerToCurrentShift("000000013", "Guard");
        facade.addWorkerToCurrentShift("000000010", "Storekeeper");
        facade.addWorkerToCurrentShift("0000000012", "Usher");

        facade.chooseShift(LocalDate.now().plusDays(4).format(formatter), "Morning");
        facade.addWorkerToCurrentShift("000000004", "Shift_Manager");
        facade.addWorkerToCurrentShift("000000006", "Cashier");
        facade.addWorkerToCurrentShift("000000007", "Cashier");
        facade.addWorkerToCurrentShift("000000014", "Guard");
        facade.addWorkerToCurrentShift("000000010", "Storekeeper");
        facade.addWorkerToCurrentShift("0000000011", "Usher");

        facade.chooseShift(LocalDate.now().plusDays(4).format(formatter), "Evening");
        facade.addWorkerToCurrentShift("000000005", "Shift_Manager");
        facade.addWorkerToCurrentShift("000000008", "Cashier");
        facade.addWorkerToCurrentShift("000000009", "Cashier");
        facade.addWorkerToCurrentShift("000000013", "Guard");
        facade.addWorkerToCurrentShift("000000010", "Storekeeper");
        facade.addWorkerToCurrentShift("0000000012", "Usher");


        facade.chooseShift(LocalDate.now().plusDays(5).format(formatter), "Morning");
        facade.addWorkerToCurrentShift("000000004", "Shift_Manager");
        facade.addWorkerToCurrentShift("000000006", "Cashier");
        facade.addWorkerToCurrentShift("000000007", "Cashier");
        facade.addWorkerToCurrentShift("000000014", "Guard");
        facade.addWorkerToCurrentShift("000000010", "Storekeeper");
        facade.addWorkerToCurrentShift("0000000012", "Usher");

        facade.chooseShift(LocalDate.now().plusDays(5).format(formatter), "Evening");
        facade.addWorkerToCurrentShift("000000005", "Shift_Manager");
        facade.addWorkerToCurrentShift("000000008", "Cashier");
        facade.addWorkerToCurrentShift("000000009", "Cashier");
        facade.addWorkerToCurrentShift("000000013", "Guard");
        facade.addWorkerToCurrentShift("000000010", "Storekeeper");
        facade.addWorkerToCurrentShift("0000000012", "Usher");


        facade.logout();
        facade.login("000000002");
        facade.addConstraint(LocalDate.now().plusDays(17).format(formatter),"Morning", "Cant");
        facade.addConstraint(LocalDate.now().plusDays(17).format(formatter),"Evening", "Cant");
        facade.logout();
    }

    public void start() {
        if (firstRun) {
            printPrettyHeadline("Welcome to Super-Lee system!");
            System.out.println("Do you want to load database?");
            boolean load = getInputYesNo();
            if (load) {
                testingDataUpload();
            }
            firstRun = false;
        }

        System.out.println("Enter ID number for login: ");
        String ID = scanner.next();
        ResponseT<WorkerResponse> worker = facade.login(ID);
        if (worker.ErrorOccurred()){
            System.out.println(ANSI_RED + worker.getErrorMessage() + ANSI_RESET);
            start();
        }

        printPrettyConfirm("Hello, "+ worker.value.getName()+ "!");
        if (worker.value.getIsAdmin()){
            new AdminMenu().run();
        }
        else {
            new WorkerMenu().run();
        }
    }




    protected void LogOut() {
        Response logout = facade.logout();
        if (logout.ErrorOccurred())
            printPrettyError(logout.getErrorMessage());
        else {
            printPrettyConfirm("Logout succeed");
        }
    }


    protected void getDefaultWorkDay() {
        int day = getInputDay();
        ResponseT<WorkDayResponse> workDayResponse = facade.getDefaultShiftInDay(day);
        if (workDayResponse.ErrorOccurred()){
            printPrettyError(workDayResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm(workDayResponse.value.Settings());
        }
    }


    protected void getDefaultShift(){
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


    protected void printPrettyHeadline(String s) {
        System.out.println(ANSI_PURPLE + s + ANSI_RESET);
    }

    protected void printPrettyConfirm(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    protected void printPrettyError(String errorMessage) {
        System.out.println(ANSI_RED + errorMessage + ANSI_RESET);
    }


    protected String getInputDate(){
        System.out.println("Enter Date <DD/MM/YYYY>: ");
        return scanner.next();
    }

    protected String getInputShiftType(){
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

    protected String getInputJob(){
        System.out.print("Enter job name: ");
        return scanner.next();
    }


    protected String getInputWorkerID(){
        System.out.print("Enter worker id: ");
        return scanner.next();
    }


    protected int getInputDayType() {
        System.out.println("Choose day type:");
        System.out.println("1) Weekday");
        System.out.println("2) Friday");
        System.out.println("3) Saturday");
        int day = getInputInt();
        if (day == 2) day = 6;
        else if (day == 3) day = 7;
        else if (day != 1){
            printPrettyError("There's no such option");
            return getInputDayType();
        }
        return day;
    }
    protected int getInputDay() {
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

    protected boolean getInputYesNo() {
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

    protected int getInputInt() {
        while (!scanner.hasNextInt()){
            printPrettyError("Please enter a number");
            scanner.next();
        }
        return scanner.nextInt();
    }

    protected double getInputDouble() {
        while (!scanner.hasNextDouble()){
            printPrettyError("Please enter a number");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    protected String getInputConstraintType() {
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
