package uk.ac.tees.S6145076.HairSaloon;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

public class SignInActivity extends BaseActivity {

    private EditText mobileEditText;
    private Button signInButton;
    private CountryCodePicker countryCodePicker;
    private TextView typeTextView;


    @Override
    protected Context getActivity() {
        return SignInActivity.this;
    }

    @Override
    protected void initUI() {

        mobileEditText = findViewById(R.id.sign_in_mobile);
        countryCodePicker = findViewById(R.id.ccp_sign_in);
        signInButton = findViewById(R.id.sign_in_button);
        typeTextView = findViewById(R.id.user_type);

        if (userType != null && userType.equalsIgnoreCase(USER)) {
            typeTextView.setText("USER LOGIN");
        } else {
            typeTextView.setText("ADMIN LOGIN");
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNumber = mobileEditText.getText().toString();
                if (mobileNumber.isEmpty()) {
                    showSnackBar(getString(R.string.enter_mobile_number));
                    return;
                }

                mobileNumber = countryCodePicker.getSelectedCountryCodeWithPlus() + mobileNumber;
                startPhoneNumberVerification(mobileNumber);

            }
        });
    }
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_sign_in1;
    }

}
