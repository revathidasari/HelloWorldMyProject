package com.example.helloworldgrpc.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.helloworldgrpc.databinding.FragmentHomeBinding;
import com.example.helloworldgrpc.ui.home.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager packageManager = getContext().getPackageManager();
                Intent name = packageManager.getLaunchIntentForPackage("com.ola.mfgapp");
                Integer state = packageManager.getApplicationEnabledSetting("com.ola.mfgapp");
                android.util.Log.d("revathi","enabled " + packageManager.getApplicationEnabledSetting("com.ola.mfgapp"));
                if ( state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || state == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT) {
                    startActivity(new Intent(name));
                } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Package Not found");
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // packageManager.setApplicationEnabledSetting("com.ola.mfgapp",PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
                                try {
                                   // Runtime.getRuntime().exec("pm enable com.ola.mfgapp");
                                    packageManager.setApplicationEnabledSetting("com.ola.mfgapp",
                                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,0);
                                /* getContext().getPackageManager().setComponentEnabledSetting(new ComponentName("com.ola.mfgapp","com.ola.mfgapp.MainActivity"),
                                         PackageManager.COMPONENT_ENABLED_STATE_ENABLED,0);*/
                                    Toast.makeText(getContext(), "enabled pkg"+state, Toast.LENGTH_SHORT).show();
                                   // startActivity(packageManager.getLaunchIntentForPackage("com.ola.mfgapp"));
                              Intent newIntent = new Intent();
                              newIntent.setPackage("com.ola.mfgapp");
                                    startActivity(newIntent);
                                } catch (Exception ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                        });
                        builder.show();
                    }
                }
            });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}