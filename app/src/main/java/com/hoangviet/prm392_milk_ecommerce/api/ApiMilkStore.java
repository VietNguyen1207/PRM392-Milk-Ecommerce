package com.hoangviet.prm392_milk_ecommerce.api;

import com.hoangviet.prm392_milk_ecommerce.callback.Category_callback;
import com.hoangviet.prm392_milk_ecommerce.callback.NewProduct_callback;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiMilkStore {
    //get the category
    @GET("getcategory.php")
    Observable<Category_callback> getCategory();

    //get the new product
    @GET("getnewproduct.php")
    Observable<NewProduct_callback> getNewProduct();

}
