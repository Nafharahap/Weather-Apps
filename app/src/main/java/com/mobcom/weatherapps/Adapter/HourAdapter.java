package com.mobcom.weatherapps.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobcom.weatherapps.Model.Hour;
import com.mobcom.weatherapps.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.ListViewHolder>{
    ArrayList<Hour> listhour = new ArrayList<>();

    public HourAdapter(ArrayList<Hour> listhour) {
        this.listhour = listhour;

    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_row, parent, false);
        ListViewHolder listViewHolder = new ListViewHolder(view);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HourAdapter.ListViewHolder holder, int position) {
        holder.tv_hourtime.setText(listhour.get(position).getTime());
        holder.tv_hourtemp.setText(Double.toString(listhour.get(position).getTemp_c()) + " °C");
        holder.tv_hourcondition.setText(listhour.get(position).getCondition().getText());
        Picasso.get().load("https:" + listhour.get(position).getCondition().getIcon()).into(holder.img);
        
    }

    @Override
    public int getItemCount() {
        return listhour.size();
    }


    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_hourtime, tv_hourtemp, tv_hourcondition;
        ImageView img;


        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_hourtime = itemView.findViewById(R.id.hour_time);
            tv_hourtemp = itemView.findViewById(R.id.hour_temp);
            tv_hourcondition = itemView.findViewById(R.id.hour_condition);
            img = itemView.findViewById(R.id.img_hour);
        }
    }
}
