package com.example.delta_project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class friendsadapter extends RecyclerView.Adapter<friendsadapter.viewholder> {
    List<friends> profiles;
    Context context;
    String user;
    public friendsadapter(List<friends> profiles, Context context,String user) {
        this.profiles = profiles;
        this.context = context;
        this.user=user;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.friendlist,null);
        viewholder holder=new viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int i) {
        Log.d("hello",user);
      if(profiles.get(i).getFrom().equals(user)){
          Log.d("hello",profiles.get(i).getFrom());
          holder.profilename.setText(profiles.get(i).getTo());
          Picasso.with(context)
                  .load(api.BASE_URL+"/uploads/"+profiles.get(i).getTo()+".png")
                  .into(holder.profilepic);
      }
      else {
          Log.d("hello",profiles.get(i).getTo());
          holder.profilename.setText(profiles.get(i).getFrom());
          Picasso.with(context)
                  .load(api.BASE_URL+"/uploads/"+profiles.get(i).getFrom()+".png")
                  .into(holder.profilepic);
      }
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView profilepic;
        TextView profilename;
        LinearLayout parentlayout;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            profilepic=itemView.findViewById(R.id.imageView);
            profilename=itemView.findViewById(R.id.textView3);
            parentlayout=itemView.findViewById(R.id.parentlayout);
        }
    }
}
