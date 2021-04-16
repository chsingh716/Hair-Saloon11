package uk.ac.tees.S6145076.HairSaloon.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.tees.S6145076.HairSaloon.R;
import uk.ac.tees.S6145076.HairSaloon.ServiceActivity;
import uk.ac.tees.S6145076.HairSaloon.extraJava.callbackUpdate;
import uk.ac.tees.S6145076.HairSaloon.model.adminAppointmentModel;


public class appointmentAdapter extends RecyclerView.Adapter<appointmentsHolder> {
    public List<adminAppointmentModel> appointmentsList;

    Context context;
    callbackUpdate customCallback;
    //MySharedPref22 mySharedPref22;

    //constructor of class get 2 parameters from parent class
    public appointmentAdapter(Context activity, List<adminAppointmentModel> list) {
        this.context = activity;
        this.appointmentsList = list; //location list Array
      //  mySharedPref22 = MySharedPref22.getInstance(activity);
    }


    /*   method to update location list array
    ex: when item is insert or delete from location list
     */
    public void RecyclerViewAdapter(List<adminAppointmentModel> appointmentsList) {
        this.appointmentsList = appointmentsList;

    }
/* setOnClick

 */
    public void setonClick(callbackUpdate callback){
        customCallback= callback;
    }

    @NonNull
    @Override
    public appointmentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_item_design, parent, false);
        return new appointmentsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull appointmentsHolder holder, int position) {

        final adminAppointmentModel listItem = this.appointmentsList.get(position);
        //pass data to view components as per list position
        holder.name.setText(listItem.getStyleName());
        holder.date_time.setText(listItem.getTimeDate());
        holder.userName.setText(listItem.getUserName());
        holder.styleImage.setImageResource(getStyleImage(listItem.getStyleName()));
        //picasso is use to pass url link(image) to ImageView component
       // Picasso.with(this.context).load(location.getIconUrl()).resize(70, 70).into(holder.icon);

        //delete button event listener (this is also a part of view component)
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customCallback.deleteListener(listItem.getId());
              //  getdeleteClickListener.onItemClick(appoint.getId());  //activate onItemClick  event
            }
        });

    }



    @Override
    public int getItemCount() {
        return this.appointmentsList.size();
    }

    private int getStyleImage(String styleName) {
        int res = 0;
        switch (styleName) {
            case ServiceActivity.HAIR:
                res = R.drawable.hair;
                break;
            case ServiceActivity.SHAVES:
                res = R.drawable.shaves;
                break;
            case ServiceActivity.FACIALS:
                res = R.drawable.facial;
                break;
            case ServiceActivity.HAIR_REMOVAl:
                res = R.drawable.hair_removal;
                break;
            case ServiceActivity.WAXING:
                res = R.drawable.waxing;
                break;
            case ServiceActivity.NAILS:
                res = R.drawable.nails;
                break;
            case ServiceActivity.HOT_TOWEL:
                res = R.drawable.hot_towel;
                break;
        }
        return res;
    }


}