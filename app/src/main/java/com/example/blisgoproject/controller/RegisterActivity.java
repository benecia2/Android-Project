package com.example.blisgoproject.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.blisgoproject.R;
import com.example.blisgoproject.community.Board;
import com.example.blisgoproject.community.BoardClient;
import com.example.blisgoproject.community.BoardService;
import com.example.blisgoproject.community.CustomSpinnerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_title, et_name, et_content;
    private Spinner spinnerCategory;
    private  Button btn_register_insert, btn_register_return;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_title = findViewById(R.id.et_title);
        et_name = findViewById(R.id.et_name);
        et_content = findViewById(R.id.et_content);

        btn_register_insert = findViewById(R.id.btn_register_insert);
        btn_register_return = findViewById(R.id.btn_register_return);

        // Spinner 초기화
        spinnerCategory = findViewById(R.id.spinner_category);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.category_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);

        // CustomSpinnerAdapter 적용
        String[] categoryArray = getResources().getStringArray(R.array.category_array);
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(this, R.layout.spinner_item_layout, categoryArray);
        spinnerCategory.setAdapter(customSpinnerAdapter);

        //돌아가기 버튼
        btn_register_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                Toast.makeText(RegisterActivity.this, "커뮤니티 게시판", Toast.LENGTH_SHORT).show();
            }
        });


        //등록 버튼
        btn_register_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = et_title.getText().toString();
                String name = et_name.getText().toString();
                String content = et_content.getText().toString();
                String selectedCategory = spinnerCategory.getSelectedItem().toString();     //선택된 분류 가져오기

                if(title.isEmpty() || name.isEmpty() || content.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "빈 칸을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    // Board 객체
                    Board board = new Board(title, name, content, selectedCategory);

                    //현재 날짜 생성
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                    String currentDate = dateFormat.format(new Date());

                    //작성한 글 데이터에 날짜 정보 포함
                    board.setDate(currentDate);

                    //  Retrofit 사용 서버 데이터 등록
                    BoardService boardService = BoardClient.getInstance().getBoardService();
                    Call<Board>call = boardService.insert(board);
                    call.enqueue(new Callback<Board>() {
                        @Override
                        public void onResponse(Call<Board> call, Response<Board> response) {
                            Toast.makeText(RegisterActivity.this, "게시물이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                            //화면 이동
                            Intent intent = new Intent(RegisterActivity.this, CommunityActivity.class);
                            startActivity(intent);


                        }

                        @Override
                        public void onFailure(Call<Board> call, Throwable t) {
                            // 등록 실패시
                            Toast.makeText(RegisterActivity.this, "게시물 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            Log.e("RegisterActivity", "등록 실패: " + t.getMessage());
                        }
                    });

                }

            }
        });



    }
}