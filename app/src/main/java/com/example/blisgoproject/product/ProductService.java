package com.example.blisgoproject.product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {

    //업데이트 된 폐기물(전체보기)
    @GET("upProdList")
    Call<List<Product>>update10List();

    //카테고리별 폐기물 리스트
    @GET("cgList")
    Call<List<Product>>cgListByCategory(@Query("category") String category);

    //폐기물 상세보기
    @POST("productView/{pnum}")
    Call<Product>view(@Path("pnum") int pnum, @Body Product product);

    //검색 리스트
    @GET("searchView")
    Call<List<Product>>search(@Query("word")String word);

}
