package com.hoangviet.prm392_milk_ecommerce.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hoangviet.prm392_milk_ecommerce.R;
import com.google.android.material.navigation.NavigationView;
import com.hoangviet.prm392_milk_ecommerce.adapter.CategoryAdapter;
import com.hoangviet.prm392_milk_ecommerce.api.ApiMilkStore;
import com.hoangviet.prm392_milk_ecommerce.model.Category;
import com.hoangviet.prm392_milk_ecommerce.model.Category_callback;
import com.hoangviet.prm392_milk_ecommerce.retrofit.RetrofitClient;
import com.hoangviet.prm392_milk_ecommerce.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    CategoryAdapter categoryAdapter;
    List<Category> listCategories;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiMilkStore apiMilkStore;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiMilkStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMilkStore.class);
        Anhxa();
        ActionBar();
        if(isConnect(this)){
            Toast.makeText(getApplicationContext(), "Connect", Toast.LENGTH_LONG).show();
            ActionViewFlipper();
            getCategory();
        }else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
        }
    }

    private void getCategory(){
        compositeDisposable.add(apiMilkStore.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        category_callback -> {
                            if (category_callback.isSuccess()) {
                                Category category = category_callback.getResult().get(0);
                                Toast.makeText(getApplicationContext(), category.toString(), Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                            Log.e(TAG, "Error fetching category", throwable);
                            Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }


    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn1.concung.com/img/adds/2024/06/1719558990-HOME(3).png");
        mangquangcao.add("https://cdn1.concung.com/img/adds/2024/06/1719209961-HOME-TAKATO.png");
        mangquangcao.add("https://cdn1.concung.com/img/adds/2024/06/1719213195-HOME(3).png");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setAnimation(slide_in);
        viewFlipper.setAnimation(slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa(){
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recyleview);
        navigationView = findViewById(R.id.navigationview);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        // Create adapter
        listCategories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getApplicationContext(), listCategories);
        listViewManHinhChinh.setAdapter(categoryAdapter);
    }

    private boolean isConnect (Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return true;
        } else {
            return false;
        }
    }
}
