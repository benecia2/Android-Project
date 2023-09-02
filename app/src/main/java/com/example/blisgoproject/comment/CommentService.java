package com.example.blisgoproject.comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentService {
    //댓글 추가
    @POST("comment/{id}")
    Call<Comment> createComment(@Path("id") Long boardId, @Body Comment comment);

    //댓글 목록 조회
    @GET("comment/{boardId}")
    Call<List<Comment>>getComments(@Path("boardId")Long boardId);

    //댓글삭제
    @DELETE("comment/{id}")
    Call<Void>deleteComment(@Path("id") Long id);
}
