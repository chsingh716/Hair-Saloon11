package uk.ac.tees.S6145076.HairSaloon.ui.appointments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import uk.ac.tees.S6145076.HairSaloon.R;

public class AppointmentsFragment extends Fragment {

    private AppointmentsViewModel appointmentsViewModel;
    private AppointmentAdapter appointmentAdapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        appointmentsViewModel =
                new ViewModelProvider(this).get(AppointmentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_appointments, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.appointments_recycler_view);
        appointmentAdapter = new AppointmentAdapter(getActivity());
        recyclerView.setAdapter(appointmentAdapter);

    }
}
