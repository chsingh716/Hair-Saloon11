package uk.ac.tees.S6145076.HairSaloon.extraJava;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BroadcastReceiverForHeadset extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_HEADSET_PLUG .equals(intent.getAction())){
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    Toast.makeText(context,"Headset Disconnected",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(context,"Headset connected",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Log.d("TAG", "I have no idea what the headset state is");
            }
        }
    }
}
