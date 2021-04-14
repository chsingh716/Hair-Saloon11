package uk.mylibrary;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MyRequestsDao {
    @Query("select COUNT(*) FROM  appointments_table")
    int count();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRequests(List<UserAppointment> requests);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertRequests(UserAppointment appointment);

    @Query("Select * from appointments_table")
    Single<List<UserAppointment>> getAllRequests();

    @Query("select * from appointments_table where id  =:userId")
    Single<UserAppointment> getRequestById(int userId);

}
