package com.dalujos.matematicaapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalujos.matematicaapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MenuProfesorAdapter extends RecyclerView.Adapter<MenuProfesorAdapter.ViewHolder> {

    Context mContext;
    OnItemClickListener mItemClickListener;
    ArrayList<Menu> listData = new MenuDatos().placeListProfesor();

    public MenuProfesorAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_desplegable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Menu place = listData.get(position);

        holder.placeName.setText(place.name);
        Picasso.with(mContext).load(place.getImageResourceId(mContext)).into(holder.placeImage);

        String font_path = "font/LindenHill-Regular.ttf";
        Typeface TF = Typeface.createFromAsset(mContext.getAssets(),font_path);
        holder.placeName.setTypeface(TF, Typeface.BOLD);
        Bitmap photo = BitmapFactory.decodeResource(mContext.getResources(), place.getImageResourceId(mContext));

        Palette.generateAsync(photo, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                int mutedLight = palette.getMutedColor(mContext.getResources().getColor(android.R.color.black));
                holder.placeNameHolder.setBackgroundColor(mutedLight);
                float factor = 0.8f;
                holder.placeNameHolder.setAlpha(factor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return new MenuDatos().placeListProfesor().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout placeHolder;
        public LinearLayout placeNameHolder;
        public TextView placeName;
        public ImageView placeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placeName = (TextView) itemView.findViewById(R.id.placeName);
            placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
            placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
            placeHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}