package com.hoangviet.prm392_milk_ecommerce.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.hoangviet.prm392_milk_ecommerce.R;
import com.hoangviet.prm392_milk_ecommerce.model.NewProduct;

public class ProductInformationActivity extends AppCompatActivity {

    TextView txtProductName, txtProductDescription, txtProductPrice;
    Button btnAddtoCart;
    ImageView imgProduct;
    Spinner spinner;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_information);
        initView();
        ActionToolBar();
        initData();
    }

    private void initData(){
        NewProduct newProduct = (NewProduct) getIntent().getSerializableExtra("productDetail");
        txtProductName.setText(newProduct.getProduct_name());
        txtProductPrice.setText(newProduct.getProduct_price());
        txtProductDescription.setText(newProduct.getDescription());
        Glide.with(getApplicationContext()).load(newProduct.getProduct_image()).into(imgProduct);
        Integer[]  quantity = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspinner = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, quantity);
        spinner.setAdapter(adapterspinner);
    }

    private void initView(){
        txtProductName = findViewById(R.id.txtProductName);
        txtProductPrice = findViewById(R.id.txtProductPrice);
        txtProductDescription = findViewById(R.id.txtProductDetail);
        btnAddtoCart = findViewById(R.id.btnAddToCart);
        spinner = findViewById(R.id.spinner);
        imgProduct = findViewById(R.id.imgProdcutInformation);
        toolbar = findViewById(R.id.toolbarDetail);
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
}