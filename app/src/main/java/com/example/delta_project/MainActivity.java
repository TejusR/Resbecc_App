package com.example.delta_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText user,pass;
    Button signin,signup;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=findViewById(R.id.editText);
        pass=findViewById(R.id.editText2);
        signin=findViewById(R.id.button);
        signup=findViewById(R.id.button2);
        tv=findViewById(R.id.textView2);
        signin.setOnClickListener(new View.OnClickListener() {
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
                Call<authres> call=myapi.signin(user.getText().toString(),pass.getText().toString());
                call.enqueue(new Callback<authres>() {
                    @Override
                    public void onResponse(Call<authres> call, Response<authres> response) {
                        if(response.body().getMessage().equals("success")==true)
                        {
                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),homeactivity.class);
                            intent.putExtra("token",response.body().getToken());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<authres> call, Throwable t) {
                    }
                });
            }
        });
    }
}
