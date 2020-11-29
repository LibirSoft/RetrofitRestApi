package com.libirsoft.retrofit_demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textresult;
    Retrofit retrofit;
    JsonPlaceHolderApi js;
    Button postbtn, commentbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postbtn = findViewById(R.id.post_btn);
        commentbtn = findViewById(R.id.comment_btn);
        textresult = findViewById(R.id.result);
        // our connection here
        buildretrofit();

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textresult.setText("");
                //this method showing only https://jsonplaceholder.typicode.com/posts
                showPosts();
            }
        });

        commentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textresult.setText("");
                // this method showing only https://jsonplaceholder.typicode.com/posts/1/comments
                showComments();
            }
        });


    }

    void showPosts() {

        js = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = js.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {

                    textresult.setText("response denied : " + response.code());
                    return;

                }
                List<Post> posts = response.body();
                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";
                    textresult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                textresult.setText(t.getMessage());
            }
        });


    }

    void showComments() {
        js = retrofit.create(JsonPlaceHolderApi.class);
        //i an giving value to id . You can do this with editbox or somthing else
        Call<List<Comment>> call = js.getComments(3);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()) {

                    textresult.setText("response denied : " + response.code());
                    return;

                }
                List<Comment> comments = response.body();

                for (Comment comment : comments) {

                    String content = "";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += " ID: " + comment.getId() + "\n";
                    content += "Email : " + comment.getEmail() + "\n";
                    content += "Name : " + comment.getName() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";
                    textresult.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

                textresult.setText(t.getMessage());
            }
        });


    }

    public void buildretrofit() {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}