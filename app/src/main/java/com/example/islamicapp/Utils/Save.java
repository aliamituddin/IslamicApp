package com.example.islamicapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Save {

    public  static  void save(Context ctx,String name,String value){
        SharedPreferences sp = ctx.getSharedPreferences("sharedPrefrences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name,value);
        editor.apply();
    }

    public static String read(Context ctx, String name, String dealutValue){
        SharedPreferences sp = ctx.getSharedPreferences("sharedPrefrences",Context.MODE_PRIVATE);
        return sp.getString(name,dealutValue);
    }
}
