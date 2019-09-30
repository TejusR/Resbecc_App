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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class profilefragment extends Fragment {
    View view;
    ImageView profilepic;
    friendsadapter adapter;
    RecyclerView r1,r2;
    postadapter adapter2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.profilefragment,null);
        profilepic=view.findViewById(R.id.imageView4);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api myapi=retrofit.create(api.class);
        Call<callprofile> call=myapi.viewprofile(getArguments().getString("token"));
        call.enqueue(new Callback<callprofile>() {
            @Override
            public void onResponse(Call<callprofile> call, Response<callprofile> response) {
                Picasso.with(getActivity())
                        .load(api.BASE_URL+"/uploads/"+response.body().getUser()+".png")
                        .into(profilepic);
                TextView name=view.findViewById(R.id.textView14);
                name.setText(response.body().getUser());
                r1=view.findViewById(R.id.profilefriendsrecycler);
                adapter=new friendsadapter(response.body().getUserfrnds(),getActivity(),response.body().getUser());
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,true);
                r1.setLayoutManager(layoutManager);
                r1.setAdapter(adapter);
                r2=view.findViewById(R.id.profileposts);
                adapter2=new postadapter(getActivity(),response.body().getUserposts(),getArguments().getString("token"));
                RecyclerView.LayoutManager lm=new LinearLayoutManager(getActivity());
                r2.setLayoutManager(lm);
                r2.setAdapter(adapter2);
            }

            @Override
            public void onFailure(Call<callprofile> call, Throwable t) {

            }
        });
        return view;
    }
}
