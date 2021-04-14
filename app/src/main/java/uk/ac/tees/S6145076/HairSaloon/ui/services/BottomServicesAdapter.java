package uk.ac.tees.S6145076.HairSaloon.ui.services;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import uk.ac.tees.S6145076.HairSaloon.ServiceActivity;
import uk.ac.tees.S6145076.HairSaloon.R;

class BottomServicesAdapter extends RecyclerView.Adapter<BottomServicesAdapter.AppointmentViewHolder> {

    final Context mContext;

    public BottomServicesAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public BottomServicesAdapter.AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppointmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_bottom_row, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomServicesAdapter.AppointmentViewHolder holder, int position) {

        if (position == AllServicesFragment.ServiceType
                .LASHES.getPosition()) {
            holder.imageView.setBackgroundResource(R.drawable.lashes);
            holder.textView.setText("Lashes");
        } else if (position == AllServicesFragment.ServiceType
                .WAXING.getPosition()) {
            holder.imageView.setBackgroundResource(R.drawable.waxing);
            holder.textView.setText("Waxing");
        } else if (position == AllServicesFragment.ServiceType
                .NAILS.getPosition()) {
            holder.imageView.setBackgroundResource(R.drawable.nails);
            holder.textView.setText("Nails");
        } else if (position == AllServicesFragment.ServiceType
                .FACIALS.getPosition()) {
            holder.imageView.setBackgroundResource(R.drawable.facials);
            holder.textView.setText("Facials");
        } else if (position == AllServicesFragment.ServiceType
                .BROWNS.getPosition()) {
            holder.imageView.setBackgroundResource(R.drawable.brows);
            holder.textView.setText("Brows");
        } else {
            holder.imageView.setBackgroundResource(R.drawable.pedicure);
            holder.textView.setText("Pedicure");
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public AppointmentViewHolder(@NonNull View itemView, Context mContext) {
            super(itemView);

            imageView = itemView.findViewById(R.id.bottom_service_image);
            textView = itemView.findViewById(R.id.bottom_service_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ServiceActivity.class);
                    intent.putExtra(ServiceActivity.SERVICE_TYPE, AllServicesFragment.ServiceType.getType(getAdapterPosition()));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
