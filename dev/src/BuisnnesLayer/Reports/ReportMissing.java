package BuisnnesLayer.Reports;

import BuisnnesLayer.Controlls.Stock_Controller;
import BuisnnesLayer.GeneralProduct;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ReportMissing implements Report {
    private final Subject subject = Subject.Missing;
    private final Date creationDate = new Date();
    private Integer reportID;
    private LinkedList<String> categories;
    private String timeRange;
    private String report_data;

    public ReportMissing(Integer reportId, String time, LinkedList<String> categories) {
        this.reportID = reportId;
        this.categories = categories;
        this.timeRange = time;
        this.report_data = "";
    }

    @Override
    public void createReport() throws Exception {
        Stock_Controller st_c = Stock_Controller.getInstance(null);
        List<GeneralProduct> products = st_c.get_missing_products();
        List<GeneralProduct> existProducts;
        for (GeneralProduct p : products) {
            for (String cat : this.categories) {
                existProducts = st_c.get_category_products( cat);
                if (existProducts.contains(p))
                    this.report_data = this.report_data +"\n"+ p.toString();
            }
        }
    }

    @Override
    public String getSubject() {
        return this.subject.name();
    }

    @Override
    public Date getCreationDate() {
        return this.creationDate;
    }

    @Override
    public int getReportID() {
        return this.reportID;
    }


    @Override
    public LinkedList<String> getCategories() {
        return this.categories;
    }

    @Override
    public String getTimeRange() {
        return timeRange;
    }

    @Override
    public String getReportData() {
        return this.report_data;
    }

    @Override
    public String toString() {
        return "Report{" +
                "\nreport_id= " + this.reportID +
                "\nreport_date= " + this.creationDate +
                "\nsubject=" + subject +
                "\ntimeRange=" + timeRange +
                "\nreport_data= " + report_data +
                '}';
    }
}
