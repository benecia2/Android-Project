package com.example.blisgoproject.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blisgoproject.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryMain extends AppCompatActivity {

    private CategoryMainAdapter categoryMainAdapter;
    private List<Product>productList;
    private RecyclerView cgRecyclerView;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_category);

        TextView cgTitle = findViewById(R.id.cgTitle);
        Intent intent = getIntent();
        String selectCategory =intent.getStringExtra("selectCategory");
        cgTitle.setText(selectCategory);

        productList = new ArrayList<>();
        categoryMainAdapter = new CategoryMainAdapter(productList);

        cgRecyclerView = findViewById(R.id.cgRecycleView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        cgRecyclerView.setLayoutManager(gridLayoutManager);
        cgRecyclerView.setAdapter(categoryMainAdapter);

        //리스트 출력
        ProductService productService = ProductClient.getInstance().getProductService();
        Call<List<Product>>call = productService.cgListByCategory(selectCategory);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> productList = response.body();
                if (productList != null) {
                    categoryMainAdapter.cgList(productList);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });

        //리스트 물품 클릭시 상세보기 창
        categoryMainAdapter.setItemClickListener(new CategoryMainAdapter.ItemClickListener() {
            @Override
            public void ItemClick(int pos) {
                position = pos;
                Product product = categoryMainAdapter.getItem(pos);

                Intent intent1 = new Intent(getApplicationContext(), ProductMain.class);
                intent1.putExtra("pnum", product.getPnum());
                startActivity(intent1);
            }
        });

        ImageView backKey = findViewById(R.id.backKey);
        backKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

}