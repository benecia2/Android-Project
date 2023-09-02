package com.example.blisgoproject.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blisgoproject.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductMain extends AppCompatActivity {

    private Product product = new Product();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_main);

        TextView productTitle = findViewById(R.id.productTitle);
        ImageView productImage = findViewById(R.id.productImage);
        TextView contentTxt1 = findViewById(R.id.contentTxt1);
        TextView contentTxt2 = findViewById(R.id.contentTxt2);

        TextView titleTxt1 = findViewById(R.id.titleTxt1);
        TextView titleTxt2 = findViewById(R.id.titleTxt2);
        TextView pclass = findViewById(R.id.pclass);


        Intent intent = getIntent();
        int pnum = intent.getIntExtra("pnum",0);  // 두번째 변수(0)은 pnum이 존재하지 않을때 디폴트 값

        //폐기물 상세정보 출력
        ProductService productService = ProductClient.getInstance().getProductService();
        Call<Product>call = productService.view(pnum, product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();
                productTitle.setText(product.getPname());
                contentTxt1.setText(product.getPcontent1());
                contentTxt2.setText(product.getPcontent2());
                pclass.setText("※분류 : " + product.getPclass());

                String imgName = product.getPname2();
                int imgResource = getResources()
                        .getIdentifier(imgName, "drawable", getPackageName());
                if (imgResource != 0) {
                    productImage.setImageResource(imgResource);
                } else {

                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

        //클릭시 버리는 방법 출력
        titleTxt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contentTxt1.getVisibility() == View.GONE) {
                    contentTxt1.setVisibility(View.VISIBLE);
                    contentTxt2.setVisibility(View.GONE);
                } else {
                    contentTxt1.setVisibility(View.GONE);
                }
            }
        });

        titleTxt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contentTxt2.getVisibility() == View.GONE) {
                    contentTxt2.setVisibility(View.VISIBLE);
                    contentTxt1.setVisibility(View.GONE);
                } else {
                    contentTxt2.setVisibility(View.GONE);
                }
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