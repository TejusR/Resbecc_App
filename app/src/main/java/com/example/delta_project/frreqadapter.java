package com.example.delta_project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class frreqadapter  extends RecyclerView.Adapter<frreqadapter.viewholder>{
    List<friends> profiles;
    Context context;
    String token;

    public frreqadapter(List<friends> profiles, Context context,String token) {
        this.profiles = profiles;
        this.context = context;
        this.token=token;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.frreqlist,null);
        Log.d("OOF","kjhbfw");
        viewholder holder=new viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder,final int i) {
        Log.d("OOF","kjhbfw");
        holder.profilename.setText(profiles.get(i).getFrom());
        Picasso.with(context)
                .load(api.BASE_URL+"/uploads/"+profiles.get(i).getFrom()+".png")
                .into(holder.profilepic);
        holder.addfrnd.setOnClickListener(new View.OnClickListener() {
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
                Call<String> call=myapi.acceptreq(token,profiles.get(i).getFrom());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(context,"friend added",Toast.LENGTH_SHORT).show();
                        holder.addfrnd.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("frreq",t.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView profilepic;
        TextView profilename;
        Button addfrnd;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            profilepic=itemView.findViewById(R.id.imageView2);
            profilename=itemView.findViewById(R.id.textView7);
            addfrnd=itemView.findViewById(R.id.button4);
        }
    }
}
