package com.mobcom.weatherapps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobcom.weatherapps.Adapter.ForecastAdapter;
import com.mobcom.weatherapps.Model.ForecastDay;
import com.mobcom.weatherapps.Model.History;
import com.mobcom.weatherapps.Model.WeatherModel;
import com.mobcom.weatherapps.Retrofit.APIClient;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ForecastAdapter.OnForecastListener{

    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static MainActivity mainActivity;
    private static final String TAG = "MainActivity";

    //TextView current dan forecast
    TextView tv_location, tv_condition,tv_temp,tv_last_updated, tv_wind_kph, tv_pressure_mb, tv_precip_mm, tv_humidity, tv_cloud,tv_gust_kph;
    //TextView history
    TextView tv_history_temp, tv_history_condition;
    //TextView Air Quality
    TextView tv_epa;


    ImageView img, history_icon;

    //List data
    ArrayList<ForecastDay> listforecast = new ArrayList<>();
    ArrayList<ForecastDay> listhistory = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_location = findViewById(R.id.location);
        tv_condition = findViewById(R.id.condition);
        tv_temp = findViewById(R.id.temp);
        tv_last_updated = findViewById(R.id.last_updated);
        tv_cloud = findViewById(R.id.cloud);
        tv_gust_kph = findViewById(R.id.gust_kph);
        tv_humidity = findViewById(R.id.humidity);
        tv_precip_mm = findViewById(R.id.preci_mm);
        tv_pressure_mb = findViewById(R.id.pressure_mb);
        tv_wind_kph = findViewById(R.id.wind_kph);
        img = findViewById(R.id.img_weather);

        tv_epa = findViewById(R.id.btn_aqi);

        tv_history_temp = findViewById(R.id.history_temp);
        tv_history_condition = findViewById(R.id.history_condition);
        history_icon = findViewById(R.id.history_icon);

        recyclerView = findViewById(R.id.forecastlist);
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        mainActivity = this;
        getDataFromAPI();
        getHistoryDataFromAPI();
    }

    private void getDataFromAPI () {
        APIClient.endpoint().getData()
                .enqueue(new Callback<WeatherModel>() {
                    @Override
                    public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                        String day = response.body().getCurrent().getLast_updated();
                        SimpleDateFormat fromlast_updated = new SimpleDateFormat("yyyy-MM-dd:mm");
                        SimpleDateFormat Formatter = new SimpleDateFormat("EEEE");
                        SimpleDateFormat Formatter1 = new SimpleDateFormat("HH:mm");
                        String dateFormat = null;
                        String dateFormat1 = null;
                        try {
                            dateFormat = Formatter.format(fromlast_updated.parse(day));
                            dateFormat1 = Formatter1.format(fromlast_updated.parse(day));
                        } catch (ParseException e){
                            e.printStackTrace();
                        }
                        tv_location.setText(response.body().getLocation().getName());
                        tv_condition.setText(response.body().getCurrent().getCondition().getText());
                        double temp = response.body().getCurrent().getTemp_c();
                        tv_temp.setText((int) temp + "°C");
                        tv_cloud.setText(response.body().getCurrent().getCloud() +" %");
                        tv_gust_kph.setText(response.body().getCurrent().getGust_kph() + " km/hour");
                        tv_humidity.setText(response.body().getCurrent().getHumidity() + " %");
                        tv_last_updated.setText("Last Updated: " + response.body().getCurrent().getLast_updated());
                        tv_precip_mm.setText(response.body().getCurrent().getPrecip_mm() + " mm");
                        tv_pressure_mb.setText(response.body().getCurrent().getPressure_mb() + " mb");
                        tv_wind_kph.setText(response.body().getCurrent().getWind_kph() + " km/hour");

                        Picasso.get().load("https:" + response.body().getCurrent().getCondition().getIcon()).into(img);

                        listforecast = response.body().getForecast().getForecastday();
                        forecastAdapter = new ForecastAdapter(listforecast, mainActivity);
                        recyclerView.setAdapter(forecastAdapter);
                    }

                    @Override
                    public void onFailure(Call<WeatherModel> call, Throwable t) {

                    }
                });
    }

    private void getHistoryDataFromAPI() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String history = dateFormat.format(calendar.getTime());

        //Log.d(TAG, String.valueOf(dateFormat(calendar.getTime())));

        APIClient.endpoint().getHistoryData("history.json?key=5daa820d551d4c9083d164721212905&q=Jakarta&dt=" + history).enqueue(new Callback<History>() {
            @Override
            public void onResponse(Call<History> call, Response<History> response) {
                listhistory = response.body().getForecast().getForecastday();
                tv_history_temp.setText("Temp: " + Double.toString(listhistory.get(0).getDay().getAvgtemp_c()) + " °C");
                tv_history_condition.setText(listhistory.get(0).getDay().getCondition().getText());
                Picasso.get().load("https:" + listhistory.get(0).getDay().getCondition().getIcon()).into(history_icon);
            }

            @Override
            public void onFailure(Call<History> call, Throwable t) {

            }
        });
    }

    public void aqibutton(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_aqi:
                intent = new Intent(this, AirQualityActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
        startActivity(intent);
    }

    @Override
    public void onForecastClick(int position) {
        //Log.d(TAG, "onForecastClick: Terclick");

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("Forecastday", listforecast.get(position));
        startActivity(intent);
    }
}