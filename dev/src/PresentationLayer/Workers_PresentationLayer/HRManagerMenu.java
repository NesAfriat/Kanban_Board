package PresentationLayer.Workers_PresentationLayer;

import BusinessLayer.Workers_BusinessLayer.Workers_Facade;
import BusinessLayer.Workers_BusinessLayer.Responses.*;

class HRManagerMenu extends Workers_Main_Menu {
    HRManagerMenu(Workers_Facade facade){
        super(facade);
    }
    void run() {
        boolean prev = false;
        while (!prev) {
            printPrettyHeadline("\n\nHR Manager Menu");
            System.out.println("1) Manage Workers");
            System.out.println("2) Manage Shifts");
            System.out.println("3) Logout");
            System.out.println("4) Exit");
            System.out.print("Option: ");
            int option = getInputInt();
            switch (option) {
                case 1:
                    new WorkersManageMenu(facade).run();
                    break;
                case 2:
                    ShiftsManageMenu();
                    break;
                case 3:
                    LogOut();
                    prev = true;
                    break;
                case 4:
                    super.exit();
                default:
                    printPrettyError("No such option");
                    run();
            }
        }
    }



    private void ShiftsManageMenu() {
        boolean prev = false;
        while (!prev) {
            printPrettyHeadline("\n\nShifts Manage Menu");
            System.out.println("1) View shift arrangement");
            System.out.println("2) Edit shift");
            System.out.println("3) Add new shifts");
            System.out.println("4) Remove shift");
            System.out.println("5) Edit default shift/workday");
            System.out.println("6) Previous");
            System.out.println("7) Exit");
            System.out.print("Option: ");
            int option = getInputInt();
            switch (option) {
                case 1:
                    adminViewShiftArrangement();
                    break;
                case 2:
                    EditShift();
                    break;
                case 3:
                    new AddShiftsMenu(facade).run();
                    break;
                case 4:
                    RemoveShift();
                    break;
                case 5:
                    new EditDefaultWorkDayShiftMenu(facade).run();
                    break;
                case 6:
                    prev = true;
                    break;
                case 7:
                    super.exit();
                default:
                    printPrettyError("No such option");
            }
        }
    }

    private void EditShift() {
        String date = getInputDate();
        String shiftType = getInputShiftType();

        ResponseT<ShiftResponse> shiftResponse = facade.chooseShift(date, shiftType);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("Shift selected successfully");
            new EditShiftsMenu(facade).run();
        }
    }

    private void adminViewShiftArrangement() {
        String date = getInputDate();
        ResponseT<WorkDayResponse> workDay = facade.viewShiftArrangement(date);
        if (workDay.ErrorOccurred()){
            printPrettyError(workDay.getErrorMessage());
        }
        else {
            printPrettyConfirm(workDay.value.toString());
        }
    }

    private void RemoveShift(){
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



}
