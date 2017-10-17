package com.dalujos.matematicaapp.Adapter;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.Button;

/**
 * Created by JoseFernando on 11/06/2017.
 */
public class Estilos {

    private static String font_path = "font/LindenHill-Regular.ttf";
    AssetManager getAsset;

    public  Estilos(AssetManager getAssetO){
        getAsset = getAssetO;
    }

    public void estiloBotones(Button boton){
        Typeface TF = Typeface.createFromAsset(getAsset,font_path);
        boton.setTypeface(TF, Typeface.BOLD);
    }
}
