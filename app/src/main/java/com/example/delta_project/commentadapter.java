package com.example.delta_project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class commentadapter extends RecyclerView.Adapter<commentadapter.viewholder>{
    List<comments> comm;
    Context context;

    public commentadapter(List<comments> comm, Context context) {
        this.comm = comm;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.commentlist,null);
        viewholder holder=new viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int i) {
        holder.name.setText(comm.get(i).getUser());
        holder.com.setText(comm.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return comm.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView name,com;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textView19);
            com=itemView.findViewById(R.id.textView20);
        }
    }
}
