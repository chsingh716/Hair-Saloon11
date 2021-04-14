package uk.mylibrary;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "services_table")
public class ServiceModel {
    ServiceModel() {
    }

    public String serviceName;

    @PrimaryKey()
    @NonNull
    int serviceId;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String serviceDate;
    public String serviceTime;


    public ServiceModel(String serviceName, String serviceDate, String serviceTime) {
        this.serviceName = serviceName;
        this.serviceDate = serviceDate;
        this.serviceTime = serviceTime;
    }


}
