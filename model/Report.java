package model;

import java.time.LocalDate;

public class Report {

    private String userId;
    private String rumourId;
    private LocalDate reportDate;
    private String type;

    public Report(String userId, String rumourId, String type) {
        this.userId = userId;
        this.rumourId = rumourId;
        this.type = type;
        this.reportDate = LocalDate.now();
    }

    public String getUserId() { return userId; }

    public String getRumourId() { return rumourId; }

    public LocalDate getReportDate() { return reportDate; }

    public String getType() { return type; }
}
