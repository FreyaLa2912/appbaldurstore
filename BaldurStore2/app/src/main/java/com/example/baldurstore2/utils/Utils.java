package com.example.baldurstore2.utils;

import com.example.baldurstore2.model.GioHang;
import com.example.baldurstore2.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    //public static final String BASE_URL="http://192.168.0.118/android-baldur/";
    //public static final String BASE_URL="http://192.168.31.190/android-baldur/";

    public static final String BASE_URL="http://" +
            "192.168.90.181/android-baldur/";
    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static User user_current = new User();
}
