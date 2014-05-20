package com.abhishek_k.mymodule.criminalIntent.utils;

import android.text.format.DateFormat;

import java.util.Date;

public class Helper {

    public static String strDate(Date date) {
        return DateFormat.format("E, MMM dd, yyyy", date).toString();
    }

}
