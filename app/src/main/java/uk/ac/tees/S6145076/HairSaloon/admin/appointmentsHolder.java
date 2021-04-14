package uk.ac.tees.S6145076.HairSaloon.admin;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import uk.ac.tees.S6145076.HairSaloon.R;

public class appointmentsHolder extends RecyclerView.ViewHolder {

//    Button approve;
    public TextView name,userName, date_time;
    public ImageView styleImage, cancel;
    public CardView cardView;
    public appointmentsHolder(View itemView) {
        super(itemView);
//        reject = itemView.findViewById(R.id.admin_reject);
//        approve = itemView.findViewById(R.id.admin_approve);
        name = itemView.findViewById(R.id.service_name);
        userName = itemView.findViewById(R.id.user_name);
        date_time = itemView.findViewById(R.id.service_date);
        styleImage = itemView.findViewById(R.id.service_image);
        cardView =itemView.findViewById(R.id.adminCard1);
        cancel =itemView.findViewById(R.id.cancel);

    }
}