package com.mobcom.weatherapps;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.mobcom.weatherapps.Model.WeatherModel;
import com.mobcom.weatherapps.Retrofit.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirQualityActivity extends AppCompatActivity {

    TextView tv_co, tv_no2, tv_so2, tv_o3, tv_pm2_5, tv_pm10, tv_epa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_quality);

        tv_epa = findViewById(R.id.epa);
        tv_co = findViewById(R.id.co);
        tv_no2 = findViewById(R.id.no2);
        tv_o3 = findViewById(R.id.o3);
        tv_so2 = findViewById(R.id.so2);
        tv_pm2_5 = findViewById(R.id.pm2_5);
        tv_pm10 = findViewById(R.id.pm10);

        getCurentDataFromAPI();
    }

    private void getCurentDataFromAPI() {
        APIClient.endpoint().getCurrentData().enqueue(new Callback<WeatherModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                tv_epa.setText(Integer.toString(response.body().getCurrent().getAir_quality().getUs_epa_index()));
                tv_co.setText(Float.toString(response.body().getCurrent().getAir_quality().getCo()) + " μg/m\u00B3");
                tv_o3.setText(Float.toString(response.body().getCurrent().getAir_quality().getO3()) + " μg/m\u00B3");
                tv_no2.setText(Float.toString(response.body().getCurrent().getAir_quality().getNo2()) + " μg/m\u00B3");
                tv_so2.setText(Float.toString(response.body().getCurrent().getAir_quality().getSo2()) + " μg/m\u00B3");
                tv_pm2_5.setText(Float.toString(response.body().getCurrent().getAir_quality().getPm2_5()) + " μg/m\u00B3");
                tv_pm10.setText(Float.toString(response.body().getCurrent().getAir_quality().getPm10()) + " μg/m\u00B3");
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {

            }
        });
    }
}