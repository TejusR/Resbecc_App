package com.example.delta_project;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class messagesadapter extends RecyclerView.Adapter<messagesadapter.viewholder>{
    List<friends> profiles;
    Context context;
    String user;
    String token;
    public messagesadapter(List<friends> profiles, Context context,String user,String token) {
        this.profiles = profiles;
        this.context = context;
        this.user=user;
        this.token=token;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.chatlist,null);
        viewholder holder=new viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder,final int i) {
        if(profiles.get(i).getTo().equals(user)){
            holder.name.setText(profiles.get(i).getFrom());
            Picasso.with(context)
                    .load(api.BASE_URL+"/uploads/"+profiles.get(i).getFrom()+".png")
                    .into(holder.profilepic);
        }
        else{
            holder.name.setText(profiles.get(i).getTo());
            Picasso.with(context)
                    .load(api.BASE_URL+"/uploads/"+profiles.get(i).getTo()+".png")
                    .into(holder.profilepic);
        }
        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Bundle bundle=new Bundle();
                bundle.putString("token",token);
                if(profiles.get(i).getTo().equals(user)) {
                    bundle.putString("touser",profiles.get(i).getFrom());
                }
                else{
                    bundle.putString("touser",profiles.get(i).getTo());
                }
                bundle.putString("user",user);
                Fragment fragment=new textfragment();
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,fragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView profilepic;
        TextView name;
        RelativeLayout parentlayout;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            profilepic=itemView.findViewById(R.id.imageView3);
            name=itemView.findViewById(R.id.textView9);
            parentlayout=itemView.findViewById(R.id.convo);
        }
    }
}
