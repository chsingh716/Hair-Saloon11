package uk.ac.tees.S6145076.HairSaloon.model;

public class adminAppointment {
    private int id;
    private String styleName;
    private String timeDate;
    private String userName;
    private String Status;

    public String getStyleName() {
        return styleName;
    }

    public adminAppointment(int id, String styleName, String timeDate, String userName, String status) {
        this.id = id;
        this.styleName = styleName;
        this.timeDate = timeDate;
        this.userName = userName;
        Status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


    public adminAppointment() {
    }


}
