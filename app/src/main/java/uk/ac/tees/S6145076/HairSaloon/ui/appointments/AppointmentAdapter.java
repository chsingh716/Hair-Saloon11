package uk.ac.tees.S6145076.HairSaloon.ui.appointments;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.S6145076.HairSaloon.ServiceActivity;
import uk.ac.tees.S6145076.HairSaloon.R;
import uk.mylibrary.UserAppointment;

class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private final Context mContext;

    List<UserAppointment> userAppointments = new ArrayList<>();


    AppointmentAdapter(Context ctx) {
        this.mContext = ctx;
        userAppointments.add(new UserAppointment("Facials", "18 Dec 2020 , 15:00", "", "Accepted", "",""));
        userAppointments.add(new UserAppointment("Nails", "22 Dec 2020 , 13:00", "", "Rejected", "",""));
        userAppointments.add(new UserAppointment("Waxing", "25 Dec 2020 , 11:00", "", "", "",""));
        userAppointments.add(new UserAppointment("Lashes", "16 Dec 2020 , 14:00", "", "Accepted", "",""));

    }

    @NonNull
    @Override
    public AppointmentAdapter.AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppointmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_list_row, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.AppointmentViewHolder holder, int position) {

//        if (userAppointments.get(position).getStatus().isEmpty()) {
//            holder.cancelImage.setVisibility(View.VISIBLE);
//        } else {
//            holder.cancelImage.setVisibility(View.GONE);
//        }
        holder.serviceImage.setImageResource(getServiceImage(userAppointments.get(position).getServiceName()));

        if (position == 0) {
            holder.name.setText("ServiceName: " + userAppointments.get(position).getServiceName());
            holder.date.setText("Date: " + userAppointments.get(position).getDate());
            holder.status.setText("Status: " + userAppointments.get(position).getStatus());
        } else if (position == 1) {
            holder.name.setText("ServiceName: " + userAppointments.get(position).getServiceName());
            holder.date.setText("Date: " + userAppointments.get(position).getDate());
            holder.status.setText("Status: " + userAppointments.get(position).getStatus());
        } else if (position == 3) {
            holder.name.setText("ServiceName: " + userAppointments.get(position).getServiceName());
            holder.date.setText("Date: " + userAppointments.get(position).getDate());
            holder.status.setText("Status: " + userAppointments.get(position).getStatus());
        } else {
            holder.name.setText("ServiceName: " + userAppointments.get(position).getServiceName());
            holder.date.setText("Date: " + userAppointments.get(position).getDate());
            holder.status.setText("Status: " + userAppointments.get(position).getStatus());
        }
    }

    private int getServiceImage(String serviceName) {
        int res = 0;
        switch (serviceName) {
            case ServiceActivity.BROWS:
                res = R.drawable.brows;
                break;
            case ServiceActivity.NAILS:
                res = R.drawable.nails;
                break;
            case ServiceActivity.FACIALS:
                res = R.drawable.facials;
                break;
            case ServiceActivity.PEDICURE:
                res = R.drawable.pedicure;
                break;
            case ServiceActivity.WAXING:
                res = R.drawable.waxing;
                break;
            case ServiceActivity.LASHES:
                res = R.drawable.lashes;
                break;
        }
        return res;
    }

    @Override
    public int getItemCount() {
        return userAppointments.size();
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder {
        ImageView cancel;
        TextView name;
        TextView date;
        TextView status;
        ImageView serviceImage;
        ImageView cancelImage;

        public AppointmentViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            cancel = itemView.findViewById(R.id.cancel);
            name = itemView.findViewById(R.id.service_name);
            date = itemView.findViewById(R.id.service_date);
            status = itemView.findViewById(R.id.service_status);
            serviceImage = itemView.findViewById(R.id.service_image);
            cancelImage = itemView.findViewById(R.id.cancel);
            cancelImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    confirmCancel(context, getAdapterPosition());
                }
            });
        }
    }

    void confirmCancel(Context context, int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to cancel this appointment?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        userAppointments.remove(position);
                        notifyItemRemoved(position);
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
