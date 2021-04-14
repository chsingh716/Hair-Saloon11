package uk.ac.tees.S6145076.HairSaloon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static uk.ac.tees.S6145076.HairSaloon.BaseActivity.USER_TYPE;

public class SplashActivity extends AppCompatActivity {

    Button signInButton;
    Button signUpButton;
    TextView adminTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                intent.putExtra(USER_TYPE, BaseActivity.USER);
                startActivity(intent);
                finish();
            }
        });

        signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        adminTextView = findViewById(R.id.admin_login_text_view);
        adminTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                intent.putExtra(USER_TYPE, BaseActivity.ADMIN);
                startActivity(intent);
                finish();
            }
        });
    }
}
