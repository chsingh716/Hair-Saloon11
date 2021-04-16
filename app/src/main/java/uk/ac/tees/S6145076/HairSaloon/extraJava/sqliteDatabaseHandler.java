package uk.ac.tees.S6145076.HairSaloon.extraJava;

/* this class manage all SQlite insert, delete, view and edit operations
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.S6145076.HairSaloon.model.adminAppointmentModel;

public class sqliteDatabaseHandler extends SQLiteOpenHelper {

    //database parameters
    public static final String DATABASE_NAME = "appointment_system321.db";
    public static final String LOCATIONS_TABLE_NAME = "adminDisplayTable";
    public static final String LOCATIONS_COLUMN_ID = "id";
    public static final String LOCATIONS_COLUMN_NAME = "styleName";
    public static final String LOCATIONS_COLUMN_dataTime = "dataTime";
    public static final String LOCATIONS_COLUMN_userName = "userName";
    public static final String LOCATIONS_COLUMN_Status = "status";


    public sqliteDatabaseHandler(@Nullable Context context) {
        //set database name, version .
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //we can create query in the form of string as written below()
//        String createTableStatement= "create table contacts " +
//                "(id integer primary key, name text,age int,studentID int)";
        //write query using global variables
        String createTableStatement= "create table " + LOCATIONS_TABLE_NAME +
                " ( " + LOCATIONS_COLUMN_ID + " integer primary key, " + LOCATIONS_COLUMN_NAME + " text, "
                + LOCATIONS_COLUMN_dataTime + " text, " + LOCATIONS_COLUMN_userName + " text, " + LOCATIONS_COLUMN_Status + " text)";
        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }


    //method to insert data in the database
    public boolean insertContact(adminAppointmentModel adminAppointmentModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(LOCATIONS_COLUMN_NAME, adminAppointmentModel.getStyleName().toString());
        cv.put(LOCATIONS_COLUMN_dataTime, adminAppointmentModel.getTimeDate().toString());
        cv.put(LOCATIONS_COLUMN_userName, adminAppointmentModel.getUserName().toString());
        cv.put(LOCATIONS_COLUMN_Status, adminAppointmentModel.getStatus().toString());
        long insert= db.insert(LOCATIONS_TABLE_NAME,null,cv);
        if(insert==-1){
            return false;
        }
        else{
            return true;
        }
    }


    //get or view data store in the database table
    public List<adminAppointmentModel> viewAll(){
        //define list type
        List<adminAppointmentModel> returnList= new ArrayList<>();
        String getQuery= "select * from " + LOCATIONS_TABLE_NAME;
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor= db.rawQuery(getQuery,null);
        if(cursor.moveToFirst()){
            do{
                int id= cursor.getInt(0);
                String serviceName= cursor.getString(1);
                String date= cursor.getString(2);
                String username= cursor.getString(3);
                String status= cursor.getString(4);
                adminAppointmentModel adminAppointmentModel = new adminAppointmentModel(id,serviceName,date,username,status);
                returnList.add(adminAppointmentModel);

            }while (cursor.moveToNext());
            db.close();
        }

        //return list of appoint. array
        return returnList;
    }

    //delete entry from the database according to position or index num.
    public boolean deleteOne(int position){
        String query= "delete from " + LOCATIONS_TABLE_NAME + " where " + LOCATIONS_COLUMN_ID + " = " + position;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            return false;
        }
        else{
            return true;
        }
    }
}