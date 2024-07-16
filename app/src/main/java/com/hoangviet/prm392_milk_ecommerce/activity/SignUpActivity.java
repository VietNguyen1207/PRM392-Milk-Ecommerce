package com.hoangviet.prm392_milk_ecommerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hoangviet.prm392_milk_ecommerce.R;
import com.hoangviet.prm392_milk_ecommerce.api.ApiMilkStore;
import com.hoangviet.prm392_milk_ecommerce.retrofit.RetrofitClient;
import com.hoangviet.prm392_milk_ecommerce.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity {
    TextView txtSignUpEmail, txtSignUpPassword, txtSignUpRePassword, txtSignUpPhone, txtSignUpUsername;
    AppCompatButton btnSignUp;

    ApiMilkStore apiMilkStore;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        initView();
        initControl();
    }

    private void initControl() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    private void signUp(){
        String email = txtSignUpEmail.getText().toString().trim();
        String password = txtSignUpPassword.getText().toString().trim();
        String repassword = txtSignUpRePassword.getText().toString().trim();
        String phone = txtSignUpPhone.getText().toString().trim();
        String username = txtSignUpUsername.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(username)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập tên", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(repassword)){
            Toast.makeText(getApplicationContext(), "Hãy nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(phone)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
        }else {
            if(password.equals(repassword)){
                compositeDisposable.add(apiMilkStore.signUp(email,password,username,phone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userCallback -> {
                                    if(userCallback.isSuccess()) {
                                        Toast.makeText(getApplicationContext(),"Dang ki thanh cong", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(),userCallback.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        ));
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(), "Mật khẩu chưa trùng khớp", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        txtSignUpEmail = (TextView) findViewById(R.id.txtSignUpEmail);
        txtSignUpPassword = (TextView) findViewById(R.id.txtSignUpPassword);
        txtSignUpRePassword = (TextView) findViewById(R.id.txtSignUpRePassword);
        txtSignUpPhone = (TextView) findViewById(R.id.txtSignUpPhone);
        txtSignUpUsername = (TextView) findViewById(R.id.txtSignUpUsername);
        btnSignUp = findViewById(R.id.btnSignUp);
        apiMilkStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMilkStore.class);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
