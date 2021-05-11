package BuisnnesLayer.Reports;

import java.util.LinkedList;

public class ReportFactory {
    private int report_id;

    public ReportFactory(int rep_id) {
        this.report_id = rep_id;
    }

    public Report getReport(String subject, String timeRange, LinkedList<String> categories) throws Exception {
        Report r = null;
        switch (subject) {
            case "defects":
                r = new ReportDefects(report_id, timeRange, categories);
                r.createReport();
                report_id++;
                break;
            case "missing":
                r = new ReportMissing(report_id, timeRange, categories);
                r.createReport();
                report_id++;
                break;
            case "stock":
                r = new ReportStock(report_id, timeRange, categories);
                r.createReport();
                report_id++;
                break;
        }
        return r;
    }
}

