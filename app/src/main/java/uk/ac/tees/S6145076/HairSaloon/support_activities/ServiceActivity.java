package uk.ac.tees.S6145076.HairSaloon.support_activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import uk.ac.tees.S6145076.HairSaloon.R;
import uk.ac.tees.S6145076.HairSaloon.extraJava.MySharedPref22;
import uk.ac.tees.S6145076.HairSaloon.model.ServiceTimeAdapter;


public class ServiceActivity extends AppCompatActivity {

    public static final String HAIR = "Hair";
    public static final String SHAVES = "Shaves";
    public static final String NAILS = "Nails";
    public static final String WAXING = "Waxing";
    public static final String FACIALS = "Facials";
    public static final String HAIR_REMOVAl = "Hair Removal";
    public static final String HOT_TOWEL = "Hot Towel";

    public static final String SERVICE_TYPE = "service_type";

    private String serviceName;
    private TextView title;
    private RecyclerView recyclerView;
    private ServiceTimeAdapter adapter;
    private MaterialCalendarView calendarView;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private CalendarDay currentDate;
    private String timeSelected;
    private String userId;
    private String userName;
    MySharedPref22 mySharedPref22;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service12);
        Toolbar toolbar = findViewById(R.id.service_page_toolbar);
        setSupportActionBar(toolbar);


        mySharedPref22 = MySharedPref22.getInstance(ServiceActivity.this);
        userId = mySharedPref22.getUserId();
        userName = mySharedPref22.getName();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = toolbar.findViewById(R.id.service_title);

        serviceName = getIntent().getStringExtra(SERVICE_TYPE);
        recyclerView = findViewById(R.id.service_page_list);
        adapter = new ServiceTimeAdapter(this, new ServiceTimeAdapter.OnTimeSelected() {
            @Override
            public void onSelected(String time) {
                timeSelected = time;
                showConfirmDialog();
            }
        });
        recyclerView.setAdapter(adapter);

        calendarView = findViewById(R.id.service_page_calendarView);

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

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                currentDate = date;
            }
        });

    }

    private void showConfirmDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("BOOKING CONFIRMED");
        if (currentDate == null)
            currentDate = calendarView.getSelectedDate();

        builder.setMessage("Your booking confirmed for " + serviceName + " service on " + currentDate.getDate().toString() + ", " + timeSelected)
                .setCancelable(false)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



    private void onError(Throwable throwable) {
        Log.v("success", "save");
    }

    private void onSuccess(Long aLong) {
        Log.v("Fail", "save");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
