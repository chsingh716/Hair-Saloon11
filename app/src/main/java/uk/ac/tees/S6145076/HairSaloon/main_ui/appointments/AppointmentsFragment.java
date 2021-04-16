package uk.ac.tees.S6145076.HairSaloon.main_ui.appointments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.S6145076.HairSaloon.extraJava.MySharedPref22;
import uk.ac.tees.S6145076.HairSaloon.R;
import uk.ac.tees.S6145076.HairSaloon.support_activities.addAppointmentActivity;
import uk.ac.tees.S6145076.HairSaloon.admin_page.appointmentAdapter;
import uk.ac.tees.S6145076.HairSaloon.extraJava.callbackUpdate;
import uk.ac.tees.S6145076.HairSaloon.extraJava.sqliteDatabaseHandler;
import uk.ac.tees.S6145076.HairSaloon.model.adminAppointmentModel;


public class AppointmentsFragment extends Fragment {
   MySharedPref22 mySharedPref22;
    private uk.ac.tees.S6145076.HairSaloon.admin_page.appointmentAdapter appointmentAdapter;
    private RecyclerView recyclerView;


    sqliteDatabaseHandler databasehandler1; //sqlite
    private FloatingActionButton addAppointments;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isFetching = false;
    private TextView noData;
    List<adminAppointmentModel> serviceList= new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_appointments, container, false);
         mySharedPref22 = MySharedPref22.getInstance(getActivity());
        //add new appointment //
        root.findViewById(R.id.add_item).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getActivity().getApplicationContext(), addAppointmentActivity.class);
                 startActivity(intent);

             }
         });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        displayAppointments();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noData=view.findViewById(R.id.text_gallery);
        databasehandler1 = new sqliteDatabaseHandler(getActivity()); //sql
        //adapter
        recyclerView = view.findViewById(R.id.appointments_recycler_view);
        appointmentAdapter = new appointmentAdapter(getActivity(),null);  //initiate with null listArray
        //recyclerView.setAdapter(appointmentAdapter);
        displayAppointments();



        //delete listener
        appointmentAdapter.setonClick(new callbackUpdate() {
            @Override
            public void deleteListener(int id) {
                confirmCancel(getActivity(),id);
              //  Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccessStore(boolean state) {

            }
        });
    }



    /* this method help to pass data to Recycler view to display
    appointments
     */
    void displayAppointments(){
        serviceList.clear();
        Toast.makeText(getActivity(), mySharedPref22.getName(), Toast.LENGTH_SHORT).show();
        if ( getAppointmentList() != null &&  getAppointmentList().size() > 0) {
            for (int i = 0; i < getAppointmentList().size(); i++) {
                //user filter operation
               if ((getAppointmentList().get(i).getUserName()).equals(mySharedPref22.getName())) {
                   serviceList.add(getAppointmentList().get(i));
                 }
           }
        }
       if ( serviceList != null &&  serviceList.size() > 0) {

            recyclerView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
            appointmentAdapter.RecyclerViewAdapter(serviceList);
            recyclerView.setAdapter(appointmentAdapter);
        } else {  //if list is empty
            recyclerView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
    }

    /* get all admin list details store in the database
     */
    private List<adminAppointmentModel> getAppointmentList(){
        return databasehandler1.viewAll();
    }


    //delete store appointments // / // /
    void confirmCancel(Context context, int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to cancel this appointment?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        databasehandler1.deleteOne(position);//delete click item
                        displayAppointments();

                       // appointmentArrayList.remove(position);
                        //notifyItemRemoved(position);
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


}
