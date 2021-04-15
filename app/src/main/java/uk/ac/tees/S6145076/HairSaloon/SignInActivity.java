package uk.ac.tees.S6145076.HairSaloon;

import android.content.Context;
import android.content.Intent;
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
import com.hbb20.CountryCodePicker;

public class SignInActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button signInButton;
    private TextView typeTextView;

    //firebase
    FirebaseAuth fAuth;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        loginEmail = findViewById(R.id.sign_in_et_email);
        loginPassword = findViewById(R.id.sign_in_et_password);
        signInButton = findViewById(R.id.sign_in_button);
        typeTextView = findViewById(R.id.user_type);

        //initialize firebase database
        fAuth = FirebaseAuth.getInstance();

//        if (userType != null && userType.equalsIgnoreCase(USER)) {
//            typeTextView.setText("USER LOGIN");
//        } else {
//            typeTextView.setText("ADMIN LOGIN");
//        }

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
                    Toast.makeText(getApplicationContext(),"Successfully Logout", Toast.LENGTH_SHORT).show();
                    //Hide ProgressBar///
//                    findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                    //login Successfully..
                    startActivity(new Intent(getApplicationContext(),signUpActivity.class));
                    finish();
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