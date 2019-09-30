package com.example.delta_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class postadapter extends RecyclerView.Adapter<postadapter.viewholder>{
    Context context;
    List<posts> post;
    String token;

    public postadapter(Context context, List<posts> post,String token) {
        this.context = context;
        this.post = post;
        this.token=token;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.postlist,null);
        viewholder holder=new viewholder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder,final int i) {
        holder.profile.setText(post.get(i).getUser());
        holder.postcontent.setText(post.get(i).getContent());
        Picasso.with(context)
                .load(api.BASE_URL+"/uploads/"+post.get(i).getUser()+".png")
                .into(holder.profilepic,new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        holder.postpic.setMaxWidth(0);
                        holder.postpic.setMaxHeight(0);
                        Toast.makeText(context,"kjdsbvud",Toast.LENGTH_SHORT).show();
                        Log.d("oof","kjbvkjs");
                    }
                });
        Picasso.with(context)
                .load(api.BASE_URL+"/uploads/"+ (Integer.parseInt(post.get(i).getId())-1)+".png")
                .into(holder.postpic);
        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl(api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api myapi=retrofit.create(api.class);
                Call<String> call=myapi.addcomm(token,holder.comment.getText().toString(),post.get(i).getId());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(context,"comment added",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
        holder.t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Bundle bundle=new Bundle();
                bundle.putString("postid",post.get(i).getId());
                Fragment fragment=new commentfragment();
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,fragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView profilepic,postpic;
        TextView profile,postcontent,t;
        EditText comment;
        Button submit;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            profile=itemView.findViewById(R.id.postprofilename);
            postcontent=itemView.findViewById(R.id.posttext);
            profilepic=itemView.findViewById(R.id.postprofilepic);
            postpic=itemView.findViewById(R.id.postpic);
            comment=itemView.findViewById(R.id.commentText);
            submit=itemView.findViewById(R.id.addcomm);
            t=itemView.findViewById(R.id.textView17);
        }
    }
}
