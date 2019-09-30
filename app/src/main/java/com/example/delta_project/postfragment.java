package com.example.delta_project;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.ByteString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class postfragment extends Fragment {
    RecyclerView r;
    View view;
    EditText e;
    String result;
    Button upload;
    int GALLERY_REQUEST_CODE;
    postadapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.postsfragment,null);
        upload=view.findViewById(R.id.button7);
        e=view.findViewById(R.id.editText3);
        GALLERY_REQUEST_CODE=1;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api myapi=retrofit.create(api.class);
        Call<callpost> call=myapi.getposts(getArguments().getString("token"));
        call.enqueue(new Callback<callpost>() {
            @Override
            public void onResponse(Call<callpost> call, Response<callpost> response) {
              r=view.findViewById(R.id.postsrecycler);
              adapter=new postadapter(getActivity(),response.body().getPost(),getArguments().getString("token"));
              RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
              r.setLayoutManager(layoutManager);
              r.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<callpost> call, Throwable t) {

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_REQUEST_CODE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case 1:
                    Uri uri = data.getData();
                    String filePath = "";
                    String fileId = DocumentsContract.getDocumentId(uri);
                    // Split at colon, use second item in the array
                    String id = fileId.split(":")[1];
                    String[] column = {MediaStore.Images.Media.DATA};
                    String selector = MediaStore.Images.Media._ID + "=?";
                    Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, selector, new String[]{id}, null);
                    int columnIndex = cursor.getColumnIndex(column[0]);
                    if (cursor.moveToFirst()) {
                        filePath = cursor.getString(columnIndex);
                    }
                    cursor.close();
                    result= filePath;
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(api.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    api myapi=retrofit.create(api.class);
                    if(result!=null)
                      Log.d("photopath",result);
                    File file= new File(result);
                    RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part part=MultipartBody.Part.createFormData("photo",file.getName(),fileReqBody);
                    RequestBody message= (RequestBody) RequestBody.create(MediaType.parse("text/plain"), e.getText().toString());
                    RequestBody token=RequestBody.create(MediaType.parse("text/plain"),getArguments().getString("token"));
                    Call<String> call=myapi.newpost(message,token,part);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(getActivity(),response.body(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
    }
   }
}

