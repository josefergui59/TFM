package com.dalujos.matematicaapp.CapaVista;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalujos.matematicaapp.Adapter.MenuDatos;
import com.dalujos.matematicaapp.Adapter.MenuProfesorAdapter;
import com.dalujos.matematicaapp.Modelo.MenuAdministradorModel;
import com.dalujos.matematicaapp.R;
import com.dalujos.matematicaapp.utils.MyRecyclerScroll;
import com.dalujos.matematicaapp.utils.Utils;

/**
 * Created by JoseFernando on 10/05/2017.
 */

public class MenuProfesor extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private MenuProfesorAdapter mAdapter;
    private boolean isListView;
    private Menu menu;
    private LinearLayout toolbarContainer;
    private Toolbar mToolbar;

    int toolbarHeight;

    String nivel;
    String subtitulo;
    Context context = this;

    private DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FrameLayout mContentFrame;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private int mCurrentSelectedPosition;

    private MenuAdministradorModel menuAdministradorModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);
        setContentView(R.layout.activity_menu_administrador);

        menuAdministradorModel = new MenuAdministradorModel();

        cargarTollBar();
        cargarNevView(savedInstanceState);
        cargarRecyclerView();
        menuAdministradorModel.inicializar(nivel, subtitulo);

    }

    private void cargarTollBar(){

        mToolbar = (Toolbar) findViewById(R.id.toolbarMen);
        String font_path = "font/LindenHill-Regular.ttf";
        Typeface TF = Typeface.createFromAsset(getAssets(),font_path);
        TextView toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(TF, Typeface.BOLD);
        toolbarContainer = (LinearLayout) findViewById(R.id.fabhide_toolbar_container);

    }
    private void cargarRecyclerView(){

        toolbarHeight= Utils.getToolbarHeight(this);

        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setPadding(mRecyclerView.getPaddingLeft(), toolbarHeight,
                mRecyclerView.getPaddingRight(), mRecyclerView.getPaddingBottom());
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);

        mRecyclerView.setHasFixedSize(true); //Data size is fixed - improves performance
        mAdapter = new MenuProfesorAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.addOnScrollListener(new MyRecyclerScroll() {
            @Override
            public void show() {
                toolbarContainer.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                toolbarContainer.animate().alpha(1).setInterpolator(new DecelerateInterpolator(1)).start();

            }

            @Override
            public void hide() {
                toolbarContainer.animate().translationY(-toolbarHeight).setInterpolator(new AccelerateInterpolator(2)).start();
                toolbarContainer.animate().alpha(0).setInterpolator(new AccelerateInterpolator(1)).start();

            }
        });
        isListView = true;
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbarMen);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(menuAdministradorModel.getStateSelectedPosition(), mCurrentSelectedPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentSelectedPosition = savedInstanceState.getInt(menuAdministradorModel.getStateSelectedPosition(), 0);
        Menu menu = mNavigationView.getMenu();
        menu.getItem(mCurrentSelectedPosition).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }
    public void cargarNevView(Bundle savedInstanceState){
        setUpToolbar();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer);

        mUserLearnedDrawer = Boolean.valueOf(readSharedSetting(this, menuAdministradorModel.getPrefUserLearnedDrawer(), "false", menuAdministradorModel.getPreferencesFile()));

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(menuAdministradorModel.getStateSelectedPosition());
            mFromSavedInstanceState = true;
        }

        setUpNavDrawer();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mContentFrame = (FrameLayout) findViewById(R.id.nav_contentframe);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_1:
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        final TextView et = new TextView(context);
                        String font_path = "font/LindenHill-Regular.ttf";
                        Typeface TF = Typeface.createFromAsset(getAssets(),font_path);
                        et.setTypeface(TF,  Typeface.NORMAL);
                        et.setTextSize(20f);
                        et.setText("Aplicación Profesor, ayuda a los estudiantes en sus problemas matemáticos!");

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(et);

                        // set dialog message
                        alertDialogBuilder.setCancelable(false).setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();
                        mCurrentSelectedPosition = 0;
                        return true;
                    case R.id.navigation_item_2:
                        Snackbar.make(mContentFrame, "Llamanos al 0992869066", Snackbar.LENGTH_SHORT).show();
                        mCurrentSelectedPosition = 1;
                        return true;
                    case R.id.navigation_item_3:
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);;
                        prefs.edit().putBoolean("register", false).commit();
                        Intent intent;
                        intent = new Intent(context, LogIn.class);
                        startActivity(intent);
                        finish();
                        return true;
                    default:
                        return true;
                }
            }
        });

    }



    private void abrirReto(int opcion){
        Intent i ;

        switch (opcion){
            case 0:
                i = new Intent(MenuProfesor.this, DetResolverEjerVista.class);
                break;
            case 1:
                i = new Intent(MenuProfesor.this, CambiarClaveVista.class);
                i.putExtra("Tipo", "Estudiante");
                break;
            default:
                i = new Intent(MenuProfesor.this, CambiarClaveVista.class);
                break;
        }
        startActivity(i);
    }

    MenuProfesorAdapter.OnItemClickListener onItemClickListener = new MenuProfesorAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {

            if(position == 3)
                nivel="null";
            else
                nivel=""+(position+1);
            subtitulo = MenuDatos.placeNameArrayProfesor[position];

            abrirReto(position);

        }

    };

    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_drawer);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }

        if (!mUserLearnedDrawer) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            mUserLearnedDrawer = true;
            saveSharedSetting(this, menuAdministradorModel.getPrefUserLearnedDrawer(), "true", menuAdministradorModel.getPreferencesFile());
        }

    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue, String PREFERENCES_FILE) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue, String PREFERENCES_FILE) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }


}
