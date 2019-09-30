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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class messagesfragment extends Fragment {
    View view;
    RecyclerView r;
    messagesadapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.messagesfragment,null);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api myapi=retrofit.create(api.class);
        Call<callfriends> call=myapi.getfriends(getArguments().getString("token"));
        call.enqueue(new Callback<callfriends>() {
            @Override
            public void onResponse(Call<callfriends> call, Response<callfriends> response) {
             r=view.findViewById(R.id.chatlist);
             adapter=new messagesadapter(response.body().getCf(),getActivity(),response.body().getUser(),getArguments().getString("token"));
             RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
             r.setLayoutManager(layoutManager);
             r.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<callfriends> call, Throwable t) {

            }
        });
        return view;
    }
}
