package com.example.delta_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class textfragment extends Fragment {
    View view;
    RecyclerView r;
    TextView con;
    EditText message;
    Button sendit;
    List<messages> text;
    JSONObject object,sendmes;
    textadapter adapter;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(api.BASE_URL);
        } catch (URISyntaxException e) {}
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.testfragment,null);
        mSocket.connect();
        message=view.findViewById(R.id.editText4);
        sendit=view.findViewById(R.id.button6);
        object=new JSONObject();
        sendmes=new JSONObject();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api myapi=retrofit.create(api.class);
        Call<callmessage> call=myapi.dismes(getArguments().getString("token"),getArguments().getString("touser"));
        call.enqueue(new Callback<callmessage>() {
            @Override
            public void onResponse(Call<callmessage> call, final Response<callmessage> response) {
                text=new ArrayList<>(response.body().getMes());
                con=getActivity().findViewById(R.id.textView10);
                con.setText(getArguments().getString("touser"));
                r=getActivity().findViewById(R.id.textrecycler);
                adapter=new textadapter(getActivity(),text,getArguments().getString("user"));
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
                mSocket.emit("setuser",getArguments().getString("user"));
                r.setLayoutManager(layoutManager);
                r.setAdapter(adapter);
                r.scrollToPosition(text.size()-1);
                mSocket.on("typing", new Emitter.Listener() {
                    @Override
                    public void call(final Object... args) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject data = (JSONObject) args[0];
                                String from;
                                try{
                                  from=data.getString("from");
                                }
                                catch (JSONException e){
                                    return;
                                }
                                if(getArguments().getString("touser").equals(from));
                                {
                                    TextView typ=getActivity().findViewById(R.id.textView11);
                                    typ.setText("is typing");
                                }
                            }
                        });
                    }
                });
                mSocket.on("stoptyping", new Emitter.Listener() {
                    @Override
                    public void call(final Object... args) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject data = (JSONObject) args[0];
                                String from;
                                try{
                                    from=data.getString("from");
                                }
                                catch (JSONException e){
                                    return;
                                }
                                if(getArguments().getString("touser").equals(from));
                                {
                                    TextView typ=getActivity().findViewById(R.id.textView11);
                                    typ.setText("");
                                }
                            }
                        });
                    }
                });
                mSocket.on("updatemessage", new Emitter.Listener() {
                    @Override
                    public void call(final Object... args) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject data = (JSONObject) args[0];
                                String from=null,mes=null;
                                try {
                                    from=data.getString("from");
                                    mes=data.getString("mes");
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(from.equals(getArguments().getString("touser"))){
                                    text.add(new messages(from,getArguments().getString("user"),mes));
                                    adapter=new textadapter(getActivity(),text,getArguments().getString("user"));
                                    adapter.notifyDataSetChanged();
                                    r.setAdapter(adapter);
                                    r.scrollToPosition(text.size()-1);
                                }
                            }
                        });
                    }
                });
                sendit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            sendmes.put("mes",message.getText().toString());
                            sendmes.put("from",getArguments().getString("user"));
                            sendmes.put("to",getArguments().getString("touser"));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        mSocket.emit("chatmessage",sendmes);
                        text.add(new messages(getArguments().getString("user"),getArguments().getString("touser"),message.getText().toString()));
                        adapter=new textadapter(getActivity(),text,getArguments().getString("user"));
                        adapter.notifyDataSetChanged();
                        r.setAdapter(adapter);
                        r.scrollToPosition(text.size()-1);
                    }
                });
                message.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            object.put("fromuser",getArguments().getString("user"));
                            object.put("touser",getArguments().getString("touser"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mSocket.emit("typing",object);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                      mSocket.emit("stoptyping",object);
                    }
                });
            }

            @Override
            public void onFailure(Call<callmessage> call, Throwable t) {

            }
        });
        return view;
    }
}
