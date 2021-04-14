package uk.ac.tees.S6145076.HairSaloon.ui.services;

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

public class AllServicesFragment extends Fragment {

    private AllServicesViewModel allServicesViewModel;

    private BottomServicesAdapter bottomServicesAdapter;
    private TopServicesAdapter topServicesAdapter;
    private RecyclerView bottomRecyclerView;
    private RecyclerView topRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        allServicesViewModel =
                new ViewModelProvider(this).get(AllServicesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        allServicesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        topRecyclerView = view.findViewById(R.id.home_page_top_list);
        topServicesAdapter = new TopServicesAdapter(getActivity());
        topRecyclerView.setAdapter(topServicesAdapter);

        bottomRecyclerView = view.findViewById(R.id.home_page_bottom_list);
        bottomServicesAdapter = new BottomServicesAdapter(getActivity());
        bottomRecyclerView.setAdapter(bottomServicesAdapter);

    }

    public enum ServiceType {
        LASHES(0), WAXING(1), NAILS(2), FACIALS(3), BROWNS(4), PEDICURE(5);
        int position;

        ServiceType(int index) {
            position = index;
        }

        public static String getType(int position) {
            String type;
            if (position == AllServicesFragment.ServiceType
                    .LASHES.getPosition()) {
                type = "Lashes";
            } else if (position == AllServicesFragment.ServiceType
                    .WAXING.getPosition()) {
                type = "Waxing";
            } else if (position == AllServicesFragment.ServiceType
                    .NAILS.getPosition()) {
                type = "Nails";
            } else if (position == AllServicesFragment.ServiceType
                    .FACIALS.getPosition()) {
                type = "Facials";
            } else if (position == AllServicesFragment.ServiceType
                    .BROWNS.getPosition()) {
                type = "Browns";
            } else {
                type = "Pedicure";
            }
            return type;
        }

        int getPosition() {
            return position;
        }
    }
}
