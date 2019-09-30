package com.example.delta_project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class textadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<messages> mes;
    String user;
    public textadapter(Context context, List<messages> mes,String user) {
        this.context = context;
        this.mes = mes;
        this.user=user;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder;
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        if(i==0){
            View view=inflater.inflate(R.layout.messagefrom,null);
            holder=new viewholdersent(view);
        }
        else{
            View view=inflater.inflate(R.layout.messagerecieved,null);
            holder=new viewholderrecieved(view);
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if(mes.get(position).getFrom().equals(user))
          return 0;
        else
            return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
       if(holder.getItemViewType()==0){
           viewholdersent vh=(viewholdersent)holder;
           vh.messent.setText(mes.get(i).getContent());
       }
       else{
           viewholderrecieved vh=(viewholderrecieved)holder;
           vh.mesrec.setText(mes.get(i).getContent());
       }
    }

    @Override
    public int getItemCount() {
        return mes.size();
    }

    public class viewholdersent extends RecyclerView.ViewHolder{
        TextView messent;
        public viewholdersent(@NonNull View itemView) {
            super(itemView);
            messent=itemView.findViewById(R.id.textView12);
        }
    }
    public class viewholderrecieved extends RecyclerView.ViewHolder{
        TextView mesrec;
        public viewholderrecieved(@NonNull View itemView) {
            super(itemView);
            mesrec=itemView.findViewById(R.id.textView13);
        }
    }
}
