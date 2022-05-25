package com.example.baldurstore2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baldurstore2.R;
import com.example.baldurstore2.retrofit.ApiBanHang;
import com.example.baldurstore2.retrofit.RetrofitClient;
import com.example.baldurstore2.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    TextView txtdangki, txtResetPassword;
    EditText email, pass;
    AppCompatButton btndangnhap;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initControll();
    }

    private void initControll() {
        txtdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        //reset pass
        txtResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        //xu ly dang nhap
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = email.getText().toString().trim();
                String str_pass = pass.getText().toString().trim();
                if(TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập Email", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(str_pass)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập Pass", Toast.LENGTH_SHORT).show();
                }else{
                    //save
                    compositeDisposable.add(apiBanHang.dangNhap(str_email,str_pass)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel ->{
                                        if(userModel.isSuccess()){
                                            isLogin = true;
                                            Paper.book().write("isLogin", isLogin);
                                            Utils.user_current = userModel.getResult().get(0);
                                            //Luu lai thong tin nguoi dung
                                            Paper.book().write("user", userModel.getResult().get(0));
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(getApplicationContext(),"Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else{
                                            Toast.makeText(getApplicationContext(),"Tai khoan hoac mat khau bi sai", Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable ->{
                                        Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                            ));

                }
            }
        });


    }



    private void initView() {
        Paper.init(this);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        txtdangki = findViewById(R.id.txtSignUp);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        btndangnhap = findViewById(R.id.btnSignIn);
        txtResetPassword = findViewById(R.id.txtResetPassword);

        //read data

    }



    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.user_current.getEmail() != null && Utils.user_current.getPass() != null){
            email.setText(Utils.user_current.getEmail());
            pass.setText(Utils.user_current.getPass());        }

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}