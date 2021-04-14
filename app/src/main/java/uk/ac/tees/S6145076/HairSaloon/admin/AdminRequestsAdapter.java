package uk.ac.tees.S6145076.HairSaloon.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

class AdminRequestsAdapter extends RecyclerView.Adapter<AdminRequestsAdapter.AppointmentViewHolder> {

    private final Context mContext;

    List<UserAppointment> userAppointments = new ArrayList<>();
    static OnRequestAction onRequestAction;


    AdminRequestsAdapter(Context ctx, OnRequestAction onRequestAction) {
        this.mContext = ctx;
        this.onRequestAction = onRequestAction;

    }

    public void addData() {
        userAppointments.add(new UserAppointment("Facials", "18 Dec 2020 , 15:00", "", "Accepted", "", "Anna"));
        userAppointments.add(new UserAppointment("Nails", "22 Dec 2020 , 13:00", "", "Rejected", "", "Sara"));
        userAppointments.add(new UserAppointment("Lashes", "16 Dec 2020 , 14:00", "", "Accepted", "", "Mariam"));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminRequestsAdapter.AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppointmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_request_row, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRequestsAdapter.AppointmentViewHolder holder, int position) {

        holder.serviceImage.setImageResource(getServiceImage(userAppointments.get(position).getServiceName()));
        String name = userAppointments.get(position).getUserName();
        if (name == null)
            name = "Example User";

        holder.userName.setText("UserName: " + name);

        if (position == 0) {
            holder.name.setText("ServiceName: " + userAppointments.get(position).getServiceName());
            holder.date.setText("Date: " + userAppointments.get(position).getDate());
        } else if (position == 1) {
            holder.name.setText("ServiceName: " + userAppointments.get(position).getServiceName());
            holder.date.setText("Date: " + userAppointments.get(position).getDate());
        } else if (position == 2) {
            holder.name.setText("ServiceName: " + userAppointments.get(position).getServiceName());
            holder.date.setText("Date: " + userAppointments.get(position).getDate());
        } else {
            holder.name.setText("ServiceName: " + userAppointments.get(position).getServiceName());
            holder.date.setText("Date: " + userAppointments.get(position).getDate());
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

    public void updateRequests(List<UserAppointment> requests) {
        userAppointments.clear();
        userAppointments.addAll(requests);
        notifyDataSetChanged();
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder {
        Button reject;
        Button approve;
        TextView name;
        TextView userName;
        TextView date;
        ImageView serviceImage;

        public AppointmentViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            reject = itemView.findViewById(R.id.admin_reject);
            approve = itemView.findViewById(R.id.admin_approve);
            name = itemView.findViewById(R.id.service_name);
            userName = itemView.findViewById(R.id.user_name);
            date = itemView.findViewById(R.id.service_date);
            serviceImage = itemView.findViewById(R.id.service_image);
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmDialog(context, getAdapterPosition(), 2);
                }
            });
            approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmDialog(context, getAdapterPosition(), 1);
                }
            });
        }
    }

    void confirmDialog(Context context, int position, int type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String msg = "";
        if (type == 1) {
            msg = "Confirm Approve this appointment?";
        } else {
            msg = "Confirm Reject this appointment?";
        }

        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        onRequestAction.requestAction(userAppointments.get(position), type == 1 ? true : false);
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

    public interface OnRequestAction {
        void requestAction(UserAppointment requestId, boolean accepted);
    }
}
