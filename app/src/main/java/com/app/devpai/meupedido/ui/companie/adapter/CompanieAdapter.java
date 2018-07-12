package com.app.devpai.meupedido.ui.companie.adapter;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.devpai.meupedido.R;
import com.app.devpai.meupedido.models.Companie;
import com.squareup.picasso.Picasso;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Lucas on 17/10/2017.
 */

public class CompanieAdapter extends RecyclerView.Adapter<CompanieAdapter.CompanieViewHolder> {

    private final Location mLocationUser;
    private List<Companie> companiesList;
    private Context mContext;

    public CompanieAdapter(List<Companie> companiesList, Location locationUser, Context context) {
        this.companiesList = companiesList;
        this.mContext = context;
        this.mLocationUser = locationUser;
    }

    public static class CompanieViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView name;
        public TextView hr;
        public TextView distance;
        public ImageView photo;

        public CompanieViewHolder(View view) {
            super(view);
            this.cv =       (CardView) view.findViewById(R.id.card_view_companie);
            this.name =     (TextView) view.findViewById(R.id.companie_name);
            this.hr =       (TextView) view.findViewById(R.id.companie_hr);
            this.distance = (TextView) view.findViewById(R.id.companie_distance);
            this.photo =    (ImageView) view.findViewById(R.id.companie_photo);
        }
    }

    @Override
    public CompanieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_companies, parent, false);
        return new CompanieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CompanieViewHolder holder, int position) {

        Companie companie = companiesList.get(position);

        Picasso.with(mContext).load(companie.getFoto()).placeholder(R.drawable.no_image_found).into(holder.photo);
        holder.name.setText(companie.getNome());

        if(isOpen(companie)) {
            holder.hr.setText("Aberto");
            holder.hr.setTextColor(Color.GREEN);
        } else {
            holder.hr.setText("Fechado");
            holder.hr.setTextColor(Color.RED);
        }

        holder.distance.setText(String.format("%.1f", getDistance(companie)) + " Km daqui");
    }

    @Override
    public int getItemCount() {
        return companiesList.size();
    }

    public float getDistance(Companie companie) {
        return 0;
    }

    private boolean isOpen(Companie companie) {

        SimpleDateFormat formatter = new SimpleDateFormat("h:mm");
        LocalDateTime date = LocalDateTime.now();

        System.out.println("Data atual: " + date);

        return false;
    }
}
