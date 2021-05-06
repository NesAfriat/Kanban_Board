package BuisnnesLayer.Controlls;

import BuisnnesLayer.Reports.Report;
import BuisnnesLayer.Reports.ReportFactory;
import BuisnnesLayer.Reports.Subject;
import BuisnnesLayer.Reports.TimeRange;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import static BuisnnesLayer.FacedeModel.inventModel.getDate;

public class Reports_Controller {
    private static Reports_Controller report_C = null;
    private HashMap<Integer, Report> reports;           //holds all reports ever created
    private ReportFactory reportFactory;                //hold the class which create a report

    private void check_valid_string(String[] arr) throws Exception {
        for (String str : arr) {
            if (str.length() == 0)
                throw new Exception("input was '' which is invalid");
            if (str.length() >= 300)
                throw new Exception("input was too long (300) which is invalid");
        }
    }

    private void check_valid_string(LinkedList<String> lst) throws Exception {
        for (String str : lst) {
            if (str.length() == 0)
                throw new Exception("input was '' which is invalid");
            if (str.length() >= 300)
                throw new Exception("input was too long (300) which is invalid");
        }
    }

    private void check_valid_number(Number[] arr) throws Exception {
        for (Number number : arr) {
            if (number.doubleValue() < 0) {
                throw new Exception("negative number is not allowed");
            }
        }
    }

    private void check_valid_Dates(Date date) throws Exception {
        Date now = new Date();
        if (date.after(now))
            throw new Exception("date can't be after today");
    }

    private Reports_Controller() {
        this.reports = new HashMap<>();
        this.reportFactory = new ReportFactory();
    }

    public static Reports_Controller getInstance() {
        if (report_C == null)
            report_C = new Reports_Controller();
        return report_C;
    }

    public Report createReport( String subject, String timeRange, LinkedList<String> categories) throws Exception {
        check_valid_string(new String[]{subject, timeRange});
        check_valid_string(categories);
        Subject sub = convertSubject(subject);
        Report r = reportFactory.getReport(sub, timeRange, categories);
        reports.put(r.getReportID(), r);
        return r;
    }


    private Subject convertSubject(String sub) throws Exception {
        switch (sub.toLowerCase()) {
            case "stock":
                return Subject.Stock;
            case "missing":
                return Subject.Missing;
            case "defects":
                return Subject.Defects;
            default:
                throw new Exception("No such Subject");
        }
    }

    public Report getReportById(int id) throws Exception {
        if (!reports.containsKey(id))
            throw new Exception("report id doesnt exist");
        return reports.get(id);
    }

    // search reports by date and subject
    public String getReportId(String subject, Date date) throws Exception {
        check_valid_Dates(date);
        check_valid_string(new String[]{subject});
        String output = "Report ID's about " + subject + "from " + date + ":\n";
        for (Report r : reports.values()) {
            if (subject.equals(r.getSubject()) &&
                    date.equals(r.getCreationDate()))
                output = output + r.getReportID();
        }
        return output;
    }

    public LinkedList<String> get_all_reports() {
        LinkedList<String> output = new LinkedList<>();
        for (Report report : reports.values()) {
            output.addFirst(report.getReportData() + " " + report.getSubject() + " " + getDate(report.getDate()) + "\n");
        }
        return output;
    }
}


