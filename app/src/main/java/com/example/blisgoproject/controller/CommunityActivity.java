package com.example.blisgoproject.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.blisgoproject.R;
import com.example.blisgoproject.community.Board;
import com.example.blisgoproject.community.BoardAdapter;
import com.example.blisgoproject.community.BoardClient;
import com.example.blisgoproject.community.BoardService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BoardAdapter boardAdapter;
    private List<Board> boardList;
    private Button btn_move;
    private EditText searchView;
    private int currentPage = 1;    //시작페이지
    private final int items_per_page = 10;  //페이지당 아이템 수


    @Override
    protected void onResume() {
        // 데이터를 다시 불러와서 어댑터를 업데이트
        loadBoardData();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);


        //글쓰기 버튼
        btn_move = findViewById(R.id.btn_move);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // RegisterActivity로 이동하는 intent
                Intent intent = new Intent(CommunityActivity.this, RegisterActivity.class);
                startActivity(intent);
                Toast.makeText(CommunityActivity.this, "게시글 작성페이지", Toast.LENGTH_SHORT).show();
            }
        });

        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recycleView);

        boardList = new ArrayList<>();
        boardAdapter = new BoardAdapter(boardList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        recyclerView.setAdapter(boardAdapter);
       loadBoardData();



//        // 게시판 데이터 불러오는 작업
//        BoardService boardService = BoardClient.getInstance().getBoardService();
//        Call<List<Board>> call = boardService.list();
//        call.enqueue(new Callback<List<Board>>() {
//            @Override
//            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
//                if (response.isSuccessful()) {
//                    boardList.addAll(response.body());
//                    boardAdapter.notifyDataSetChanged();
//                } else {
//                    Log.d("CommunityActivity", "실패");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Board>> call, Throwable t) {
//                Log.d("CommunityActivity", "Error: " + t.getMessage());
//            }
//        });


        // 리사이클러뷰 아이템 클릭 리스너 설정
        boardAdapter.setOnItemClickListener(new BoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Board board) {
                // 디테일 화면으로 데이터 전달
                Intent intent = new Intent(CommunityActivity.this, DetailActivity.class);
                intent.putExtra("board", board);
                startActivity(intent);
                Toast.makeText(CommunityActivity.this, "상세 페이지", Toast.LENGTH_SHORT).show();
            }
        });


        //검색
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString().toLowerCase();
                List<Board> filteredList = new ArrayList<>();

                for(Board board : boardList){
                    if(board.getTitle().toLowerCase().contains(searchText) ||
                         board.getCategory().toLowerCase().contains(searchText) ||
                         board.getName().toLowerCase().contains(searchText)){
                        filteredList.add(board);
                    }
                }
                boardAdapter.setFilteredList(filteredList);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //(메인으로)
        ImageView backToMain = findViewById(R.id.backToMain);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), MainActivity.class );
                startActivity(intent);
            }
        });

        //페이지 버튼
        Button btnLoadMore = findViewById(R.id.btn_loadMore);
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMoreData();
            }
        });

    }//onCreate

    private void loadBoardData() {
        BoardService boardService = BoardClient.getInstance().getBoardService();
        Call<List<Board>> call = boardService.list();
        call.enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                if (response.isSuccessful()) {
                    boardList.clear();
                    boardList.addAll(response.body());
                    boardAdapter.notifyDataSetChanged();
                } else {
                    Log.d("CommunityActivity", "실패");
                }
            }

            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
                Log.d("CommunityActivity", "Error: " + t.getMessage());
            }
        });
    }

    //더보기
    private void loadMoreData(){
        BoardService boardService = BoardClient.getInstance().getBoardService();
        Call<List<Board>>call = boardService.getBoards(currentPage, items_per_page);
        call.enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                if(response.isSuccessful()){
                    List<Board>newBoards = response.body();
                    if(newBoards != null && newBoards.size() > 0){
                        boardList.addAll(newBoards);
                         boardAdapter.notifyDataSetChanged();
                        currentPage++;  //다음 페이지로 설정
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
            }
        });
    }


}
