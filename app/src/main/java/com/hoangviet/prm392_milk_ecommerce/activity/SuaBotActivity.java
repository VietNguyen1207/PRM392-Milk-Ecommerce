package com.hoangviet.prm392_milk_ecommerce.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hoangviet.prm392_milk_ecommerce.R;
import com.hoangviet.prm392_milk_ecommerce.adapter.SuabotAdapter;
import com.hoangviet.prm392_milk_ecommerce.api.ApiMilkStore;
import com.hoangviet.prm392_milk_ecommerce.model.NewProduct;
import com.hoangviet.prm392_milk_ecommerce.retrofit.RetrofitClient;
import com.hoangviet.prm392_milk_ecommerce.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SuaBotActivity extends AppCompatActivity {

    //params
    Toolbar toolbar;
    RecyclerView recyclerView;

    //api
    ApiMilkStore apiMilkStore;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int category_id;

    //adapter
    SuabotAdapter suabotAdapter;
    List<NewProduct> listSuabot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sua_bot);
        apiMilkStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMilkStore.class);
        category_id = getIntent().getIntExtra("category_id",1);
        Anhxa();
        ActionToolBar();
        getData();
    }

    private void getData() {
        compositeDisposable.add(apiMilkStore.getProductInformation(page , category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                   newProductCallback -> {
                        if (newProductCallback.isSuccess()) {
                            listSuabot = newProductCallback.getResult();
                            suabotAdapter = new SuabotAdapter(getApplicationContext(), listSuabot);
                            recyclerView.setAdapter(suabotAdapter);
                       }                   },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Khong the ket noi den server", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.suabot_toolbar);
        recyclerView = findViewById(R.id.suabot_recyleview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        listSuabot = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}