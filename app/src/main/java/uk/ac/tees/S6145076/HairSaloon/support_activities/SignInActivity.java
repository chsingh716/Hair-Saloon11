package uk.ac.tees.S6145076.HairSaloon.support_activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import uk.ac.tees.S6145076.HairSaloon.R;
import uk.ac.tees.S6145076.HairSaloon.extraJava.control_hub;

public class SignInActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button signInButton;
    private TextView typeTextView;
    private String userTypeBtn;
    //firebase
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    control_hub control_hub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in1);

        Toolbar toolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        // calling the action bar to show back button
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        loginEmail = findViewById(R.id.sign_in_et_email);
        loginPassword = findViewById(R.id.sign_in_et_password);
        signInButton = findViewById(R.id.sign_in_button);
        typeTextView = findViewById(R.id.user_type);

        //initialize firebase database
        fAuth = FirebaseAuth.getInstance();
        //fStore = FirebaseFirestore.getInstance();

        userTypeBtn= getIntent().getStringExtra("userType");  //get data from parent activity
        typeTextView.setText(userTypeBtn);

        control_hub = new control_hub(this);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = loginEmail.getText().toString().trim();
                String Password = loginPassword.getText().toString().trim();
                if (Email.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Enter valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Password.isEmpty() || Password.length() < 6) {
                    Toast.makeText(getApplicationContext(),"Enter valid password", Toast.LENGTH_SHORT).show();
                    return;
                }
              Login(Email,Password);

            }
        });
    }


//login using emmail and password
    void Login(String email,String password) {

        //start login process using firebase function ..
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                     control_hub.read(userTypeBtn);

                    //reset edit text fields..
                      loginEmail.setText("");
                       loginPassword.setText("");

                }
                else{
                    Toast.makeText(getApplicationContext(),"wrong username or password", Toast.LENGTH_SHORT).show();

                    //Hide ProgressBar///
                   // findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    /* back button on toolbar

     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

}