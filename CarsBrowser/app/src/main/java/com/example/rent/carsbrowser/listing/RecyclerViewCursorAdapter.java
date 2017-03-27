package com.example.rent.carsbrowser.listing;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rent.carsbrowser.CarsTableContract;
import com.example.rent.carsbrowser.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RENT on 2017-03-27.
 */

public class RecyclerViewCursorAdapter extends RecyclerView.Adapter<RecyclerViewCursorAdapter.ViewHolder> {

    private Cursor cursor;

    @Override
    public RecyclerViewCursorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewCursorAdapter.ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        String imageURL = cursor.getString(cursor.getColumnIndex(CarsTableContract.COLUMN_IMAGE));
        String make = cursor.getString(cursor.getColumnIndex(CarsTableContract.COLUMN_MAKE));
        String model = cursor.getString(cursor.getColumnIndex(CarsTableContract.COLUMN_MODEL));
        int year = cursor.getInt(cursor.getColumnIndex(CarsTableContract.COLUMN_YEAR));

        holder.year.setText("Rocznik: " + year);
        holder.make_model.setText(make + " " + model);
        Glide.with(holder.imageView.getContext()).load(imageURL).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        TextView make_model;

        TextView year;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = ButterKnife.findById(itemView, R.id.image);
            make_model = ButterKnife.findById(itemView, R.id.make_model);
            year = ButterKnife.findById(itemView, R.id.year);
        }
    }
}
