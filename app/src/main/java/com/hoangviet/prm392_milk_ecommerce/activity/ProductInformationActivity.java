package com.hoangviet.prm392_milk_ecommerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
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
import com.hoangviet.prm392_milk_ecommerce.model.Cart;
import com.hoangviet.prm392_milk_ecommerce.model.NewProduct;
import com.hoangviet.prm392_milk_ecommerce.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

public class ProductInformationActivity extends AppCompatActivity {

    TextView txtProductName, txtProductDescription, txtProductPrice;
    Button btnAddtoCart;
    ImageView imgProduct;
    Spinner spinner;
    Toolbar toolbar;
    NewProduct newProduct;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_information);
        initView();
        ActionToolBar();
        initData();
        initAddtoCart();

    }

    private void initAddtoCart(){
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
    }

    private void addToCart(){
        if(Utils.listCart.size() > 0){
            boolean flag = false;
            int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i = 0; i < Utils.listCart.size(); i++) {
                if(Utils.listCart.get(i).getIdProduct() == newProduct.getId()){
                    Utils.listCart.get(i).setQuantity(quantity + Utils.listCart.get(i).getQuantity());
//                    Float productPrice = newProduct.getProduct_price() * quantity;
                    Float productPrice = newProduct.getProduct_price();
                    Utils.listCart.get(i).setProductPrice(productPrice);
                    flag = true;
                }
            }
            if(flag == false){
//                Float productPrice = newProduct.getProduct_price() * quantity;
                Float productPrice = newProduct.getProduct_price();
                Cart cart = new Cart();
                cart.setProductPrice(productPrice);
                cart.setQuantity(quantity);
                cart.setIdProduct(newProduct.getId());
                cart.setProductName(newProduct.getProduct_name());
                cart.setProductImage(newProduct.getProduct_image());
                Utils.listCart.add(cart);
            }


        }else{
            int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
//            Float productPrice = newProduct.getProduct_price() * quantity;
            Float productPrice = newProduct.getProduct_price();
            Cart cart = new Cart();
            cart.setProductPrice(productPrice);
            cart.setQuantity(quantity);
            cart.setIdProduct(newProduct.getId());
            cart.setProductName(newProduct.getProduct_name());
            cart.setProductImage(newProduct.getProduct_image());
            Utils.listCart.add(cart);
        }
        int totalItems = 0;
        for (int i = 0; i < Utils.listCart.size(); i++) {
            totalItems += Utils.listCart.get(i).getQuantity();
        }
        badge.setText(String.valueOf(totalItems));
    }

    private void initData(){
        newProduct = (NewProduct) getIntent().getSerializableExtra("productDetail");
        txtProductName.setText(newProduct.getProduct_name());
        txtProductPrice.setText(String.valueOf(newProduct.getProduct_price()));
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
        badge = findViewById(R.id.cartQuantity);
        FrameLayout frameLayoutCart = (FrameLayout) findViewById(R.id.frameLayoutCart);
        frameLayoutCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent interCart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(interCart);
            }
        });

        if(Utils.listCart != null){
            badge.setText(String.valueOf(Utils.listCart.size()));
        }
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