package com.example.blisgoproject.community;


import com.example.blisgoproject.comment.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BoardService {
    //게시글 등록
    @POST("insert")
    Call<Board> insert(@Body Board board);

    //커뮤니티 게시글 목록 가져오기
    @GET("list")
    Call<List<Board>> list();

    //상세보기 삭제
    @DELETE("delete/{id}")
    Call<Void>deleteBoard(@Path("id") Long id);

    //수정
    @PUT("update/{id}")
    Call<Board>updatedBoard(@Path("id")Long id, @Body Board board );

    //페이지네이션(요청할 페이지 번호, 페이지당 아이템 수)
    @GET("boards")
    Call<List<Board>>getBoards(@Query("page") int page, @Query("per_page") int perPage);


}
