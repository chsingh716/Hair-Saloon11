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
    MySharedPref22 mySharedPref22;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public readFirebaseData(Context context) {
        this.context = context;
        mySharedPref22= MySharedPref22.getInstance(context);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

    }

    public void read(String userTypeBtn) {
        //userTypeBtn // means which button is pressed
        String userId= fAuth.getCurrentUser().getUid();
        DocumentReference db = fStore.collection("users")
                .document(userId);

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String first_name = document.getString("firstName");
                                mySharedPref22.saveName(first_name);
                                String userType = document.getString("userType");
                               // Toast.makeText(context, userType, Toast.LENGTH_SHORT).show();
                                if (userType.equalsIgnoreCase("Admin") && userTypeBtn.equalsIgnoreCase("Admin")) {
                                    context.startActivity(new Intent(context, appointments_activity.class));

                                } else if(userTypeBtn.equalsIgnoreCase("Admin") && !userType.equalsIgnoreCase("Admin")){
                                    Toast.makeText(context, "Sorry these login details are for normal user", Toast.LENGTH_SHORT).show();
                                    fAuth.signOut();  //signout  //
                                }

                                else {
                                    context.startActivity(new Intent(context, MainActivity.class));
                                }
                               // Toast.makeText(context, first_name, Toast.LENGTH_SHORT).show();


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