package com.example.divya.wallpaperapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.divya.wallpaperapp.Fragments.CompleteImageFragment;
import com.example.divya.wallpaperapp.R;
import com.example.divya.wallpaperapp.VolleyLibrary.RequestQueueServer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by divya on 21/7/16.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private List<String> urls;
    private List<String> largeUrls;
    private FragmentManager fragmentManager;
    private Context context;

    public ImageAdapter(List<String> urls, List<String> largeUrls, FragmentManager fragmentManager, Context context){
        this.urls = urls ;
        this.largeUrls = largeUrls;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String url = urls.get(position);
        holder.progressBar.setVisibility(View.VISIBLE);
        ImageLoader mImageLoader = RequestQueueServer.getInstance().getmImageLoader();
        holder.image.setImageUrl(url, mImageLoader);
        holder.progressBar.setVisibility(View.GONE);
       /* Picasso.with(context)
                .load(urls.get(position)).resize(100,100)
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError() {
                        //show error
                    }
                });*/
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Clicked","Image Clicked");
                CompleteImageFragment fragment = new CompleteImageFragment();
                Bundle bundle = new Bundle();
                bundle.putString(CompleteImageFragment.URL,url);
                bundle.putString(CompleteImageFragment.HIGH_URL, largeUrls.get(position));
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.fragment_container,
                        fragment).addToBackStack(null).commit();
            }
        });
    }

    public void dataSetChanged(List<String> urls, List<String> largeUrls){
        this.urls = urls;
        this.largeUrls = largeUrls;
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public NetworkImageView image;
        public ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (NetworkImageView)itemView.findViewById(R.id.imageViewItem);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progressBarId);
        }
    }
}
