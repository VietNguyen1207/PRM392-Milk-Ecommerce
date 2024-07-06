package com.hoangviet.prm392_milk_ecommerce.api;

import com.hoangviet.prm392_milk_ecommerce.model.Category_callback;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiMilkStore {
    @GET("getproduct.php")
    Observable<Category_callback> getCategory();
}
