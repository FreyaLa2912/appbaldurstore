package com.example.baldurstore2.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baldurstore2.Interface.ImageClickListener;
import com.example.baldurstore2.R;
import com.example.baldurstore2.model.EventBus.TinhTongEvent;
import com.example.baldurstore2.model.GioHang;
import com.example.baldurstore2.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import okhttp3.internal.Util;

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
        holder.itemCartProductPrice.setText(decimalFormat.format((gioHang.getGiasp())));
        long gia = gioHang.getSoLuong() * gioHang.getGiasp();
        holder.itemCartProductPrice2.setText(decimalFormat.format(gia));
        holder.itemCartCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Utils.mangmuahang.add(gioHang);
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                } else {
                    for (int i = 0; i < Utils.mangmuahang.size(); i++){
                        if (Utils.mangmuahang.get(i).getIdsp() == gioHang.getIdsp()){
                            Utils.mangmuahang.remove(i);
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        }
                    }
                }
            }
        });


        holder.setListener(new ImageClickListener() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                Log.d("TAG", "onImageClick: " + pos + " ..." + giatri);
                if (giatri == 1){
                    if (gioHangList.get(pos).getSoLuong() > 1){
                        int soLuongMoi = gioHangList.get(pos).getSoLuong()-1;
                        gioHangList.get(pos).setSoLuong(soLuongMoi);

                        holder.itemCartAmount.setText(gioHangList.get(pos).getSoLuong() + " ");
                        long gia = gioHangList.get(pos).getSoLuong() * gioHangList.get(pos).getGiasp();
                        holder.itemCartProductPrice2.setText(decimalFormat.format(gia));
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    } else if (gioHangList.get(pos).getSoLuong() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Th??ng b??o");
                        builder.setMessage("B???n c?? mu???n x??a s???n ph???m n??y kh???i gi??? h??ng");
                        builder.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        });
                        builder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                }else if (giatri == 2){
                    if (gioHangList.get(pos).getSoLuong() < 10){
                        int soLuongMoi = gioHangList.get(pos).getSoLuong()+1;
                        gioHangList.get(pos).setSoLuong(soLuongMoi);
                    }
                    holder.itemCartAmount.setText(gioHangList.get(pos).getSoLuong() + " ");
                    long gia = gioHangList.get(pos).getSoLuong() * gioHangList.get(pos).getGiasp();
                    holder.itemCartProductPrice2.setText(decimalFormat.format(gia));
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView itemCartImage, itemCartRemoveProduct, itemCartAddProduct;
        TextView itemCartProductName, itemCartProductPrice, itemCartAmount, itemCartProductPrice2;
        ImageClickListener listener;
        CheckBox itemCartCheck;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCartImage = itemView.findViewById(R.id.itemCartImage);
            itemCartProductName = itemView.findViewById(R.id.itemCartProductName);
            itemCartProductPrice = itemView.findViewById(R.id.itemCartProductPrice);
            itemCartAmount = itemView.findViewById(R.id.itemCartAmount);
            itemCartProductPrice2 = itemView.findViewById(R.id.itemCartProductPrice2);
            itemCartRemoveProduct = itemView.findViewById(R.id.itemCartRemoveProduct);
            itemCartAddProduct = itemView.findViewById(R.id.itemCartAddProduct);
            itemCartCheck = itemView.findViewById(R.id.itemCartCheck);
            //event click
            itemCartRemoveProduct.setOnClickListener(this);
            itemCartAddProduct.setOnClickListener(this);
        }

        public void setListener(ImageClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if (view == itemCartRemoveProduct){
                listener.onImageClick(view, getAdapterPosition(), 1);
                //1 la tru
            }else if (view == itemCartAddProduct){
                listener.onImageClick(view, getAdapterPosition(), 2);
                //2 la cong
            }
        }
    }
}
