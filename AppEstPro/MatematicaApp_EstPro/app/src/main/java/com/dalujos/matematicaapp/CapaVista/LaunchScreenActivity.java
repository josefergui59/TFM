package com.dalujos.matematicaapp.CapaVista;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dalujos.matematicaapp.R;

public class LaunchScreenActivity extends AppCompatActivity {

    private static  int SPLASH_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Transparent Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_launch_screen);

        SPLASH_TIME = 3000; //milisegundos
        new BackgroundTask().execute();
    }
    public void fontText(){
        TextView t1 = (TextView)findViewById(R.id.textoGrande);
        TextView t2 = (TextView)findViewById(R.id.textoPeque);
        String font_path = "font/LindenHill-Regular.ttf";
        Typeface TF = Typeface.createFromAsset(this.getAssets(), font_path);
        t1.setTypeface(TF, Typeface.BOLD);
        t2.setTypeface(TF, Typeface.NORMAL);
    }


    private class BackgroundTask extends AsyncTask {
        Intent intent;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            intent = new Intent(LaunchScreenActivity.this, LogIn.class);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            /*  Use this method to load background
            * data that your app needs. */

            try {
                Thread.sleep(SPLASH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            startActivity(intent);
            finish();
        }
    }
}