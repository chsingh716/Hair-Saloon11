package uk.ac.tees.S6145076.HairSaloon.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.S6145076.HairSaloon.SplashActivity;
import uk.ac.tees.S6145076.HairSaloon.R;
import uk.ac.tees.S6145076.HairSaloon.extraJava.callbackUpdate;
import uk.ac.tees.S6145076.HairSaloon.model.adminAppointmentModel;


import uk.ac.tees.S6145076.HairSaloon.extraJava.sqliteDatabaseHandler;


public class appointments_activity extends AppCompatActivity {

    private appointmentAdapter adminRequestsAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton addAppointments;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isFetching = false;
    private TextView noData;


    //////////////////////////////////////////
    sqliteDatabaseHandler databasehandler1;
    private List<adminAppointmentModel> appointList = new ArrayList<>();

    //onStart method is call when we move when activity to other
  protected void onStart(){
      super.onStart();
      displayAppointments();
      //Toast.makeText(getApplicationContext(),"Start...",Toast.LENGTH_SHORT ).show();
  }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_requests);


        databasehandler1 = new sqliteDatabaseHandler(getApplicationContext());


        Toolbar toolbar = findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);

        noData = findViewById(R.id.textView_no_result);
        ImageView logout = toolbar.findViewById(R.id.admin_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmLogout();
            }
        });


        swipeRefreshLayout = findViewById(R.id.admin_swipe);
        recyclerView = findViewById(R.id.admin_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext())); //create linearLayout to display multi-items
        adminRequestsAdapter=new appointmentAdapter(getApplicationContext(),getAppointmentList());//pass locationList with Context to Custom adapter
        recyclerView.setAdapter(adminRequestsAdapter);



        //delete listener
        adminRequestsAdapter.setonClick(new callbackUpdate() {
            @Override
            public void deleteListener(int id) {
                cancelAppointment(id);
              //  confirmCancel(getApplicationContext(),id);
                  Toast.makeText(getApplicationContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
            }
        });



        //add new appointment by admin user event listener //
        addAppointments = findViewById(R.id.admin_fab);
        addAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(appointments_activity.this, addAppointmentActivity.class);
                startActivity(intent);
            }
        });



        /* check appointment list is empty or not and then display data on the recycler

         */
        displayAppointments();

//        swipeRefreshLayout.setOnRefreshListener(() -> {
//            swipeRefreshLayout.setRefreshing(false);
//        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (!isFetching) {
                swipeRefreshLayout.setRefreshing(true);
                displayAppointments();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    /* this method help to pass data to Recycler view to display
    appointments
     */
    void displayAppointments(){
        appointList=getAppointmentList();

        adminRequestsAdapter.RecyclerViewAdapter(appointList);
        if (appointList != null && appointList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
            adminRequestsAdapter.RecyclerViewAdapter(appointList);  //pass array list to adapter
            recyclerView.setAdapter(adminRequestsAdapter);
        } else {  //if list is empty
            recyclerView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }

    }


    private void onError(Throwable throwable) {
    Toast.makeText(getApplicationContext(),"err",Toast.LENGTH_SHORT).show();
    }



    public void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(appointments_activity.this, SplashActivity.class));
                        finish();
                    }
                });
    }

    private void confirmLogout() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        logout();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }






    private void cancelAppointment(int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to cancel appointment?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {

                        //delete appointment //
                        databasehandler1.deleteOne(position);
                        displayAppointments();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }











    /* get all admin list details store in the database
     */
    private List<adminAppointmentModel> getAppointmentList(){
        return databasehandler1.viewAll();
    }

}
