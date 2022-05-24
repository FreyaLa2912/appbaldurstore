package com.example.baldurstore2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baldurstore2.R;
import com.example.baldurstore2.model.GioHang;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;

    public CartAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.itemCartProductName.setText(gioHang.getTensp());
        holder.itemCartAmount.setText(gioHang.getSoLuong() + " ");
        Glide.with(context).load(gioHang.getHinhsp()).into(holder.itemCartImage);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.itemCartProductPrice.setText("Giá: " + decimalFormat.format((gioHang.getGiasp()))+ "Đ");
        long gia = gioHang.getSoLuong() * gioHang.getGiasp();
        holder.itemCartProductPrice2.setText(decimalFormat.format(gia));


    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView itemCartImage;
        TextView itemCartProductName, itemCartProductPrice, itemCartAmount, itemCartProductPrice2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCartImage = itemView.findViewById(R.id.itemCartImage);
            itemCartProductName = itemView.findViewById(R.id.itemCartProductName);
            itemCartProductPrice = itemView.findViewById(R.id.itemCartProductPrice);
            itemCartAmount = itemView.findViewById(R.id.itemCartAmount);
            itemCartProductPrice2 = itemView.findViewById(R.id.itemCartProductPrice2);

        }
    }
}
