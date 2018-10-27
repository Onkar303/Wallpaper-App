package com.example.admin.myapplication;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.myapplication.Adapter.ProfileCustomAdapter;
import com.example.admin.myapplication.Model.SplashModel;
import com.example.admin.myapplication.RecyclerViewClasses.PageScrollListner;
import com.example.admin.myapplication.Utils.ProfileBottomSheetCallBack;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    ProfileBottomSheetCallBack callback;
    SplashModel splashModel;
    TextView name, bio, totla_photos, total_likes;
    CircleImageView profileImage;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    List<SplashModel> list;
    SwipeRefreshLayout swipeRefreshLayout;
    ProfileCustomAdapter adapter;
    NestedScrollView nestedScrollView;
    BottomSheetBehavior bottomSheetBehavior;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton floatingActionButton;
    LayoutAnimationController controller;
    RelativeLayout bottom_sheet_dragger;
    ImageView nowifi;
    Button showAllPhotos;
    GridLayoutManager gridLayoutManager;
    PageScrollListner listner;
    LinearLayout linearLayout;
    boolean isLoading;
    int pagenumber = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        init();
        getData();
    }

    public void init() {
        //swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.profile_swiperefreshlayout);
        // nestedScrollView=(NestedScrollView)findViewById(R.id.nestedscrollview_bottomsheet);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        bottomSheetBehavior = BottomSheetBehavior.from(coordinatorLayout);
        bottomSheetBehavior.setPeekHeight(0);
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        linearLayout = (LinearLayout) findViewById(R.id.profile_container);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.profile_coordinator);

        nowifi = (ImageView) findViewById(R.id.bottom_sheet_nowifi);


        showAllPhotos = (Button) findViewById(R.id.all_profile_photos);
        showAllPhotos.setOnClickListener(this);

        totla_photos = (TextView) findViewById(R.id.total_photos);
        total_likes = (TextView) findViewById(R.id.total_likes_text);

//        bottom_sheet_dragger = (RelativeLayout) findViewById(R.id.bottom_sheet_dragger);
//        bottom_sheet_dragger.setOnClickListener(this);

//        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_refreshbutton);
//        floatingActionButton.setOnClickListener(this);

        //floatingActionButton.setBackgroundColor(Color.parseColor(splashModel.getColor()));

        list = new ArrayList<>();
        name = (TextView) findViewById(R.id.profilename);
        bio = (TextView) findViewById(R.id.bio);
        profileImage = (CircleImageView) findViewById(R.id.user_image);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_profile);
        gridLayoutManager = new GridLayoutManager(this, 2);
        if (isTablet(this)) {

            staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            //linearLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(300,CoordinatorLayout.LayoutParams.WRAP_CONTENT));
        } else {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }


        listner = new PageScrollListner(staggeredGridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                pagenumber++;
                new MyAsyncTask(pagenumber).execute();
                // swipeRefreshLayout.setRefreshing(true);


            }

            @Override
            public int getTotalPageCount() {
                return (caculateTotalpages(splashModel.getUser().getTotalPhotos()));
            }

            @Override
            public boolean isLastPage() {
                if (pagenumber == getTotalPageCount()) {
                    return true;
                } else {
                    return false;
                }

            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        };

        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        adapter = new ProfileCustomAdapter(list, this);
        controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(listner);


        //setting bottom sheet callback
        callback = new ProfileBottomSheetCallBack(list, this, adapter);
        bottomSheetBehavior.setBottomSheetCallback(callback);


    }

    public void getData() {
        if (getIntent().hasExtra("myobject")) {
            splashModel = (SplashModel) getIntent().getSerializableExtra("myobject");
        }
        name.setText(splashModel.getUser().getName());
        if (String.valueOf(splashModel.getUser().getBio()).equals("null")) {
            bio.setText("No Bio");
        } else {
            bio.setText(String.valueOf(splashModel.getUser().getBio()));
        }


        totla_photos.setText(String.valueOf(splashModel.getUser().getTotalPhotos()));
        total_likes.setText(String.valueOf(splashModel.getUser().getTotalLikes()));
        Glide.with(this).load(splashModel.getUser().getProfileImage().getLarge()).thumbnail(0.2f).into(profileImage);
        profileImage.setBorderColor(Color.parseColor(splashModel.getColor()));

        //coordinatorLayout.setBackgroundColor(Color.parseColor(splashModel.getColor()));

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_refreshbutton:
                BottomSheetChangeFunction();
                break;

            case R.id.all_profile_photos:
                BottomSheetChangeFunction();
                break;

        }
    }


    public class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        Request request;
        Response response;
        OkHttpClient client;
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
                    .url("https://api.unsplash.com/users/" + splashModel.getUser().getUsername() + "/photos?client_id=197cb5c51cab8fdca1fe72c8898f9ca97325bb961fa0d59b2dae0e2a92d0f7ca&page=" + i)
                    .build();

            String s = null;

            try {

                response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    s = response.body().string();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            Gson gson = new Gson();
            JSONArray array = null;


            try {
                array = new JSONArray(s);
            } catch (Exception e) {
                e.printStackTrace();
            }

//            if (!list.isEmpty()) {
//                list.clear();
//            }

            for (int i = 0; i < array.length(); i++) {
                try {

                    list.add(gson.fromJson(array.get(i).toString(), SplashModel.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            s = null;
            array = null;
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            adapter.notifyDataSetChanged();
            // swipeRefreshLayout.setRefreshing(false);
            //recyclerView.setLayoutAnimation(controller);
            isLoading = false;

        }
    }


    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    public boolean getConnectionStatus() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            if (activeNetworkInfo.isConnected()) {
                return true;
            }

        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public void BottomSheetChangeFunction() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_DRAGGING) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            if (getConnectionStatus()) {
                nowifi.setVisibility(View.INVISIBLE);
                pagenumber = 1;
                new MyAsyncTask(pagenumber).execute();
                //swipeRefreshLayout.setRefreshing(true);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                nowifi.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }
    }


    public int caculateTotalpages(int number_photos) {
        return ((number_photos / 10) + 1);
    }

    public static boolean isTablet(Context ctx) {
        return (ctx.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
