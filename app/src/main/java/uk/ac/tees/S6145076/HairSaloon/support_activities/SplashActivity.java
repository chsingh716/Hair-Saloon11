package uk.ac.tees.S6145076.HairSaloon.support_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import uk.ac.tees.S6145076.HairSaloon.R;
import uk.ac.tees.S6145076.HairSaloon.extraJava.control_hub;

//import static uk.ac.tees.S6145076.HairSaloon.BaseActivity.USER_TYPE;

public class SplashActivity extends AppCompatActivity {

    Button getStart;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
         fAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_splash111);
        getStart = findViewById(R.id.btn_getStart);
        getStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(SplashActivity.this, signUpActivity.class);

                startActivity(intent);
                finish();
            }
        });


        /* when app is started and app have user login details */
        if (fAuth.getCurrentUser() != null) {
            //Toast.makeText(getApplicationContext(),fAuth.getCurrentUser().toString(),Toast.LENGTH_LONG).show();
            //startActivity(new Intent(signUpActivity.this, MainActivity.class));
            new control_hub(SplashActivity.this).read("user");
            //finish();
        }

    }
}
