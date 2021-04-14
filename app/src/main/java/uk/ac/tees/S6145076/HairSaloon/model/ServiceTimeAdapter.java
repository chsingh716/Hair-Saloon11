package uk.ac.tees.S6145076.HairSaloon.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.S6145076.HairSaloon.R;

public class ServiceTimeAdapter extends RecyclerView.Adapter<ServiceTimeAdapter.AppointmentViewHolder> {

    final Context mContext;
    private final OnTimeSelected onTimeSelected;

    private List<String> availableTimes = new ArrayList<>();
    private String mSelectedTime = "";

    public ServiceTimeAdapter(Context context, OnTimeSelected onTimeSelected) {
        this.mContext = context;
        this.onTimeSelected = onTimeSelected;
        fillDefaultTime();
    }

    private void fillDefaultTime() {
        availableTimes.add("10:00");
        availableTimes.add("11:00");
        availableTimes.add("12:00");
        availableTimes.add("13:00");
        availableTimes.add("14:00");
        availableTimes.add("15:00");
        availableTimes.add("16:00");
        availableTimes.add("17:00");
        availableTimes.add("18:00");
    }

    public void updateTimeList(ArrayList<String> list) {
        availableTimes.clear();
        availableTimes.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceTimeAdapter.AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppointmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.service_time_row, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceTimeAdapter.AppointmentViewHolder holder, int position) {
        holder.textView.setText(availableTimes.get(position));
    }

    @Override
    public int getItemCount() {
        return availableTimes.size();
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public AppointmentViewHolder(@NonNull View itemView, Context mContext) {
            super(itemView);

            textView = itemView.findViewById(R.id.service_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSelectedTime = availableTimes.get(getAdapterPosition());
                    onTimeSelected.onSelected(mSelectedTime);
                }
            });
        }
    }


    public interface OnTimeSelected {
        void onSelected(String time);
    }
}
