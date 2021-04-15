package uk.ac.tees.S6145076.HairSaloon;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import uk.ac.tees.S6145076.HairSaloon.extraJava.firebaseAuthentication;
import uk.mylibrary.UserModel;

import static uk.ac.tees.S6145076.HairSaloon.MySharedPref22.IMAGE;

public class signUpActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    public static final int MAP_REQUEST_CODE = 1111;
    public static final String USER_ADDRESS = "user_address";
    private static final int PERMISSION_REQUEST_LOCATION = 2222;
    private CircleImageView avatarImageView11;
    private EditText firstNameEditText,lastNameEditText, passwordEditText,emailEditText,mobileEditText;
    private TextView addressTextView,dateOfBirthTextView;

    private Button submit;

    private boolean isLocationEnabled = false;
    private CountryCodePicker countryCodePicker;
    private SpotsDialog waitingDialog;
    private DatePickerDialog datePickerDialog;
    private String imageFile1 = "";
    private String provider = "";
    MySharedPref22 mySharedPref22;
   public FirebaseAuth fAuth;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
    mySharedPref22 = MySharedPref22.getInstance(signUpActivity.this);

    fAuth = FirebaseAuth.getInstance();
    avatarImageView11 = findViewById(R.id.img_profile_avatar);
    firstNameEditText = findViewById(R.id.edit_firstname);
    lastNameEditText = findViewById(R.id.edit_lastname);
    passwordEditText = findViewById(R.id.edit_password);
    emailEditText = findViewById(R.id.edit_email);
    addressTextView = findViewById(R.id.edit_address);
    mobileEditText = findViewById(R.id.edit_mobile);
    dateOfBirthTextView = findViewById(R.id.edit_date_birth);

    submit = findViewById(R.id.sign_up_button);

    countryCodePicker = findViewById(R.id.ccp_profile_info);
    initListeners();
    initDatePicker();


    if (fAuth.getCurrentUser() != null) {
        Toast.makeText(getApplicationContext(),fAuth.getCurrentUser().toString(),Toast.LENGTH_LONG).show();
        startActivity(new Intent(signUpActivity.this, MainActivity.class));
        finish();
    }

    //normal user login page
    findViewById(R.id.tv_btnLogin).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
           intent.putExtra("userType", "user");
            startActivity(intent);
            //Toast.makeText(getApplicationContext(),"login",Toast.LENGTH_SHORT).show();
        }
    });

    //admin user login  // / /
    findViewById(R.id.tv_btnAdmin).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
            intent.putExtra("userType", "admin");
            startActivity(intent);
            //Toast.makeText(getApplicationContext(),"Admin",Toast.LENGTH_SHORT).show();;
        }
    });


}

    private void initDatePicker() {
        Calendar now = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(
                signUpActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
    }

    private void initListeners() {
        avatarImageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(signUpActivity.this)
                        .cropSquare()
                        .compress(300)
                        .maxResultSize(200, 200).start();
            }
        });

        addressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLocationPermission();
            }
        });

        dateOfBirthTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });

        /* when register button is clicked */
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAllFields();
            }
        });
    }

    private void checkAllFields() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = firstNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String _email = emailEditText.getText().toString().trim();
        String _dateOfBirth = dateOfBirthTextView.getText().toString();
        String _address = addressTextView.getText().toString();
        String _mobileNumber = mobileEditText.getText().toString().trim();

        if (imageFile1.isEmpty()) {
            showMsg(getString(R.string.enter_select_image));
            return;
        }

        if (firstName.isEmpty()) {
            showMsg(getString(R.string.enter_first_name));
            return;
        }
        if (lastName.isEmpty()) {
            showMsg(getString(R.string.enter_last_name));
            return;
        }
        if (password.isEmpty()) {
            showMsg("Please Enter Password");
            return;
        }
        if (_email.isEmpty()) {
            showMsg(getString(R.string.enter_email));
            return;
        }
        if (_dateOfBirth.isEmpty()) {
            showMsg(getString(R.string.enter_date_birth));
            return;
        }
        if (_mobileNumber.isEmpty()) {
            showMsg(getString(R.string.enter_mobile_number));
            return;
        }
        _mobileNumber = countryCodePicker.getSelectedCountryCodeWithPlus() + _mobileNumber;

            fAuth.createUserWithEmailAndPassword(_email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //open MainActivity once user create account //
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        Toast.makeText(getApplicationContext(), "Successfully Created User Account", Toast.LENGTH_SHORT).show();
                        // findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);  //hide progress Bar

                    } else {
                        Toast.makeText(getApplicationContext(), "Error to create account", Toast.LENGTH_SHORT).show();
                        // findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);  //hide progress Bar
                    }

                }
            });
}



    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && requestCode == MAP_REQUEST_CODE) {
            String address = data.getStringExtra(USER_ADDRESS);
            addressTextView.setText(address);
        } else {
            if (resultCode == RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                Uri fileUri = data.getData();
                avatarImageView11.setImageURI(fileUri);
                imageFile1 = ImagePicker.Companion.getFilePath(data);
               mySharedPref22.saveValue(imageFile1, IMAGE);
    //            mySharedPref22.saveName((firstName + " " + lastName));
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Location Permission  ..");
                builder.setMessage("Please grant the app to location permission");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                builder.show();
            } else {
                enableGPS();
            }
        }
    }

    private void enableGPS() {
        provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) {
            buildAlertMessageNoGps();
        } else {
            Intent intent = new Intent(signUpActivity.this, MapsActivity.class);
            startActivityForResult(intent, MAP_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableGPS();
                }
            }
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to enable gps ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
        dateOfBirthTextView.setText(date);
    }
}
