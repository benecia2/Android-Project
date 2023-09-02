package com.example.blisgoproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blisgoproject.R;
import com.example.blisgoproject.product.CategoryMain;
import com.example.blisgoproject.product.Product;
import com.example.blisgoproject.product.ProductAdapter;
import com.example.blisgoproject.product.ProductClient;
import com.example.blisgoproject.product.ProductMain;
import com.example.blisgoproject.product.ProductService;
import com.example.blisgoproject.product.SearchMain;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProductAdapter productAdapter;
    private List<Product>productList;
    private RecyclerView recyclerView;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList);

        recyclerView = findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(productAdapter);

        //업데이트 된 폐기물 리스트(전체보기)
        ProductService productService = ProductClient.getInstance().getProductService();
        Call<List<Product>>call = productService.update10List();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> productsList = response.body();
                if (productsList != null) {
                    productAdapter.updateProductList(productsList);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });



        int[] gridLayoutIds = {
                R.id.furniture1, R.id.homeapp1, R.id.lifeuse1,
                R.id.bathuse1, R.id.book1, R.id.cosmetic1,
                R.id.kitchen1, R.id.food1, R.id.surface1,
                R.id.fashion1, R.id.etc1
        };

        int[] textIds = {
                R.id.furnitureTxt, R.id.homeappTxt, R.id.lifeuseTxt,
                R.id.bathuseTxt, R.id.bookTxt, R.id.cosmeticsTxt,
                R.id.kitchenTxt, R.id.foodTxt, R.id.surfaceTxt,
                R.id.fashionTxt, R.id.etcTxt
        };

        for (int i=0; i<gridLayoutIds.length; i++) {
            LinearLayout gridlayout = findViewById(gridLayoutIds[i]);
            TextView textView = findViewById(textIds[i]);

            gridlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String category = textView.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), CategoryMain.class);
                    intent.putExtra("selectCategory", category);
                    startActivity(intent);
                }
            });
        }

        // 업데이트 된 폐기물 클릭시 상세보기 창
        productAdapter.setItemClickListener(new ProductAdapter.ItemClickListener() {
            @Override
            public void ItemClick(int pos) {
                position = pos;
                Product product = productAdapter.getItem(pos);

                Intent intent = new Intent(getApplicationContext(), ProductMain.class);
                intent.putExtra("pnum", product.getPnum());
                startActivity(intent);

            }
        });

        //검색창
        Button searchBtn = findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchMain.class);
                startActivity(intent);
            }
        });


        //게시판 이동
//        Button BoardBtn = findViewById(R.id.boardBtn);
        TextView BoardBtn = findViewById(R.id.boardBtn);

        BoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommunityActivity.class);
                startActivity(intent);
            }
        });

    }
}