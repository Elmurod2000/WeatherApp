package com.example.weatherapp.ui.weatherFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.base.BaseFragment;
import com.example.weatherapp.common.Resource;
import com.example.weatherapp.databinding.FragmentWheatherBinding;
import com.example.weatherapp.models.Root;
import com.example.weatherapp.repsitories.Repository;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WheatherFragment extends BaseFragment<FragmentWheatherBinding> {
    private WeatherViewModel viewModel;
    private WheatherFragmentArgs args;

    public WheatherFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args=WheatherFragmentArgs.fromBundle(getArguments());
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
    }

    @Override
    protected FragmentWheatherBinding bind() {
        return FragmentWheatherBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
    }

    @Override
    protected void callRequests() {
        viewModel.getWeatherByCityName(args.getArgs());
    }

    @Override
    protected void setupListener() {
        binding.textCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_wheatherFragment_to_chooseFragment);
            }
        });
    }

    @Override
    protected void setupObserver() {
        viewModel.liveData.observe(getViewLifecycleOwner(), new Observer<Resource<Root>>() {
            @Override
            public void onChanged(Resource<Root> rootResp) {
                switch (rootResp.status) {
                    case SUCCESS: {
                        binding.constraintlayout.setVisibility(View.VISIBLE);
                        binding.progress.setVisibility(View.GONE);
                        try {
                            setViews(rootResp.data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case ERROR: {
                        binding.constraintlayout.setVisibility(View.GONE);
                        binding.progress.setVisibility(View.GONE);
                        Snackbar.make(binding.getRoot(), rootResp.msc,
                                BaseTransientBottomBar.LENGTH_LONG)
                                .show();
                        break;
                    }
                    case LOADING: {
                        binding.constraintlayout.setVisibility(View.GONE);
                        binding.progress.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        });
    }

    int i = 0;

    private String getDate(long updatedAt, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(updatedAt + calendar.getTimeZone().getRawOffset());
        return format.format(calendar.getTime());
    }

    @SuppressLint("SetTextI18n")
    private void setViews(Root mainResponse) {
        long dt = mainResponse.getDt();
        long timezone = mainResponse.getTimezone();

        binding.textClock.setText(getDate((dt + timezone) * 1000L,
                "E, dd MMM yyyy | HH:mm a"));
        binding.textCity.setText(mainResponse.getName().toString() + ", "
                + mainResponse.getSys().getCountry().toString());

        String url = "http://openweathermap.org/img/wn/" + mainResponse.getWeather().get(0)
                .getIcon() + "@2x.png";

        Glide.with(binding.getRoot())
                .load(url)
                .centerCrop()
                .into(binding.ivCloud);
        binding.tvWeather.setText(mainResponse.getWeather().get(0).getMain());

        int temp= mainResponse.getMain().getTemp();
        int maxTemp = mainResponse.getMain().getTempMax();
        int minTemp = mainResponse.getMain().getTempMin();

        binding.tvTextemp.setText(String.valueOf(temp));
        binding.tvMaxTemp.setText(String.valueOf(maxTemp));
        binding.tvMinTemp.setText(String.valueOf(minTemp));

        String str = String.format("%s", mainResponse.getMain().getHumidity());
        binding.tvHumidityCifry.setText(str + "%");

        binding.textPressureCifry.setText(String.format("%s mBar", mainResponse.getMain().getPressure() / 1000f));

        binding.tvWindCifry.setText(String.format("%s km/h", mainResponse.getWind().getSpeed()));

        binding.tvSunriseCifry.setText(getDate((mainResponse.getSys().getSunrise()
                + timezone) * 1000L, "hh:mm a"));

        binding.tvSunsetCifry.setText(getDate((mainResponse.getSys().getSunset()
                + timezone) * 1000L, "hh:mm a"));

        long date3 = (mainResponse.getSys().getSunset() - mainResponse.getSys().getSunrise()) * 1000L;
        String rawFormat = getDate(date3, "HH m");
        String[] hours = rawFormat.split(" ");
        binding.tvDaytimeCifry.setText(hours[0] + "h " + hours[1] + "m");
    }

}