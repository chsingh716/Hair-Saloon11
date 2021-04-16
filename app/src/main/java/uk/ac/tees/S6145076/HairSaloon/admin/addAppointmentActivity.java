package uk.ac.tees.S6145076.HairSaloon.admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

import uk.ac.tees.S6145076.HairSaloon.MySharedPref22;
import uk.ac.tees.S6145076.HairSaloon.R;
import uk.ac.tees.S6145076.HairSaloon.extraJava.sqliteDatabaseHandler;
import uk.ac.tees.S6145076.HairSaloon.model.adminAppointmentModel;


public class addAppointmentActivity extends AppCompatActivity {

    MySharedPref22 mySharedPref22;
    sqliteDatabaseHandler databasehandler1;  //sqlite
    private AppCompatSpinner spinner1;

    private Button addButton;
    private RecyclerView recyclerView;
    private timeAdapter adapter;
    private MaterialCalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_service);

        Toolbar toolbar = findViewById(R.id.admin_add_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         mySharedPref22= MySharedPref22.getInstance(getApplicationContext());
        //sqlite initilize
        databasehandler1 = new sqliteDatabaseHandler(getApplicationContext());


        spinner1 = findViewById(R.id.service_spinner);

//        serviceName = getIntent().getStringExtra(SERVICE_TYPE);

        recyclerView = findViewById(R.id.service_page_list);
        adapter = new timeAdapter(this);
        recyclerView.setAdapter(adapter);

        calendarView = findViewById(R.id.service_page_calendarView);

        //add button listener
        addButton = findViewById(R.id.admin_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddItem();
            }
        });

        initCalendarView();
    }

    private void initCalendarView() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        CalendarDay minimum = CalendarDay.from(year, (month + 1), day);

        calendarView.setDateSelected(minimum, true);
        calendarView.state().edit()
                .setMinimumDate(minimum)
                .setMaximumDate(CalendarDay.from(2021, 5, 15))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
    }

    protected void onAddItem() {
        String styleName = spinner1.getSelectedItem().toString();
        CalendarDay calendarDay = calendarView.getSelectedDate();
        int day = calendarDay.getDay();
        int month = calendarDay.getMonth();
        int year = calendarDay.getYear();

        String date = day + "-" + month + "-" + year;
        String time = adapter.getSelectedTime();
        String userName= mySharedPref22.getName();

        if (!styleName.isEmpty() && !time.isEmpty() && !date.isEmpty() && !userName.isEmpty()) {
            //insert data to database
            String date_time= date + " , " + time;
            adminAppointmentModel add_item=new adminAppointmentModel(styleName,date_time,userName,"Accepted");
            databasehandler1.insertContact(add_item);
            Toast.makeText(addAppointmentActivity.this, "Service added successfully", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
