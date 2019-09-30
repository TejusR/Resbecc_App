package com.example.delta_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class friendsfragment extends Fragment {
    View view;
    RecyclerView r1,r2,r3;
    friendsadapter adapter1;
    frreqadapter adapter2;
    EditText search;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.friendsfragment,null);
        search=view.findViewById(R.id.friendsearch);
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
                r1=view.findViewById(R.id.cfrecycler);
                r2=view.findViewById(R.id.frreqrecycler);
                r3=view.findViewById(R.id.addfrndsrecycler);
                adapter1=new friendsadapter(response.body().getCf(),getActivity(),response.body().getUser());
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true);
                r1.setLayoutManager(layoutManager);
                r1.setAdapter(adapter1);
                adapter2=new frreqadapter(response.body().getFrreq(),getActivity(),getArguments().getString("token"));
                RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true);
                r2.setLayoutManager(layoutManager2);
                r2.setAdapter(adapter2);
            }

            @Override
            public void onFailure(Call<callfriends> call, Throwable t) {
                Log.d("friends",t.getMessage());
            }
        });
        return view;
    }
}
