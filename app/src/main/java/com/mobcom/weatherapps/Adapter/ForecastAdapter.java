package com.mobcom.weatherapps.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobcom.weatherapps.Model.ForecastDay;
import com.mobcom.weatherapps.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ListViewHolder> {
    ArrayList<ForecastDay> listforecastday = new ArrayList<>();
    private OnForecastListener onforecastlistener;

    public ForecastAdapter(ArrayList<ForecastDay> listforecastday, OnForecastListener onforecastlistener) {
        this.listforecastday = listforecastday;
        this.onforecastlistener = onforecastlistener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_row, parent, false);
        ListViewHolder listViewHolder = new ListViewHolder(view, onforecastlistener);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ListViewHolder holder, int position) {
        holder.tv_date.setText(listforecastday.get(position).getDate());
        holder.tv_avgtemp.setText("Temp: " + Double.toString(listforecastday.get(position).getDay().getAvgtemp_c()) + "°C");
        Picasso.get().load("https:" + listforecastday.get(position).getDay().getCondition().getIcon()).into(holder.tv_forecasticon);
    }

    @Override
    public int getItemCount() {
        return listforecastday.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_date, tv_avgtemp;
        public ImageView tv_forecasticon;
        OnForecastListener onForecastListener;

        public ListViewHolder(@NonNull View itemView, OnForecastListener onForecastListener) {
            super(itemView);

            tv_date = itemView.findViewById(R.id.date);
            tv_avgtemp = itemView.findViewById(R.id.avgtemp);
            tv_forecasticon = (ImageView)itemView.findViewById(R.id.forecasticon);
            this.onForecastListener = onForecastListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onForecastListener.onForecastClick(getAdapterPosition());
        }
    }

    public interface OnForecastListener {
        void onForecastClick(int position);
    }
}
