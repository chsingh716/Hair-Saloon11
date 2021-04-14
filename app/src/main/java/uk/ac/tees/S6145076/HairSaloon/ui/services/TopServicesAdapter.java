package uk.ac.tees.S6145076.HairSaloon.ui.services;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import uk.ac.tees.S6145076.HairSaloon.ServiceActivity;
import uk.ac.tees.S6145076.HairSaloon.R;

class TopServicesAdapter extends RecyclerView.Adapter<TopServicesAdapter.TopServicesViewHolder> {


    final Context mContext;

    public TopServicesAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public TopServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopServicesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_top_row, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull TopServicesViewHolder holder, int position) {

        if (position == AllServicesFragment.ServiceType
                .LASHES.getPosition()) {
            holder.imageView.setBackgroundResource(R.drawable.lashes_wide);
        } else if (position == AllServicesFragment.ServiceType
                .WAXING.getPosition()) {
            holder.imageView.setBackgroundResource(R.drawable.waxing_wide);
        } else if (position == AllServicesFragment.ServiceType
                .NAILS.getPosition()) {
            holder.imageView.setBackgroundResource(R.drawable.nails_wide);
        } else {
            holder.imageView.setBackgroundResource(R.drawable.facials_wide);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public static class TopServicesViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public TopServicesViewHolder(@NonNull View itemView, Context mContext) {
            super(itemView);

            imageView = itemView.findViewById(R.id.top_service_image);
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
