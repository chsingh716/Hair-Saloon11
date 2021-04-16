package uk.ac.tees.S6145076.HairSaloon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import uk.ac.tees.S6145076.HairSaloon.admin.appointments_activity;
import uk.ac.tees.S6145076.HairSaloon.extraJava.readFirebaseData;

public class SignInActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button signInButton;
    private TextView typeTextView;
    private String userType;
    //firebase
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    uk.ac.tees.S6145076.HairSaloon.extraJava.readFirebaseData readFirebaseData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in1);

        loginEmail = findViewById(R.id.sign_in_et_email);
        loginPassword = findViewById(R.id.sign_in_et_password);
        signInButton = findViewById(R.id.sign_in_button);
        typeTextView = findViewById(R.id.user_type);

        //initialize firebase database
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userType= getIntent().getStringExtra("userType");  //get data from parent activity
        typeTextView.setText(userType);

        readFirebaseData = new readFirebaseData(this,userType);

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
                  //  Toast.makeText(getApplicationContext(),"Successfully Login", Toast.LENGTH_SHORT).show();
                    //Hide ProgressBar///
//                    findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                    //login Successfully..
                    //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //intent.putExtra("userType", userType);
                    //startActivity(intent);
                    //finish();
                    String userId= fAuth.getCurrentUser().getUid();
                     readFirebaseData.read(fStore,userId);

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

}