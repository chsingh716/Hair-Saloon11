package uk.ac.tees.S6145076.HairSaloon.admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import uk.ac.tees.S6145076.HairSaloon.R;
import uk.mylibrary.AppDataBase;
import uk.mylibrary.ServiceModel;

public class AdminAddServiceActivity extends AppCompatActivity {

    public static final String LASHES = "Lashes";
    public static final String WAXING = "Waxing";
    public static final String NAILS = "Nails";
    public static final String FACIALS = "Facials";
    public static final String BROWS = "Brows";
    public static final String PEDICURE = "Pedicure";
    SpotsDialog waitingDialog;

    public static final String SERVICE_TYPE = "service_type";
    private static final String SERVICE_DB = "Services";
    private int max;
    private DatabaseReference databaseReference;

    private String serviceName;
    private TextView title;
    private RecyclerView recyclerView;
    private AdminServiceTimeAdapter adapter;
    private MaterialCalendarView calendarView;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private AppCompatSpinner spinner;
    private Button addButton;
    private ServiceModel serviceModel;
    private AppDataBase appDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_service);
        Toolbar toolbar = findViewById(R.id.admin_add_toolbar);
        setSupportActionBar(toolbar);

        appDataBase = AppDataBase.getInstance(AdminAddServiceActivity.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = toolbar.findViewById(R.id.service_page_title);
        spinner = findViewById(R.id.service_spinner);
        waitingDialog = new SpotsDialog(this, R.style.waiting_dialog);

        serviceName = getIntent().getStringExtra(SERVICE_TYPE);

        recyclerView = findViewById(R.id.service_page_list);
        adapter = new AdminServiceTimeAdapter(this);
        recyclerView.setAdapter(adapter);

        calendarView = findViewById(R.id.service_page_calendarView);

        addButton = findViewById(R.id.admin_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddClicked();
            }
        });

        initCalendarView();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");
//
//        initDataBase();

    }

//    private void initDataBase() {
//        databaseReference = FirebaseDatabase.getInstance().getReference(SERVICE_DB);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot != null) {
//                    for (DataSnapshot data : dataSnapshot.getChildren()) {
//                        max = Integer.parseInt(data.getKey());
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d("TAG", "onCancelled: ");
//            }
//        });
//    }

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

    protected void onAddClicked() {
        String service = spinner.getSelectedItem().toString();
        CalendarDay calendarDay = calendarView.getSelectedDate();
        int day = calendarDay.getDay();
        int month = calendarDay.getMonth();
        int year = calendarDay.getYear();

        String date = month + "-" + day + "-" + year;
        String time = adapter.getSelectedTime();

        if (!service.isEmpty() && !time.isEmpty() && !date.isEmpty()) {

            serviceModel = new ServiceModel(service, date, time);


            appDataBase.getServiceDao().insertService(serviceModel);
            Toast.makeText(AdminAddServiceActivity.this, "Service added successfully", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
