package com.example.divya.networkingone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by divya on 30/6/16.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    //TODO : why isnt there any access specifier for these variables?
    List<String> urls;

    Context      context;

    public ImageAdapter(List<String> urls, Context context) {
        this.urls = urls;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        //TODO: why this statement is necessary?
        holder.progressBar.setVisibility(View.VISIBLE);

        //TODO: never harcode any dimension values in code, always load from dimens.xml file
        int height = (int)context.getResources().getDimension(R.dimen.dimen_200dp);
        int width = (int)context.getResources().getDimension(R.dimen.dimen_400dp);

        Picasso.with(context)
                .load(urls.get(position))
                .resize(width, height)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        //show error
                    }
                });
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView   imageView;

        public ProgressBar progressBar;

        MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imgId);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }
}
