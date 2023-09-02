package com.example.blisgoproject.community;

import android.util.Log;

import com.example.blisgoproject.comment.CommentService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class BoardClient {
    private  static BoardClient instance;
    private  BoardService boardService;


    public BoardClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.100.102.14:8999/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        boardService = retrofit.create(BoardService.class);

    }

    public  static  BoardClient getInstance(){
        if(instance == null){
            instance = new BoardClient();
        }
        return  instance;
    }

    public  BoardService getBoardService(){

        return  boardService;
    }

}
