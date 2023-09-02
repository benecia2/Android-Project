package com.example.blisgoproject.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blisgoproject.R;
import com.example.blisgoproject.community.Board;
import com.example.blisgoproject.community.BoardClient;
import com.example.blisgoproject.community.BoardService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateFormActivity extends AppCompatActivity {
           private  EditText update_title, update_name, update_content;
           private  TextView update_date;
            private Button btnUpdate, btnCancel;
            private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_form);

        update_title = findViewById(R.id.update_title);
        update_name = findViewById(R.id.update_name);
        update_content = findViewById(R.id.update_content);
        update_date = findViewById(R.id.update_date);
        btnUpdate = findViewById(R.id.btn_update);
        btnCancel = findViewById(R.id.btn_cancel);

          // Intent로 전달된 게시글 데이터 받기
        board = (Board) getIntent().getSerializableExtra("board");

        // 게시글 데이터를 폼에 나타냄
        if(board != null){
            update_title.setText(board.getTitle());
            update_name.setText(board.getName());
            update_date.setText(board.getDate());
            update_content.setText(board.getContent());
        }


        //수정완료
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //수정내용 얻기
                String updatedTitle = update_title.getText().toString();
                String updatedName = update_name.getText().toString();
                String updatedContent = update_content.getText().toString();
                String updatedDate = update_date.getText().toString();



                //수정된 내용 board 객체에 업데이트
                board.setTitle(updatedTitle);
                board.setName(updatedName);
                board.setContent(updatedContent);
                board.setDate(updatedDate);


                //수정 내용 서버에 전송
                updateBoard(board.getId(), board);
                Toast.makeText(UpdateFormActivity.this, "게시물 내용이 수정 되었습니다.",Toast.LENGTH_SHORT).show();

            }
        });

        //취소
        btnCancel.setOnClickListener(v -> onBackPressed());


    }//onCreate

    private  void  updateBoard(Long id, Board board){
        BoardService boardService = BoardClient.getInstance().getBoardService();
        Call<Board>call = boardService.updatedBoard(id, board);
        call.enqueue(new Callback<Board>() {
            @Override
            public void onResponse(Call<Board> call, Response<Board> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(UpdateFormActivity.this, DetailActivity.class);
                    intent.putExtra("board", response.body());
                    startActivity(intent);
                }else {
                    Toast.makeText(UpdateFormActivity.this, "게시물 수정에 실패했습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Board> call, Throwable t) {
                Toast.makeText(UpdateFormActivity.this, "서버와의 통신에 실패.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}//업데이트 액티비티