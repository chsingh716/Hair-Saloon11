package uk.mylibrary;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MyServicesDao {
    @Query("select COUNT(*) FROM  services_table")
    int count();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertServices(List<ServiceModel> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertService(ServiceModel services);

    @Query("Select * from services_table")
    Single<List<ServiceModel>> getAllServices();

    @Query("select * from services_table where serviceId  =:id")
    Single<ServiceModel> getServiceById(String id);

}
