package com.libirsoft.retrofit_demo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    // for getting https://jsonplaceholder.typicode.com/posts
    @GET("posts")
    Call<List<Post>> getPosts(@Query("userId") int userid);
   // for getting https://jsonplaceholder.typicode.com/posts/1/comments id is user comments what we want to show
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);


}
