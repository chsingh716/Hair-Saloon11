package uk.mylibrary;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "appointments_table")

public class UserAppointment {

    UserAppointment(){}
    @PrimaryKey(autoGenerate = true)
    int id;

    public static final String PENDING_STATUS = "Pending";
    public static final String ACCEPTED_STATUS = "Accepted";
    public static final String REJECTED_STATUS = "Rejected";
    public static final String COMPLETED_STATUS = "Completed";

    public UserAppointment(String serviceName, String date, String serviceId, String status, String userId, String userName) {
        this.serviceName = serviceName;
        this.date = date;
        this.serviceId = serviceId;
        this.status = status;
        this.userId = userId;
        this.userName = userName;
    }

    public String serviceName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String date;
    public String serviceId;
    public String status;
    public String userId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String requestId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String userName;

}
