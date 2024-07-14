package com.hoangviet.prm392_milk_ecommerce.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

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
        getData(page);
        addEventLoad();
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isLoading == false) {
                    if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == listSuabot.size()-1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                //add null
                listSuabot.add(null);
                suabotAdapter.notifyItemInserted(listSuabot.size() - 1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //remove null
                listSuabot.remove(listSuabot.size() -1);
                suabotAdapter.notifyItemRemoved(listSuabot.size());
                page = page + 1;
                getData(page);
                suabotAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }

    private void getData( int page) {
        compositeDisposable.add(apiMilkStore.getProductInformation(page , category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                   newProductCallback -> {
                        if (newProductCallback.isSuccess()) {
                            if(suabotAdapter == null) {
                                listSuabot = newProductCallback.getResult();
                                suabotAdapter = new SuabotAdapter(getApplicationContext(), listSuabot);
                                recyclerView.setAdapter(suabotAdapter);
                            }else {
                                int newproductloadedPos = listSuabot.size()-1;
                                int quantityAdd = newProductCallback.getResult().size();
                                for (int i = 0; i < quantityAdd; i++) {
                                    listSuabot.add(newProductCallback.getResult().get(i));
                                }
                                suabotAdapter.notifyItemRangeInserted(newproductloadedPos, quantityAdd);
                            }

                       }else {
                            Toast.makeText(getApplicationContext(),"Het data", Toast.LENGTH_LONG).show();
                            isLoading = true;

                        }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Khong the ket noi den server", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.suabot_toolbar);
        recyclerView = findViewById(R.id.suabot_recyleview);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        listSuabot = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}