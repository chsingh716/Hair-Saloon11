package uk.ac.tees.S6145076.HairSaloon;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.hbb20.CountryCodePicker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import uk.mylibrary.UserModel;

import static uk.ac.tees.S6145076.HairSaloon.MySharedPref.IMAGE;

public class SignUpActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {


    public static final int MAP_REQUEST_CODE = 1111;
    public static final String USER_ADDRESS = "user_address";
    private static final int PERMISSION_REQUEST_LOCATION = 2222;
    private CircleImageView avatarImageView;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private TextView addressTextView;
    private EditText mobileEditText;
    private TextView dateOfBirthTextView;

    private Button submit;

    private boolean isLocationEnabled = false;
    private CountryCodePicker countryCodePicker;
    private SpotsDialog waitingDialog;
    private DatePickerDialog datePickerDialog;
    private String imageFile = "";
    private String provider = "";
    private String firstName;
    private String lastName;

    @Override
    protected Context getActivity() {
        return SignUpActivity.this;
    }

    @Override
    protected void initUI() {

        avatarImageView = findViewById(R.id.img_profile_avatar);
        firstNameEditText = findViewById(R.id.edit_first_name);
        lastNameEditText = findViewById(R.id.edit_last_name);
        emailEditText = findViewById(R.id.edit_email);
        addressTextView = findViewById(R.id.edit_address);
        mobileEditText = findViewById(R.id.edit_mobile);
        dateOfBirthTextView = findViewById(R.id.edit_date_birth);

        submit = findViewById(R.id.sign_up_button);

        countryCodePicker = findViewById(R.id.ccp_profile_info);
        initListeners();
        initDatePicker();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_sign_up;
    }

    private void initDatePicker() {
        Calendar now = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(
                SignUpActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
    }

    private void initListeners() {
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(SignUpActivity.this)
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFields();
            }
        });
    }

    private void checkFields() {
        firstName = firstNameEditText.getText().toString();
        lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String dateOfBirth = dateOfBirthTextView.getText().toString();
        String address = addressTextView.getText().toString();
        String mobileNumber = mobileEditText.getText().toString();

        if (imageFile.isEmpty()) {
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
        if (email.isEmpty()) {
            showMsg(getString(R.string.enter_email));
            return;
        }
        if (dateOfBirth.isEmpty()) {
            showMsg(getString(R.string.enter_date_birth));
            return;
        }
        if (mobileNumber.isEmpty()) {
            showMsg(getString(R.string.enter_mobile_number));
            return;
        }
        mobileNumber = countryCodePicker.getSelectedCountryCodeWithPlus() + mobileNumber;
        userModel = new UserModel("", firstName, lastName, imageFile, mobileNumber, email, address, dateOfBirth);
        userType = USER;
        startPhoneNumberVerification(mobileNumber);
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
                avatarImageView.setImageURI(fileUri);
                imageFile = ImagePicker.Companion.getFilePath(data);
                mySharedPref.saveValue(imageFile, IMAGE);
                mySharedPref.saveName((firstName + " " + lastName));
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
                builder.setTitle("Location Permission");
                builder.setMessage("Please grant the location permission");
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
            Intent intent = new Intent(SignUpActivity.this, MapsActivity.class);
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
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
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
