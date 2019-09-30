package com.example.delta_project;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface api {
    public String BASE_URL="http://localhost:3000";
    @FormUrlEncoded
    @POST("/auth")
    Call<authres> signin(@Field("user") String user, @Field("pass") String pass);
    @FormUrlEncoded
    @POST("/displayPosts")
    Call<callpost> getposts(@Field("token") String token);
    @FormUrlEncoded
    @POST("/frnds")
    Call<callfriends> getfriends(@Field("token") String token);
    @FormUrlEncoded
    @POST("/acceptreq")
    Call<String> acceptreq(@Field("token") String token,@Field("userid") String user);
    @FormUrlEncoded
    @POST("/sendreq")
    Call<String> sendreq(@Field("token") String token,@Field("to") String to);
    @FormUrlEncoded
    @POST("/dismes")
    Call<callmessage> dismes(@Field("token") String token, @Field("touser") String to);
    @FormUrlEncoded
    @POST("/profile")
    Call<callprofile> viewprofile(@Field("token") String token);
    @Multipart
    @POST("/post")
    Call<String> newpost(@Part("message") RequestBody message, @Part("token") RequestBody token, @Part MultipartBody.Part photo);
    @FormUrlEncoded
    @POST("/addcomment")
    Call<String> addcomm(@Field("token") String token,@Field("comment") String comment,@Field("postid") String postid);
    @FormUrlEncoded
    @POST("/showcomments")
    Call<List<comments>> showcom(@Field("postid") String postid);
}
