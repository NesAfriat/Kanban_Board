package BuisnnesLayer.Reports;

import BuisnnesLayer.Controlls.Stock_Controller;
import BuisnnesLayer.GeneralProduct;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class ReportStock implements Report {
    private final Subject subject = Subject.Stock;
    private final Date date = new Date();
    private Integer reportID;
    private LinkedList<String> categories;
    private TimeRange timeRange;
    private String report_data;

    public ReportStock(Integer reportId,TimeRange time, LinkedList<String> categories) {
        this.reportID = reportId;
        this.categories = categories;
        this.timeRange = time;
        this.report_data = "";
    }

    @Override
    public void createReport() throws Exception {
        Stock_Controller st_c = Stock_Controller.getInstance(null);
        List<GeneralProduct> existProducts;
        for (String cat : this.categories) {
            existProducts = st_c.get_category_products(cat);
            for (GeneralProduct p : existProducts)
                this.report_data = this.report_data +"\n" +p.toString();
        }
    }

    @Override
    public String getSubject() {
        return this.subject.name();
    }

    @Override
    public Date getDate() {
        return this.date;
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
        return timeRange.name();
    }

    @Override
    public String getReportData() {
        return this.report_data;
    }

    public String toString() {
        return "Report{" +
                "\nreport_id= " + this.reportID +
                "\nreport_date= " + this.date +
                "\nsubject=" + subject +
                "\ntimeRange=" + timeRange +
                "\nreport_data= " + report_data +
                '}';
    }
}
