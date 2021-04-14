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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import uk.ac.tees.S6145076.HairSaloon.SplashActivity;
import uk.ac.tees.S6145076.HairSaloon.R;
import uk.mylibrary.AppDataBase;
import uk.mylibrary.UserAppointment;

import static uk.mylibrary.UserAppointment.ACCEPTED_STATUS;
import static uk.mylibrary.UserAppointment.REJECTED_STATUS;

public class AdminRequestsActivity extends AppCompatActivity {

    private AdminRequestsAdapter adminRequestsAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isFetching = false;
    private List<UserAppointment> mRequests = new ArrayList<>();
    private TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_requests);

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
        adminRequestsAdapter = new AdminRequestsAdapter(this, new AdminRequestsAdapter.OnRequestAction() {
            @Override
            public void requestAction(UserAppointment userAppointment, boolean accepted) {
                updateRequestStatus(userAppointment, accepted);
            }
        });
        recyclerView.setAdapter(adminRequestsAdapter);

        floatingActionButton = findViewById(R.id.admin_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminRequestsActivity.this, AdminAddServiceActivity.class);
                startActivity(intent);
            }
        });

        getDataFromDatabase();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (!isFetching) {
                isFetching = true;
                getDataFromDatabase();
            }
        });

    }

    private void getDataFromDatabase() {
        mRequests.clear();
      Toast.makeText(getApplicationContext(),"hkjhjkhjkhj",Toast.LENGTH_SHORT).show();
        mRequests.add(new UserAppointment("Facials", "18 Dec 2020 , 15:00", "", "Accepted", "", "Anna"));
      mRequests.add(new UserAppointment("Nails", "22 Dec 2020 , 13:00", "", "Rejected", "", "Sara"));
      mRequests.add(new UserAppointment("Lashes", "16 Dec 2020 , 14:00", "", "Accepted", "", "Mariam"));
      onSuccess(mRequests);

      }

    private void onError(Throwable throwable) {
Toast.makeText(getApplicationContext(),"err",Toast.LENGTH_SHORT).show();
    }

    private void onSuccess(List<UserAppointment> userAppointments) {
        swipeRefreshLayout.setRefreshing(false);
        if (userAppointments != null && userAppointments.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);

            mRequests.addAll(userAppointments);
            adminRequestsAdapter.updateRequests(mRequests);
        } else {
            recyclerView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }

    }

    public void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(AdminRequestsActivity.this, SplashActivity.class));
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

    private void updateRequestStatus(UserAppointment userAppointment, boolean accepted) {
        String status;
        if (accepted) {
            status = ACCEPTED_STATUS;
        } else {
            status = REJECTED_STATUS;
        }
        userAppointment.setStatus(status);
        AppDataBase.getInstance(this).getRequestsDao().insertRequests(userAppointment);
        String msg;
        if (accepted) {
            msg = "Request Confirmed";
        } else {
            msg = "Request Rejected";
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
