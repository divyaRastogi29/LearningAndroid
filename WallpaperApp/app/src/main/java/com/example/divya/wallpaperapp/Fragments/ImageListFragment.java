package com.example.divya.wallpaperapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.divya.wallpaperapp.Adapter.ImageAdapter;
import com.example.divya.wallpaperapp.R;
import com.example.divya.wallpaperapp.VolleyLibrary.RequestQueueServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by divya on 22/7/16.
 */
public class ImageListFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private List<String> urls;
    private List<String> largeUrls;
    private String nextPageUrl;
    private ProgressBar progressBar;
    private GridLayoutManager gridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_list_fragment,container,false);
        initViews(view);
        bindViews();
        populateImageUrls(nextPageUrl);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(gridLayoutManager.findLastVisibleItemPosition()==urls.size()-1)
                    populateImageUrls(nextPageUrl);
            }
        });
        return view;
    }

    private void bindViews() {
        adapter = new ImageAdapter(urls, largeUrls, getFragmentManager(), getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void initViews(View view) {
        urls = new ArrayList<>();
        largeUrls = new ArrayList<>();
        progressBar = (ProgressBar)view.findViewById(R.id.mainActivityProgressBar);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewId);
        nextPageUrl = getResources().getString(R.string.FirstPageUrl);
    }

    private void populateImageUrls(final String url) {
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getImagesOfPage(response);
                        progressBar.setVisibility(View.GONE);
                        adapter.dataSetChanged(urls,largeUrls);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(getResources().getString(R.string.Authorization),
                        getResources().getString(R.string.password));
                return params;
            }
        };
        RequestQueueServer.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public void getImagesOfPage(JSONObject jsonObject) {
        try {
            if(jsonObject!=null) {
                JSONArray array = jsonObject.getJSONArray("photos");
                nextPageUrl = jsonObject.getString("next_page");
                Log.d("NExt",nextPageUrl);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject photo = (JSONObject) array.get(i);
                    JSONObject src = (JSONObject) photo.get("src");
                    String mediumImgUrl = src.getString("medium");
                    String largeImgUrl = src.getString("portrait");
                    mediumImgUrl.replace("\\", "");
                    largeImgUrl.replace("\\", "");
                    urls.add(mediumImgUrl);
                    largeUrls.add(largeImgUrl);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
