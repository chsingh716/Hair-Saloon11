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

import uk.ac.tees.S6145076.HairSaloon.MainActivity;
import uk.ac.tees.S6145076.HairSaloon.R;

public class firebaseAuthentication extends AppCompatActivity {
    public FirebaseAuth fAuth12;
   public Context context;

    public firebaseAuthentication( Context cx) {
        this.context = cx;

        fAuth12 = FirebaseAuth.getInstance();  //initalise firebase
        //check isUser already login or not
       // if (fAuth12.getCurrentUser() != null) {
           startActivity(new Intent(cx, MainActivity.class));
            finish();
       // }
    }

    public void createUserAccount(String email, String password) {
        //create firebase user if above validation is correct ..
        fAuth12.createUserWithEmailAndPassword("singh345@gmail.com", "singj232").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //open MainActivity once user create account //
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    Toast.makeText(context, "Successfully Created User Account", Toast.LENGTH_SHORT).show();
                    // findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);  //hide progress Bar

                } else {
                    Toast.makeText(context, "Error to create account", Toast.LENGTH_SHORT).show();
                    // findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);  //hide progress Bar
                }

            }
        });
    }
}


