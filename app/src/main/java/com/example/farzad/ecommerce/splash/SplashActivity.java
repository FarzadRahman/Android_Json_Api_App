package com.example.farzad.ecommerce.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.farzad.ecommerce.R;
import com.example.farzad.ecommerce.Utility.SharedPreferenceUtils;
import com.example.farzad.ecommerce.home.HomeActivity;
import com.example.farzad.ecommerce.login.SigninActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    public void init(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
//                if(SharedPreferenceUtils.getInstance().getString("register_user").equalsIgnoreCase("")){
////                    Not Register User
//                   intent=new Intent(SplashActivity.this, SigninActivity.class);
//                }
//                else {
//                    Register User
                     intent=new Intent(SplashActivity.this, HomeActivity.class);
//                }
                startActivity(intent);
                finish();

            }
        },2000);
    }
}
