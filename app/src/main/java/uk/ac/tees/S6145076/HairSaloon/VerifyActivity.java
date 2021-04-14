package uk.ac.tees.S6145076.HairSaloon;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.UUID;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import uk.ac.tees.S6145076.HairSaloon.admin.AdminRequest_activity;
import uk.mylibrary.AppDataBase;
import uk.mylibrary.UserModel;

public class VerifyActivity extends AppCompatActivity {

    private EditText ed_verify_number1;
    private EditText ed_verify_number2;
    private EditText ed_verify_number3;
    private EditText ed_verify_number4;
    private EditText ed_verify_number5;
    private EditText ed_verify_number6;
    UserModel userModel = new UserModel();

    private Button verifyButton;
    private FirebaseAuth mAuth;
    private SpotsDialog waitingDialog;
    private String userType;
    private MySharedPref mySharedPref;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        mySharedPref = MySharedPref.getInstance(VerifyActivity.this);

        userType = getIntent().getStringExtra(BaseActivity.USER_TYPE);
        mAuth = FirebaseAuth.getInstance();
        waitingDialog = new SpotsDialog(this, R.style.waiting_dialog);

        initUI();

    }

    void initUI() {
        mAuth = FirebaseAuth.getInstance();

        mVerificationId = getIntent().getStringExtra(BaseActivity.VERIFICATION_ID);
        userType = getIntent().getStringExtra(BaseActivity.USER_TYPE);

        ed_verify_number1 = findViewById(R.id.ed_verify_number1);
        ed_verify_number2 = findViewById(R.id.ed_verify_number2);
        ed_verify_number3 = findViewById(R.id.ed_verify_number3);
        ed_verify_number4 = findViewById(R.id.ed_verify_number4);
        ed_verify_number5 = findViewById(R.id.ed_verify_number5);
        ed_verify_number6 = findViewById(R.id.ed_verify_number6);


        verifyButton = findViewById(R.id.verify_button);
        verifyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onVerifyClick();
            }
        });
        initOTPView();
    }

    protected int getLayoutRes() {
        return R.layout.activity_verify;
    }

    private void onVerifyClick() {

        String verificationCode =
                ed_verify_number1.getText().toString() + ed_verify_number2.getText().toString() + ed_verify_number3.getText().toString() +
                        ed_verify_number4.getText().toString() + ed_verify_number5.getText().toString() + ed_verify_number6.getText().toString();

        if (verificationCode.length() < 6) {
            Toast.makeText(
                    VerifyActivity.this,
                    getString(R.string.error_complete_verification_code), Toast.LENGTH_LONG
            ).show();

            return;
        }
        waitingDialog.show();

        verifyPhoneNumberWithCode(mVerificationId, verificationCode);
    }

    private void initOTPView() {
        //GenericTextWatcher here works only for moving to next EditText when a number is entered
        //first parameter is the current EditText and second parameter is next EditText

      ed_verify_number1.addTextChangedListener(
                new GenericTextWatcher(
                        ed_verify_number1,
                        ed_verify_number2
                )
        );
        ed_verify_number2.addTextChangedListener(
                new GenericTextWatcher(
                        ed_verify_number2,
                        ed_verify_number3
                )
        );
        ed_verify_number3.addTextChangedListener(
                new GenericTextWatcher(
                        ed_verify_number3,
                        ed_verify_number4
                )
        );
        ed_verify_number4.addTextChangedListener(
                new GenericTextWatcher(
                        ed_verify_number4,
                        ed_verify_number5
                )
        );
        ed_verify_number5.addTextChangedListener(
                new GenericTextWatcher(
                        ed_verify_number5,
                        ed_verify_number6
                )
        );
        ed_verify_number6.addTextChangedListener(new GenericTextWatcher(ed_verify_number6, null));

        //GenericKeyEvent here works for deleting the element and to switch back to previous EditText
        //first parameter is the current EditText and second parameter is previous EditText
        ed_verify_number1.setOnKeyListener(new GenericKeyEvent(ed_verify_number1, null));
        ed_verify_number2.setOnKeyListener(new GenericKeyEvent(ed_verify_number2, ed_verify_number1));
        ed_verify_number3.setOnKeyListener(new GenericKeyEvent(ed_verify_number3, ed_verify_number2));
        ed_verify_number4.setOnKeyListener(new GenericKeyEvent(ed_verify_number4, ed_verify_number3));
        ed_verify_number5.setOnKeyListener(new GenericKeyEvent(ed_verify_number5, ed_verify_number4));
        ed_verify_number6.setOnKeyListener(new GenericKeyEvent(ed_verify_number6, ed_verify_number5));

    }

    class GenericKeyEvent implements View.OnKeyListener {
        private final EditText currentView;
        private final EditText previousView;

        GenericKeyEvent(EditText currentView, EditText previousView) {
            this.currentView = currentView;
            this.previousView = previousView;
        }

        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL
                    && currentView.getId() != R.id.ed_verify_number1 && currentView.getText().toString().isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView.setText("");
                previousView.requestFocus();
                return true;
            }
            return false;
        }
    }

    class GenericTextWatcher implements TextWatcher {
        private final View currentView;
        private final View nextView;

        GenericTextWatcher(View currentView, View nextView) {
            this.currentView = currentView;
            this.nextView = nextView;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (text.length() == 1 && nextView != null) nextView.requestFocus();
        }
    }

    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    void verifyPhoneNumberWithCode(String verificationId, String code) {
        if (isNetworkConnected(this)) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithPhoneAuthCredential(credential);
        } else {
            showSnackBar(getString(R.string.no_internet_connection));
        }
    }

    void showSnackBar(String msg) {
        waitingDialog.dismiss();

        Snackbar.make(findViewById(android.R.id.content), msg,
                Snackbar.LENGTH_SHORT).show();
    }

    void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        waitingDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();
//                            if ((getActivity() instanceof SignUpActivity)) {
                            updateProfile(user);
//                            } else {
//                                if (userType == ADMIN) {
//                                    navigateToAdminActivity();
//                                } else {
//                                    navigateToMainActivity();
//                                }
//                            }
                        } else {
                            waitingDialog.dismiss();
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                showSnackBar("Invalid code.");
                            }
                            showSnackBar("Sign in Failed");
                        }
                    }
                });
    }

    private void updateProfile(FirebaseUser user) {
        String uuid = UUID.randomUUID().toString();
        if (user != null) {
            uuid = user.getUid();
        }
        mySharedPref.saveID(uuid);

        if (userModel != null) {
            userModel.setUserId(uuid);

            AppDataBase.getInstance(this).getUserDao().insertUser(userModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSuccess, this::onError);
        }

    }

    private void onError(Throwable throwable) {

    }

    private void onSuccess(Long aLong) {
        if (userType.equalsIgnoreCase(BaseActivity.USER)) {
            navigateToMainActivity();
        } else {
            navigateToAdminActivity();
        }
    }

    void navigateToMainActivity() {
        waitingDialog.dismiss();
        Intent intent = new Intent(VerifyActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    void navigateToAdminActivity() {
        waitingDialog.dismiss();
        Intent intent = new Intent(VerifyActivity.this, AdminRequest_activity.class);
        startActivity(intent);
        finish();
    }
}
