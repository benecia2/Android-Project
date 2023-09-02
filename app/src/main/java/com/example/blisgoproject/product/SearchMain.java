package com.example.blisgoproject.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blisgoproject.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMain extends AppCompatActivity {

    private SearchAdapter searchAdapter;
    private List<Product>productList;
    private RecyclerView recyclerView;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        productList = new ArrayList<>();
        searchAdapter = new SearchAdapter(productList);

        recyclerView = findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchAdapter);

        //검색결과
        EditText word = findViewById(R.id.word);
        ImageView searchGo = findViewById(R.id.searchGo);

        searchGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = word.getText().toString();

                ProductService productService = ProductClient.getInstance().getProductService();
                Call<List<Product>>call = productService.search(keyword);
                call.enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        List<Product>productList1 = response.body();
                        if (productList1 != null) {
                            searchAdapter.searchList(productList1);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });
            }
        });

        //폐기물 클릭시 상세보기 창
        searchAdapter.setItemClickListener(new SearchAdapter.ItemClickListener() {
            @Override
            public void ItemClick(int pos) {
                position = pos;
                Product product = searchAdapter.getItem(pos);

                Intent intent = new Intent(getApplicationContext(), ProductMain.class);
                intent.putExtra("pnum", product.getPnum());
                startActivity(intent);
            }
        });

        //뒤로가기
        ImageView backkey = findViewById(R.id.backGo);

        backkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}