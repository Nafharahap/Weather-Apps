package com.mobcom.weatherapps;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mobcom.weatherapps.Adapter.HourAdapter;
import com.mobcom.weatherapps.Model.ForecastDay;
import com.mobcom.weatherapps.Model.Hour;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    TextView tv_detail_date, tv_detail_maxtemp, tv_detail_mintemp, tv_detail_avgtemp, tv_detail_wind, tv_detail_precip, tv_detail_condition;
    ImageView img;

    private ArrayList<Hour> listhour = new ArrayList<>();
    private RecyclerView recyclerView;
    private HourAdapter hourAdapter;
    private RecyclerView.LayoutManager layoutManager;

    SwipeRefreshLayout swipeLayout;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //initialize swipe refresh layout
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout1);
        linearLayout = (LinearLayout) findViewById(R.id.swipe_refresh1);

        int color1 = getResources().getColor(R.color.magenta);
        int color2 = getResources().getColor(R.color.pink);


        swipeLayout.setColorSchemeColors(color1,color2);

        ForecastDay forecastDay = getIntent().getParcelableExtra("Forecastday");

        tv_detail_date = findViewById(R.id.detail_date);
        tv_detail_maxtemp = findViewById(R.id.detail_maxtemp);
        tv_detail_mintemp = findViewById(R.id.detail_mintemp);
        tv_detail_avgtemp = findViewById(R.id.detail_avgtemp);
        tv_detail_wind = findViewById(R.id.detail_wind);
        tv_detail_precip = findViewById(R.id.detail_precip);
        tv_detail_condition = findViewById(R.id.detail_condition);

        img =findViewById(R.id.img_detail);

        recyclerView = findViewById(R.id.hour_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        tv_detail_date.setText(forecastDay.getDate());
        tv_detail_maxtemp.setText("Max Temp: " + Double.toString(forecastDay.getDay().getMaxtemp_c()) + " °C");
        tv_detail_mintemp.setText("Min Temp: " + Double.toString(forecastDay.getDay().getMintemp_c()) + " °C");
        tv_detail_avgtemp.setText("Avg Temp: " + Double.toString(forecastDay.getDay().getAvgtemp_c()) + " °C");
        tv_detail_wind.setText("Max Wind: " + Double.toString(forecastDay.getDay().getMaxwind_kph()) + " km/hour");
        tv_detail_precip.setText("Total Precip: " + Double.toString(forecastDay.getDay().getTotalprecip_mm()) + " mm");
        tv_detail_condition.setText(forecastDay.getDay().getCondition().getText());
        Picasso.get().load("https:" + forecastDay.getDay().getCondition().getIcon()).into(img);

        listhour = forecastDay.getHour();
        hourAdapter = new HourAdapter(listhour);
        recyclerView.setAdapter(hourAdapter);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        swipeLayout.setRefreshing(false);

                    }
                }, 2000);
            }
        });
    }
}