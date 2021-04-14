package uk.mylibrary;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MyUserDao {
    @Query("select COUNT(*) FROM  user_table")
    int count();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(List<UserModel> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertUser(UserModel user);

    @Query("Select * from user_table")
    Single<List<UserModel>> getAllUsers();

    @Query("select * from user_table where userId  =:userId")
    Single<UserModel> getUserById(String userId);
}
