package com.example.baldurstore2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.baldurstore2.R;
import com.example.baldurstore2.adapter.CartAdapter;
import com.example.baldurstore2.model.EventBus.TinhTongEvent;
import com.example.baldurstore2.model.GioHang;
import com.example.baldurstore2.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

import okhttp3.internal.Util;

public class CartActivity extends AppCompatActivity {
    TextView giohangtrong, tongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnBuy;
    CartAdapter adapter;
    long totalProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();
        initControl();
        tinhTotals();
    }

    private void tinhTotals() {
        totalProduct = 0;
        for (int i = 0; i < Utils.mangmuahang.size(); i++){
            totalProduct += (Utils.mangmuahang.get(i).getGiasp() * Utils.mangmuahang.get(i).getSoLuong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(totalProduct));

    }

    private void initControl() {
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
        if (Utils.manggiohang.size() == 0){
            giohangtrong.setVisibility(View.VISIBLE);
        } else {
            adapter = new CartAdapter(getApplicationContext(), Utils.manggiohang);
            recyclerView.setAdapter(adapter);
        }

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra("tongtien", totalProduct);
                Utils.manggiohang.clear();
                startActivity(intent);
            }
        });

    }

    private void initView() {
        giohangtrong = findViewById(R.id.txtEmptyCart);
        tongtien = findViewById(R.id.txtTotals);
        toolbar = findViewById(R.id.toolBar);
        recyclerView = findViewById(R.id.recycleViewCart);
        btnBuy = findViewById(R.id.btnBuy);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event){
        if (event != null){
            tinhTotals();
        }
    }
}