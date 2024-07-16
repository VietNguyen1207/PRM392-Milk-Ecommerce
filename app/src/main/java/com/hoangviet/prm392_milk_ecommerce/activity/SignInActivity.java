package com.hoangviet.prm392_milk_ecommerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hoangviet.prm392_milk_ecommerce.R;

public class SignInActivity extends AppCompatActivity {
    TextView txtSignUp;
    TextView txtSignInEmail, txtSignInPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        initView();
        initControl();
    }

    private void initControl() {
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn(){
        String email = txtSignInEmail.getText().toString().trim();
        String pass = txtSignInPassword.getText().toString().trim();
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        if(email.equals("viet@gmail.com") && pass.equals("123456")){
            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }else if(email.equals("thinh@gmail.com") && pass.equals("123456")){
            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }else if(email.equals("admin@gmail.com") && pass.equals("admin")){
            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }else if(email.equals("ha@gmail.com") && pass.equals("123456")){
            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }else if(email.equals("nguyentt@gmail.com") && pass.equals("123456")){
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        txtSignUp = findViewById(R.id.txtSignUpNow);
        txtSignInEmail = findViewById(R.id.txtsignInEmail);
        txtSignInPassword = findViewById(R.id.txtSignInPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
    }
}