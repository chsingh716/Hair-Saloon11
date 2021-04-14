package uk.mylibrary;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ServiceModel.class, UserModel.class, UserAppointment.class}, version = 1, exportSchema = false)

public abstract class AppDataBase extends RoomDatabase {

    public static final String DB_NAME = "bomor.db";

    private static volatile AppDataBase INSTANCE;

    public static AppDataBase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract MyUserDao getUserDao();

    public abstract MyServicesDao getServiceDao();

    public abstract MyRequestsDao getRequestsDao();


}
