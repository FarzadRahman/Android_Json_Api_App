package com.example.farzad.ecommerce.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.farzad.ecommerce.MyApp;

public class SharedPreferenceUtils {
    private static String PreferenceName="ecommerceApp";
    private static SharedPreferenceUtils sharedPreferenceUtils;
    private SharedPreferences sharedPreferences;

    private SharedPreferenceUtils(Context context){
        // For Unique Name
        PreferenceName =PreferenceName +context.getPackageName();
        this.sharedPreferences=context.getSharedPreferences(PreferenceName,Context.MODE_PRIVATE);

    }

    public static SharedPreferenceUtils getInstance(){
        if(sharedPreferenceUtils==null){
            sharedPreferenceUtils=new SharedPreferenceUtils(MyApp.getContext());
        }
        return sharedPreferenceUtils;
    }

    public void saveString(String key,String value){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public String getString(String key){
        return sharedPreferences.getString(key,"");
    }
}
