package PresentationLayer;

import BusinessLayer.Responses.Response;

class EditDefaultWorkDayShiftMenu extends AdminMenu {
    void run(){
        printPrettyHeadline("\n\nEdit Default Settings Menu");
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
                run();
            case 2:
                setDefaultShift();
                run();
                break;
            case 3:
                getDefaultWorkDay();
                run();
            case 4:
                setDefaultWorkDay();
                run();
                break;
            case 5:
                super.run();
                break;
            case 6:
                LogOut();
                System.exit(0);
            default:
                System.out.println("No such option");
                run();
        }
    }

    private void setDefaultShift() {
        int day = getInputDayType();
        String shiftType = getInputShiftType();
        String role = getInputJob();
        System.out.println("Enter new amount required: ");
        int amountRequired = getInputInt();
        Response response = facade.setDefaultJobsInShift(day, shiftType, role, amountRequired);
        if (response.ErrorOccurred()){
            printPrettyError(response.getErrorMessage());
        }
        else {
            printPrettyConfirm("New shift default updated successfully");
        }
    }

    private void setDefaultWorkDay() {
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

}
