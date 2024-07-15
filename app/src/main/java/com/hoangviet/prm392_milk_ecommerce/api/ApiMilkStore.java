package com.hoangviet.prm392_milk_ecommerce.api;

import com.hoangviet.prm392_milk_ecommerce.callback.Category_callback;
import com.hoangviet.prm392_milk_ecommerce.callback.NewProduct_callback;
import com.hoangviet.prm392_milk_ecommerce.callback.User_callback;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiMilkStore {
    //get the category
    @GET("getcategory.php")
    Observable<Category_callback> getCategory();

    //get the new product
    @GET("getnewproduct.php")
    Observable<NewProduct_callback> getNewProduct();

    //get products information
    @POST("getproductinformation.php")
    @FormUrlEncoded
    Observable<NewProduct_callback> getProductInformation(
            @Field("page") int page,
            @Field("category_id") int category_id
    );

    //sign up
    @POST("signUp.php")
    @FormUrlEncoded
    Observable<User_callback> signUp(
            @Field("email") String email,
            @Field("password") String password,
            @Field("username") String username,
              @Field("phone") String phone
    );
}
