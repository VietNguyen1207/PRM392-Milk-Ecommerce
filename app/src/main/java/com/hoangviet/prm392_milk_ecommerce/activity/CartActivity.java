package com.hoangviet.prm392_milk_ecommerce.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hoangviet.prm392_milk_ecommerce.R;
import com.hoangviet.prm392_milk_ecommerce.adapter.CartAdapter;
import com.hoangviet.prm392_milk_ecommerce.model.Cart;
import com.hoangviet.prm392_milk_ecommerce.utils.Utils;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    TextView txtblankCart, txtTotalCartPrice;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnBuy;
    CartAdapter cartAdapter;
    List<Cart> listCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        initView();
        initControll();    }

    private void initControll() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(Utils.listCart.isEmpty()) {
            txtblankCart.setText(View.VISIBLE);
        }else {
            cartAdapter = new CartAdapter(getApplicationContext(), Utils.listCart);
            recyclerView.setAdapter(cartAdapter);
        }


    }

    private void initView(){
        txtblankCart = findViewById(R.id.txtCartBlank);
        txtTotalCartPrice = findViewById(R.id.txtCartTotalPrice);
        toolbar = findViewById(R.id.cartToolbar);
        recyclerView = findViewById(R.id.recyclerViewCart);
        btnBuy = findViewById(R.id.btnCartBuy);

    }
}