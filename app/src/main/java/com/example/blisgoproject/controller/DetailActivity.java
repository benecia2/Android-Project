package com.example.blisgoproject.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blisgoproject.R;
import com.example.blisgoproject.comment.CommentAdapter;
import com.example.blisgoproject.comment.CommentClient;
import com.example.blisgoproject.comment.CommentService;
import com.example.blisgoproject.community.Board;
import com.example.blisgoproject.community.BoardClient;
import com.example.blisgoproject.community.BoardService;
import com.example.blisgoproject.comment.Comment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
  private  TextView detail_title, detail_name, detail_date, detail_content;
  private Button btn_move_commu, btn_delete, btn_update_form;
  private  Board board;

  //댓글에 대한 내용
  private  EditText et_comment;
  private  Button btn_comment;
  private  RecyclerView recyclerViewComment;
  private  CommentAdapter commentAdapter;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detail_title = findViewById(R.id.detail_title);
        detail_name = findViewById(R.id.detail_name);
        detail_date = findViewById(R.id.detail_date);
        detail_content = findViewById(R.id.detail_content);
        btn_move_commu = findViewById(R.id.btn_move_commu);
        btn_update_form = findViewById(R.id.btn_update_form);
        btn_delete = findViewById(R.id.btn_delete);



        //  전달 받은 데이터 가져오기
        board = (Board) getIntent().getSerializableExtra("board");


        // 가져온 데이터 화면  표시
        if (board != null) {
            detail_title.setText(board.getTitle());
            detail_name.setText(board.getName());
            detail_date.setText(board.getDate());
            detail_content.setText(board.getContent());

        } else {
            Toast.makeText(this, "데이터를 받아올 수 없습니다.", Toast.LENGTH_SHORT).show();
        }

        //목록 버튼 클릭 처리
        btn_move_commu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, CommunityActivity.class);
                startActivity(intent);
                Toast.makeText(DetailActivity.this, "게시판으로 이동", Toast.LENGTH_SHORT).show();

            }
        });



        //삭제 버튼
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBoard(board.getId());
            }
        });



        //수정폼으로가는 수정버튼
        btn_update_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 수정 확인 다이얼로그
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);

                    builder.setTitle("수정 확인")
                            .setMessage("수정 하시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //확인 버튼을 누를 때
                                    Intent intent = new Intent(DetailActivity.this, UpdateFormActivity.class);
                                    intent.putExtra("board", board);
                                    startActivity(intent);
                                    Toast.makeText(DetailActivity.this, "게시물 수정 페이지",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //취소 버튼을 누를 때

                                    dialogInterface.dismiss();
                                }
                            });
                    builder.create().show();
            }
        });



        //댓글 목록 리사이클러뷰
        recyclerViewComment = findViewById(R.id.recycleView_comment);
        commentAdapter = new CommentAdapter(new ArrayList<>());
        recyclerViewComment.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComment.setAdapter(commentAdapter);

        et_comment = findViewById(R.id.et_comment);
        btn_comment = findViewById(R.id.btn_comment);

        //댓글 등록 버튼 클릭 이벤트 처리
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentContent = et_comment.getText().toString();
                if(!commentContent.isEmpty()){
                    createComment(commentContent);

                }
            }
        });

        //댓글 목록 가져오기
        loadComments();



    }//OnCreate

    //삭제 버튼 클릭 시 호출
            private void deleteBoard(Long id) {
                BoardService boardService = BoardClient.getInstance().getBoardService();
                Call<Void>call =boardService.deleteBoard(id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(DetailActivity.this, "게시물이 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailActivity.this, CommunityActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(DetailActivity.this, "게시물 삭제에 실패했습니다.",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }


    //댓글조회
    private void loadComments(){
        CommentService commentService = CommentClient.getInstance().getCommentService();
        Call<List<Comment>>call = commentService.getComments(board.getId());

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(response.isSuccessful()){
                    List<Comment>comments = response.body();
                    if(comments != null){
                        commentAdapter.setComments(comments);
                    }
                    Log.d("error", "에러났다" + comments);
                }else{
                    Toast.makeText(DetailActivity.this, "댓글을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.d("error2", "call 에러났다" + call);
            }
        });
    }


    //댓글작성
    private void createComment(String content){
        Comment comment = new Comment(content);
        Long boardId = board.getId();

        CommentService commentService = CommentClient.getInstance().getCommentService();
        Call<Comment>call = commentService.createComment(boardId, comment);

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if(response.isSuccessful()){
                    Comment newComment = response.body();
                    commentAdapter.addComment(newComment);
                    et_comment.setText("");
                    Toast.makeText(DetailActivity.this, "댓글 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DetailActivity.this, "댓글을 작성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });
    }



    }// class DetailActivity
