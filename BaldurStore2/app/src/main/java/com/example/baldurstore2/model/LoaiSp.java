package com.example.baldurstore2.model;

public class LoaiSp {
    int id;
    String product_name;
    String image;

    public LoaiSp(String product_name, String image) {
        this.product_name = product_name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
