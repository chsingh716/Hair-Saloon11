package uk.ac.tees.S6145076.HairSaloon.admin_page;

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

public class timeAdapter extends RecyclerView.Adapter<timeAdapter.AppointmentViewHolder> {

    final Context mContext;

    private List<String> availableTimes = new ArrayList<>();
    private String mSelectedTime = "";

    public timeAdapter(Context context) {
        this.mContext = context;
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
    public timeAdapter.AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppointmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.service_time_row, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull timeAdapter.AppointmentViewHolder holder, int position) {
        holder.textView.setText(availableTimes.get(position));
    }

    @Override
    public int getItemCount() {
        return availableTimes.size();
    }

    public String getSelectedTime() {
        return mSelectedTime;
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public AppointmentViewHolder(@NonNull View itemView, Context mContext) {
            super(itemView);

            textView = itemView.findViewById(R.id.service_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!availableTimes.get(getAdapterPosition()).equalsIgnoreCase(mSelectedTime)) {
                        mSelectedTime = availableTimes.get(getAdapterPosition());
                        textView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    }
                }
            });
        }
    }
}
