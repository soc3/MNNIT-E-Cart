package com.crazyhitty.chdev.ks.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GlobalActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, GlobalActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, GlobalActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

}
