package com.example.delta_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class commentfragment extends Fragment {
    View view;
    RecyclerView r;
    commentadapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.commentfragment,null);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api myapi=retrofit.create(api.class);
        Call<List<comments>> call=myapi.showcom(getArguments().getString("postid"));
        call.enqueue(new Callback<List<comments>>() {
            @Override
            public void onResponse(Call<List<comments>> call, Response<List<comments>> response) {
                r=getActivity().findViewById(R.id.commentrecycler);
                adapter=new commentadapter(response.body(),getActivity());
                RecyclerView.LayoutManager lm=new LinearLayoutManager(getActivity());
                r.setLayoutManager(lm);
                r.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<comments>> call, Throwable t) {

            }
        });
        return view;
    }
}
