package com.example.admin.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.animation.FlingAnimation;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.myapplication.Adapter.CustomAdapter;
import com.example.admin.myapplication.Model.SplashModel;
import com.example.admin.myapplication.RecyclerViewClasses.PageScrollListner;
import com.example.admin.myapplication.Utils.Constants;
import com.example.admin.myapplication.Utils.InternetBroadCastReceiver;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, InternetBroadCastReceiver.ConnectivityReceiverListener {
    RecyclerView recyclerView;
    List<Object> list;
    CustomAdapter adapter;
    LayoutAnimationController animationController;
    Button button;
    SwipeRefreshLayout refreshLayout;
    FlingAnimation flingAnimation;
    AppBarLayout appBarLayout;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    PageScrollListner pageScrollListner;
    boolean isLoading;
    int pagenumber = 1;
    Toolbar toolbar;
    ImageView no_wifi_1;
    ImageView toggle_drawer;
    NavigationView navigationView;
    DrawerLayout drawerLayout;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().setBackgroundDrawableResource(R.drawable.background);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void init() {

        toolbar = (Toolbar) findViewById(R.id.mainactivity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        navigationView = (NavigationView) findViewById(R.id.navigation_view_mainactivity);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);


        toggle_drawer = (ImageView) findViewById(R.id.toggle_drawer);
        toggle_drawer.setOnClickListener(this);


        no_wifi_1 = (ImageView) findViewById(R.id.no_internert);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(Color.WHITE);
        refreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorPrimaryDark));
        animationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(this, 2);
        if(isTablet(this))
        {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        }
        else
        {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }

        linearLayoutManager = new LinearLayoutManager(this);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        list = new ArrayList<>();
        pageScrollListner = new PageScrollListner(staggeredGridLayoutManager) {
            @Override
            protected void loadMoreItems() {

                if (isConnected()) {
                    isLoading = true;
                    pagenumber++;
                    new MyAsyncTask(pagenumber).execute();
                    refreshLayout.setRefreshing(true);

                } else {

                }

            }

            @Override
            public int getTotalPageCount() {
                return 100;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        };

        recyclerView.addOnScrollListener(pageScrollListner);

        adapter = new CustomAdapter(list, MainActivity.this);

        recyclerView.setAdapter(adapter);


        if (isConnected()) {
            no_wifi_1.setVisibility(View.GONE);
            refreshLayout.setRefreshing(true);
            pagenumber = 1;
            new MyAsyncTask(pagenumber).execute();
        } else {
            recyclerView.setVisibility(View.GONE);
            no_wifi_1.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toggle_drawer:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                break;
        }

    }

    @Override
    public void onRefresh() {

        if (isConnected()) {
            pagenumber = 1;
            list.clear();
            new MyAsyncTask(pagenumber).execute();
            refreshLayout.setRefreshing(true);
        } else {
            recyclerView.setVisibility(View.GONE);
            no_wifi_1.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        Toast.makeText(this, "is connected", Toast.LENGTH_SHORT).show();

    }


    public class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        OkHttpClient client;
        Request request;
        int i;

        MyAsyncTask(int i) {
            this.i = i;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            client = new OkHttpClient();

            request = new Request.Builder()
                    .url(Constants.DEFUALT_URL + "&page=" + i)
                    .build();

            Response response;
            String s = null;

            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    s = response.body().string();
                } else if (!response.isSuccessful()) {
                    MyAlertDialog("Faliure");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            JSONArray array = null;

//            if(!list.isEmpty())
//            {
//                list.clear();
//            }

            try {
                array = new JSONArray(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < array.length(); i++) {
                try {

                    list.add(gson.fromJson(array.get(i).toString(), SplashModel.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            array = null;
            s = null;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            refreshLayout.setRefreshing(false);
            adapter.notifyDataSetChanged();
            //recyclerView.setLayoutAnimation(animationController);
            isLoading = false;
        }
    }


    public void MyAlertDialog(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(name);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean isConnected() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo.isAvailable() && networkInfo.isConnected()) {
                return true;
            }
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
        return false;
    }


    public static boolean isTablet(Context ctx){
        return (ctx.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


}
