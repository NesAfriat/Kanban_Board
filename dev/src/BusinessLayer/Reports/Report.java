package BusinessLayer.Reports;

import java.util.Date;
import java.util.LinkedList;

public interface Report {
    void createReport() throws Exception;

    String toString();

    String getSubject();

    Date getCreationDate();

    int getReportID();

    LinkedList<String> getCategories();

    String getTimeRange();

    String getReportData();

}
