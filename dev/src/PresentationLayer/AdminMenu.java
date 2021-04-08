package PresentationLayer;

import BusinessLayer.Responses.*;

import java.util.List;

class AdminMenu extends Menu{

    void run() {
        printPrettyHeadline("\n\nAdmin Menu");
        System.out.println("1) Manage Workers");
        System.out.println("2) Manage Shifts");
        System.out.println("3) Logout");
        System.out.println("4) Exit");
        System.out.print("Option: ");
        int option = getInputInt();
        switch (option){
            case 1:
                new WorkersManageMenu().run();
                break;
            case 2:
                ShiftsManageMenu();
                break;
            case 3:
                LogOut();
                start(false);
                break;
            case 4:
                LogOut();
                System.exit(0);
            default:
                printPrettyError("No such option");
                run();
        }
    }



    private void ShiftsManageMenu() {
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
                printPrettyError("No such option");
                ShiftsManageMenu();
        }
    }

    private void EditShift() {
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

}
