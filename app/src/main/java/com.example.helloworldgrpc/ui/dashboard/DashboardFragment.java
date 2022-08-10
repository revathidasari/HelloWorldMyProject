package com.example.helloworldgrpc.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.helloworldgrpc.R;
import com.example.helloworldgrpc.databinding.FragmentDashboardBinding;
import com.example.helloworldgrpc.ui.dashboard.DashboardViewModel;

public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private Spinner spinnerAge, spinnerWeight;
    private TextView textC, textP, textV, textM;

    String[] age = {"0-15", "16-30", "31-45", "46-60", "60 and Above"};
    String[] weight = {"Below 40", "40-60", "60-80", "Above 80"};
    int taps =0;
    int positionAge, positionWeight;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        spinnerAge = binding.spinnerAge;
        spinnerWeight = binding.spinnerWeight;
        textC = binding.c;
        textP = binding.p;
        textV = binding.v;
        textM = binding.m;


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taps++;
                if (taps >= 7){
                    Toast.makeText(requireContext(),"tapped 7 or more", Toast.LENGTH_SHORT).show();
                }
            }
        });

        positionWeight = spinnerWeight.getSelectedItemPosition();

        ArrayAdapter ageAdapter = new ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, age);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(ageAdapter);
        spinnerAge.setSelection(1);
        spinnerAge.setOnItemSelectedListener(this);
        positionAge = spinnerAge.getSelectedItemPosition();


        ArrayAdapter weightAdapter = new ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, weight);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeight.setAdapter(weightAdapter);
        spinnerWeight.setOnItemSelectedListener(this);

        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (view.getId()) {
            case R.id.spinner_age:
                positionAge = i;
                break;
            case R.id.spinner_weight:
                positionWeight = i;
                break;
            default:
                break;
               // throw new IllegalStateException("Unexpected value: " + view.getId());
        }
/*        Toast.makeText(requireContext(), positionAge+"::"+i, Toast.LENGTH_SHORT).show();
        if (positionAge != i) {
            Toast.makeText(requireContext(), "item " + age[i], Toast.LENGTH_SHORT).show();
        }*/
textC.setText("..");
        if (positionAge<2 && positionWeight<2) {
            textC.append("50gms/day");
            textP.append("25gms/day");
            textV.append("A,B12,C,D per day");
            textM.append("Zinc per day");
        } else {
            textC.append("0gms/day");
            textP.append("0gms/day");
            textV.append("- per day");
            textM.append("- per day");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}