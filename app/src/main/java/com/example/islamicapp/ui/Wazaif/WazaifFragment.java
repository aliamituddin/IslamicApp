package com.example.islamicapp.ui.Wazaif;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.islamicapp.Daily;
import com.example.islamicapp.Monthly;
import com.example.islamicapp.R;
import com.example.islamicapp.Yearly;

public class    WazaifFragment extends Fragment {
    Button btnDaily,btnMonthly,btnYearly;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btnDaily = root.findViewById(R.id.btnDaily);
        btnMonthly = root.findViewById(R.id.btnMonthly);
        btnYearly = root.findViewById(R.id.btnYearly);
        btnDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Daily dailyF = new Daily();
                FragmentManager manager =  getFragmentManager();
                assert manager != null;
                manager.beginTransaction().replace(R.id.nav_host_fragment,dailyF).addToBackStack(null)
                        .commit();
            }
        });
        btnMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Monthly monthlyF = new Monthly();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,monthlyF).addToBackStack(null)
                        .commit();
            }
        });

        btnYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Yearly yearlyF = new Yearly();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,yearlyF).addToBackStack(null)
                        .commit();
            }
        });
        return root;
    }



    }
