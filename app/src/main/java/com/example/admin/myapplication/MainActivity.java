package com.example.admin.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.animation.FlingAnimation;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.myapplication.Adapter.CustomAdapter;
import com.example.admin.myapplication.Listners.ConfigureDarkTheme;
import com.example.admin.myapplication.Model.SplashModel;
import com.example.admin.myapplication.RecyclerViewClasses.PageScrollListner;
import com.example.admin.myapplication.Utils.CommonUtils;
import com.example.admin.myapplication.Utils.Constants;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,ConfigureDarkTheme{
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
    ImageView no_wifi_1,settings_icon;
    ImageView toggle_drawer;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ArrayList<View> mMenuItems = new ArrayList<>(3);
    CoordinatorLayout mailLayout,mainLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView title;



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
        if(CommonUtils.getThemePreference(this))
        {
            refreshLayout.setColorSchemeResources(R.color.materialBlack);
            refreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.materialGrey));
            if(recyclerView != null)
            {
                recyclerView.setBackgroundColor(Color.parseColor(Constants.MATERIAL_BLACK));
                collapsingToolbarLayout.setBackgroundColor(Color.parseColor(Constants.TOOL_BAR_COLOR_DARK));
                title.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
                settings_icon.setColorFilter(Color.argb(255,255,255,255));

            }

        }
        else
        {
            refreshLayout.setColorSchemeResources(R.color.materialGrey);
            refreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.materialBlack));
            if(recyclerView != null)
            {
                recyclerView.setBackgroundColor(Color.WHITE);
                collapsingToolbarLayout.setBackgroundColor(Color.parseColor(Constants.MATERIAL_GGREY));
                title.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
                settings_icon.setColorFilter(Color.argb(0,0,0,0));

            }

        }

    }

    public void init() {


        mainLayout = (CoordinatorLayout)findViewById(R.id.mainLayout);

        CommonUtils.getTheme(this,this);
        Setting.theme = this;

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.mainscreen_collapsing_toolbar);
        title = (TextView)findViewById(R.id.mainscreen_title);


        settings_icon=(ImageView)findViewById(R.id.setting_icon_main);
        settings_icon.setOnClickListener(this);

        mailLayout = (CoordinatorLayout)findViewById(R.id.coordinator_layout);

        no_wifi_1 = (ImageView) findViewById(R.id.no_internert);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setOnRefreshListener(this);
        if(CommonUtils.getThemePreference(this))
        {
            refreshLayout.setColorSchemeResources(R.color.materialBlack);
            refreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.materialGrey));
        }
        else
        {
            refreshLayout.setColorSchemeResources(R.color.materialGrey);
            refreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.materialBlack));
        }
        animationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(this, 2);
        if (isTablet(this)) {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        } else {
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
        adapter = new CustomAdapter(list, MainActivity.this,drawerLayout,mailLayout);
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
            case R.id.setting_icon_main:
                Intent i=new Intent(this,Setting.class);
                startActivity(i);
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
    public void isDark(boolean isDark) {
        if(isDark)
        {
            if(recyclerView != null)
            {
                recyclerView.setBackgroundColor(Color.parseColor(Constants.MATERIAL_BLACK));
                collapsingToolbarLayout.setBackgroundColor(Color.parseColor(Constants.TOOL_BAR_COLOR_DARK));
                title.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
                settings_icon.setColorFilter(Color.argb(255,255,255,255));

            }

        }
        else
        {
            if(recyclerView != null)
            {
                recyclerView.setBackgroundColor(Color.WHITE);
                collapsingToolbarLayout.setBackgroundColor(Color.parseColor(Constants.MATERIAL_GGREY));
                title.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
                settings_icon.setColorFilter(Color.argb(0,0,0,0));

            }

        }
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


    public static boolean isTablet(Context ctx) {
        return (ctx.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


}
