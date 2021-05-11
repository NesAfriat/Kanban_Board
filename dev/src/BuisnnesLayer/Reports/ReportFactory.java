package BuisnnesLayer.Reports;

import BuisnnesLayer.Reports.*;

import java.util.LinkedList;

public class ReportFactory {
    private int report_id;

    public ReportFactory() {
        report_id = 0;
    }

    public Report getReport(Subject subject, String timeRange, LinkedList<String> categories) throws Exception {
        Report r = null;
        if (subject.equals(Subject.Defects)) {
            r = new ReportDefects(report_id,timeRange, categories);
            r.createReport();
            report_id++;
        } else if (subject.equals(Subject.Missing)) {
            r = new ReportMissing(report_id, timeRange, categories);
            r.createReport();
            report_id++;
        } else if (subject.equals(Subject.Stock)) {
            r = new ReportStock(report_id, timeRange, categories);
            r.createReport();
            report_id++;
        }
        return r;
    }
}

