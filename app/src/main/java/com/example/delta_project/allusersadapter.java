package com.example.delta_project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class allusersadapter extends RecyclerView.Adapter<allusersadapter.viewholder>{
    List<String> user;
    Context context;
    String token;

    public allusersadapter(List<String> user, Context context, String token) {
        this.user = user;
        this.context = context;
        this.token = token;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.alluserslist,null);
        viewholder holder=new viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder,final int i) {
        holder.profilename.setText(user.get(i));
        Picasso.with(context)
                .load(api.BASE_URL+"/uploads/"+user.get(i)+".png")
                .into(holder.profilepic);
        holder.sendreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl(api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                api myapi=retrofit.create(api.class);
                Call<String> call=myapi.sendreq(token,user.get(i));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(context,"request sent",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView profilepic;
        TextView profilename;
        Button sendreq;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            profilepic=itemView.findViewById(R.id.imageview3);
            profilename=itemView.findViewById(R.id.textView8);
            sendreq=itemView.findViewById(R.id.button5);
        }
    }
}
