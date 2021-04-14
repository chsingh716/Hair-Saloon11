package uk.ac.tees.S6145076.HairSaloon;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;
import uk.ac.tees.S6145076.HairSaloon.admin.appointments_activity;
import uk.mylibrary.UserModel;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    public static final String VERIFICATION_ID = "id";
    private static final String TAG = BaseActivity.class.getName();
    public static final String USER = "user";
    public static final String ADMIN = "admin";
    public static final String USER_TYPE = "type";
    UserModel userModel = new UserModel();

    MySharedPref mySharedPref;
    String userType;

    SpotsDialog waitingDialog;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private DatabaseReference databaseReference;
    private boolean mVerificationInProgress = false;
    public static final String USERS_DB = "Users";

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            mVerificationInProgress = false;
            // Update the UI and attempt sign in with the phone credential
//            showSnackBar("Verification Success");
//            signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            mVerificationInProgress = false;
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                showSnackBar("Invalid phone number.");
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                showSnackBar("Quota exceeded.");
            }
            showSnackBar("Verification Failed");
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
            mResendToken = token;
            showSnackBar("Code is sent to your mobile number");

            navigateToVerifyActivity();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());

        databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS_DB);
        mySharedPref = MySharedPref.getInstance(BaseActivity.this);

        userType = getIntent().getStringExtra(USER_TYPE);
        mAuth = FirebaseAuth.getInstance();
        waitingDialog = new SpotsDialog(this, R.style.waiting_dialog);

        initUI();

    }

    void navigateToMainActivity() {
        waitingDialog.dismiss();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    void navigateToAdminActivity() {
        waitingDialog.dismiss();
        Intent intent = new Intent(getActivity(), appointments_activity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToVerifyActivity() {
        //Intent intent = new Intent(getActivity(), VerifyActivity.class);
        //intent.putExtra(VERIFICATION_ID, mVerificationId);
        //intent.putExtra(USER_TYPE, userType);
        //startActivity(intent);
        //finish();
    }

    protected abstract Context getActivity();

    @Override
    public void onStart() {
        super.onStart();
      //  FirebaseUser currentUser = mAuth.getCurrentUser();
       // if (currentUser != null && !(getActivity() instanceof SignUpActivity)) {
         //   if (userType.equalsIgnoreCase(USER)) {
          //      navigateToMainActivity();
           // } else {
                navigateToAdminActivity();
            //}
        //}
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    void startPhoneNumberVerification(String phoneNumber) {
        if (isNetworkConnected(this)) {
            waitingDialog.show();
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(phoneNumber)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this)                 // Activity (for callback binding)
                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
            mVerificationInProgress = true;
        } else {
            showSnackBar(getString(R.string.no_internet_connection));
        }

    }

    void showSnackBar(String msg) {
        waitingDialog.dismiss();

        Snackbar.make(findViewById(android.R.id.content), msg,
                Snackbar.LENGTH_SHORT).show();
    }

    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    protected abstract void initUI();

    protected abstract int getLayoutRes();
}
