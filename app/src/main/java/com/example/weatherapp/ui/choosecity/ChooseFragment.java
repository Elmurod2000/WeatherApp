package com.example.weatherapp.ui.choosecity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.base.BaseFragment;
import com.example.weatherapp.databinding.FragmentChooseBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChooseFragment extends BaseFragment<FragmentChooseBinding> {

    public ChooseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected FragmentChooseBinding bind() {
        return FragmentChooseBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupListener() {
        binding.btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = binding.etCity.getText().toString();
                navController.navigate(ChooseFragmentDirections.actionChooseFragmentToWheatherFragment(s));
            }
        });
    }

    @Override
    protected void setupViews() {}

    @Override
    protected void callRequests() {}

    @Override
    protected void setupObserver() {}
}