package com.example.admin.myapplication.Adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.ExceptionCatchingInputStream;
import com.example.admin.myapplication.Animations.MyAnimationUtils;
import com.example.admin.myapplication.ImageScreen;
import com.example.admin.myapplication.Model.Employee;
import com.example.admin.myapplication.Model.SplashModel;
import com.example.admin.myapplication.ProfileActivity;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Utils.CustomTextView;
import com.example.admin.myapplication.ViewPagerActivity;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Object> list;
    Context context;
    Bitmap bitmap;
    final int SPLASHMODEL_TYPE = 0;
    final int PROGRESSMODEL_TYPE = 1;


    public CustomAdapter(List<Object> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof SplashModel) {
            return SPLASHMODEL_TYPE;
        } else {
            return PROGRESSMODEL_TYPE;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == SPLASHMODEL_TYPE) {
            v = LayoutInflater.from(context).inflate(R.layout.recycleritem, parent, false);
            MyRecyclerItemViewHolder holder = new MyRecyclerItemViewHolder(v);
            return holder;
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.recycler_view_progress_bar, parent, false);
            MyRecyclerProgresViewHolder holder = new MyRecyclerProgresViewHolder(v);
            return holder;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {


        try {


            if (holder instanceof MyRecyclerItemViewHolder) {
                //MyAnimationUtils.Animate(holder,true);
                final MyRecyclerItemViewHolder myholder = (MyRecyclerItemViewHolder) holder;
                final SplashModel model = (SplashModel) list.get(position);
                myholder.title.setText(model.getUser().getFirstName());
                myholder.likes.setText(model.getUser().getTotalLikes() + " likes");
                RequestOptions requestOptions = new RequestOptions();


                Glide.with(context)
                        .load(model.getUrls().getSmall())
                        .apply(requestOptions.override(500, 500))
                        .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(myholder.imageView);

                Glide.with(context)
                        .load(model.getUser().getProfileImage().getSmall())
                        .apply(requestOptions.override(50, 50))
                        .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(myholder.ProfileImage);


                //MyAnimationUtils.Animate(myholder, true);
                myholder.ProfileImage.setBorderColor(Color.parseColor(model.getColor()));
                //holder.imageView.setMaxHeight(1000);
                //holder.relativeLayout.setBackgroundColor(Color.parseColor(list.get(position).getColor()));
                myholder.ProfileImage.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View view) {
                        Intent i1 = new Intent(context, ProfileActivity.class);
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation((Activity) context, myholder.ProfileImage, myholder.ProfileImage.getTransitionName());

                        i1.putExtra("myobject", model);
                        context.startActivity(i1, options.toBundle());
                    }
                });


                myholder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        ALertDialog(model.getUrls().getRegular());
                        return true;
                    }
                });

                // holder.recycleritemrelativelayout.setBackgroundColor(Color.parseColor(list.get(position).getColor()));
                //holder.cardView.setCardBackgroundColor(Color.parseColor(list.get(position).getColor()));

                //Glide.with(context).load(list.get(position).getUrls().getRegular().toString()).into(holder.imageView);
                myholder.imageView.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, ImageScreen.class);
                        i.putExtra("list", (Serializable) list);
                        i.putExtra("position", position);

//                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                            makeSceneTransitionAnimation((Activity) context, holder.imageView,holder.imageView.getTransitionName());
//                    context.startActivity(i,options.toBundle());

                        context.startActivity(i);


                    }
                });

            } else {

                MyRecyclerProgresViewHolder myholder = (MyRecyclerProgresViewHolder) holder;
                myholder.progressBar.setIndeterminate(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyRecyclerItemViewHolder extends RecyclerView.ViewHolder {

        CircleImageView ProfileImage;
        TextView title;
        TextView likes, id;
        ImageView imageView;
        View v;
        CardView cardView;
        LinearLayout linearLayout;
        RelativeLayout relativeLayout, recycleritemrelativelayout;


        public MyRecyclerItemViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            imageView = (ImageView) v.findViewById(R.id.imageView);
            likes = (TextView) v.findViewById(R.id.likes);
            title = (TextView) v.findViewById(R.id.title);
            ProfileImage = (CircleImageView) v.findViewById(R.id.user_profile_image);
            cardView = (CardView) v.findViewById(R.id.recycler_item_card);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.header_recycleritem);
        }

    }


    public class MyRecyclerProgresViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public MyRecyclerProgresViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.recycler_item_progress_bar);

        }
    }


    public void ALertDialog(String url) {
        try {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.longpress_image, null);
            ImageView imageView = (ImageView) v.findViewById(R.id.prodifile_image_longPress);
            Glide.with(context).load(url).into(imageView);
            android.support.v7.app.AlertDialog dialog = builder.create();
            dialog.setView(v);
            dialog.show();
            dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme;
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }

    }


}
