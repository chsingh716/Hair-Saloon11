package uk.ac.tees.S6145076.HairSaloon.extraJava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.StringTokenizer;

import uk.ac.tees.S6145076.HairSaloon.MainActivity;
import uk.ac.tees.S6145076.HairSaloon.MySharedPref22;
import uk.ac.tees.S6145076.HairSaloon.R;
import uk.ac.tees.S6145076.HairSaloon.admin.appointments_activity;

public class readFirebaseData extends AppCompatActivity {
    public Context context;
    String userTypeBtn;
    MySharedPref22 mySharedPref22;

    public readFirebaseData(Context context, String userType) {
        this.context = context;
        userTypeBtn=userType;
        mySharedPref22= MySharedPref22.getInstance(context);

    }

    public void read(FirebaseFirestore store, String userId) {


        DocumentReference db = store.collection("users")
                .document(userId);

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String first_name = document.getString("firstName");
                                String userType = document.getString("userType");
                                if (userType == "Admin" && userTypeBtn == "Admin") {
                                    context.startActivity(new Intent(context, appointments_activity.class));

                                } else {
                                    mySharedPref22.saveName(first_name);
                                    context.startActivity(new Intent(context, MainActivity.class));
                                }
                                Toast.makeText(context, first_name, Toast.LENGTH_SHORT).show();


                                // Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        }else {
                            Toast.makeText(context, "error. .. ", Toast.LENGTH_SHORT).show();
                        }
                        // Log.w(TAG, "Error getting documents.", task.getException());

                    }
                });
    }

}