package uk.ac.tees.S6145076.HairSaloon.main_ui.ContactUs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import uk.ac.tees.S6145076.HairSaloon.R;

public class ContactUsFragment extends Fragment {

    private ContactUsViewModel contactUsViewModel;

    private EditText name;
    private EditText mobile;
    private EditText message;
    private Button send;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactUsViewModel =
                new ViewModelProvider(this).get(ContactUsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contact_us, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.edit_first_name);
        mobile = view.findViewById(R.id.edit_mobile);
        message = view.findViewById(R.id.edit_message);
        send = view.findViewById(R.id.contact_us_button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.getText().toString().isEmpty() || mobile.getText().toString().isEmpty() ||
                        message.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill the data", Toast.LENGTH_LONG).show();
                } else {
                    showPopup();
                }
            }
        });
    }

    private void showPopup() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Message Sent");
        builder.setMessage("A member of staff will be in touch with you as soon as possible")
                .setCancelable(false)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }
}
