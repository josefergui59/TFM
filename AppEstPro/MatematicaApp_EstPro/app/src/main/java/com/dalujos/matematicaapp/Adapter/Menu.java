package com.dalujos.matematicaapp.Adapter;

/**
 * Created by JoseFernando on 11/06/2017.
 */
import android.content.Context;


public class Menu {

    public String id;
    public String name;
    public String imageName;
    public boolean isFav;

    public int getImageResourceId(Context context) {
        return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());
    }
}
